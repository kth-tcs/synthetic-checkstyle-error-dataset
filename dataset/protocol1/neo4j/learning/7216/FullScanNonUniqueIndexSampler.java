/*
 * Copyright (c) 2002-2018 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.index.schema;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.neo4j.cursor.RawCursor;
import org.neo4j.index.internal.gbptree.GBPTree;
import org.neo4j.index.internal.gbptree.Hit;
import org.neo4j.kernel.impl.api.index.sampling.NonUniqueIndexSampler;
import org.neo4j.storageengine.api.schema.IndexSample;

/**
 * {@link NonUniqueIndexSampler} which performs a full scans of a {@link GBPTree} in {@link #result()}.
 *
 * @param <KEY> type of keys in tree.
 * @param <VALUE> type of values in tree.
 */
class FullScanNonUniqueIndexSampler<KEY extends NativeIndexKey<KEY>, VALUE extends NativeIndexValue>
        extends NonUniqueIndexSampler.Adapter
{
    private final GBPTree<KEY,VALUE> gbpTree;
    private final IndexLayout<KEY,VALUE> layout;

    FullScanNonUniqueIndexSampler( GBPTree<KEY,VALUE> gbpTree, IndexLayout<KEY,VALUE> layout )    {
        this.gbpTree = gbpTree;
        this.layout = layout;
    }

    @Override
    public IndexSample result()
    {
        KEY lowest = layout.newKey();
        lowest.initialize( Long.MIN_VALUE );
        lowest.initValuesAsLowest();
        KEY highest = layout.newKey();
        highest.initialize( Long.MAX_VALUE );
        highest.initValuesAsHighest();
        KEY prev = layout.newKey();
        try ( RawCursor<Hit<KEY,VALUE>,IOException> seek = gbpTree.seek( lowest, highest ) )
        {
            long sampledValues = 0;
            long uniqueValues = 0;

            // Get the first one so that prev gets initialized
            if ( seek.next() )
            {
                prev = layout.copyKey( seek.get().key(), prev );
                sampledValues++;
                uniqueValues++;

                // Then do the rest
                while ( seek.next() )
                {
                    Hit<KEY,VALUE> hit = seek.get();
                    if ( layout.compareValue( prev, hit.key() ) != 0 )
                    {
                        uniqueValues++;
                        layout.copyKey( hit.key(), prev );
                    }
                    // else this is a duplicate of the previous one
                    sampledValues++;
                }
            }
            return new IndexSample( sampledValues, uniqueValues, sampledValues );
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( e );
        }
    }

    @Override
    public IndexSample result( int numDocs )
    {
        throw new UnsupportedOperationException();
    }
}
