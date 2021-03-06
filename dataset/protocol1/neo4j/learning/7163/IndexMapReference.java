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
package org.neo4j.kernel.impl.api.index;

import org.eclipse.collections.api.set.primitive.IntSet;

import java.util.Set;
import java.util.function.Function;

import org.neo4j.function.ThrowingFunction;
import org.neo4j.internal.kernel.api.exceptions.schema.IndexNotFoundKernelException;
import org.neo4j.internal.kernel.api.schema.SchemaDescriptor;
import org.neo4j.storageengine.api.EntityType;
import org.neo4j.values.storable.Value;

public class IndexMapReference implements IndexMapSnapshotProvider
{
    private volatile IndexMap indexMap = new IndexMap();

    @Override
    public IndexMap indexMapSnapshot()
    {
        return indexMap.clone();
    }

    /**
     * Modifies the index map under synchronization. Accepts a {@link ThrowingFunction} which gets as input
     * a snapshot of the current {@link IndexMap}. That {@link IndexMap} is meant to be modified by the function
     * and in the end returned. The function can also return another {@link IndexMap} instance if it wants to, e.g.
     * for clearing the map. The returned map will be set as the current index map before exiting the method.
     *
     * This is the only way contents of the {@link IndexMap} considered the current one can be modified.
     *
     * @param modifier the function modifying the snapshot.
     * @throws E exception thrown by the function.
     */
    public synchronized <E extends Exception> void modify( Function<IndexMap,IndexMap> modifier ) throws E
    {
        IndexMap snapshot = indexMapSnapshot();
        indexMap = modifier.apply( snapshot );
    }

    public IndexProxy getIndexProxy( long indexId ) throws IndexNotFoundKernelException
    {
        IndexProxy proxy = indexMap.getIndexProxy( indexId );
        if ( proxy == null )
        {
            throw new IndexNotFoundKernelException( "No index for index id " + indexId + " exists." );
        }
        return proxy;
    }

    public IndexProxy getIndexProxy( SchemaDescriptor descriptor ) throws IndexNotFoundKernelException
    {
        IndexProxy proxy = indexMap.getIndexProxy( descriptor );
        if ( proxy == null )
        {
            throw new IndexNotFoundKernelException( "No index for " + descriptor + " exists." );
        }
        return proxy;
    }

    public long getIndexId( SchemaDescriptor descriptor ) throws IndexNotFoundKernelException
    {
        IndexProxy proxy = indexMap.getIndexProxy( descriptor );
        if ( proxy == null )
        {
            throw new IndexNotFoundKernelException( "No index for " + descriptor + " exists." );
        }
        return indexMap.getIndexId( descriptor );
    }

    public long getOnlineIndexId( SchemaDescriptor descriptor ) throws IndexNotFoundKernelException
    {
        IndexProxy proxy = getIndexProxy( descriptor );
        switch ( proxy.getState() )
        {
        case ONLINE:
            return indexMap.getIndexId( descriptor );

        default:
            throw new IndexNotFoundKernelException( "Expected index on " + descriptor + " to be online." );
        }
    }

    public Iterable<IndexProxy> getAllIndexProxies()
    {
        return indexMap.getAllIndexProxies();
    }

    public Set<SchemaDescriptor> getRelatedIndexes( long[] changedEntityTokens, long[] unchangedEntityTokens, IntSet properties,
            EntityType entityType )
    {
        return indexMap.getRelatedIndexes( changedEntityTokens, unchangedEntityTokens, properties, entityType );
    }

    public IndexUpdaterMap createIndexUpdaterMap( IndexUpdateMode mode 
)
    {
        return new IndexUpdaterMap( indexMap, mode );
    }

    public void validateBeforeCommit( SchemaDescriptor index, Value[] tuple )
    {
        IndexProxy proxy = indexMap.getIndexProxy( index );
        if ( proxy != null )
        {
            // Do this null-check since from the outside there's a best-effort matching going on between updates and actual indexes backing those.
            proxy.validateBeforeCommit( tuple );
        }
    }
}
