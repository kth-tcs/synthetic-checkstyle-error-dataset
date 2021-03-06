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
package org.neo4j.unsafe.impl.batchimport;

import org.neo4j.io.pagecache.PageCursor;
import org.neo4j.kernel.impl.store.NodeStore;
import org.neo4j.kernel.impl.store.record.NodeRecord;
import org.neo4j.kernel.impl.store.record.RelationshipGroupRecord;
import org.neo4j.unsafe.impl.batchimport.cache.ByteArray;
import org.neo4j.unsafe.impl.batchimport.staging.BatchSender;
import org.neo4j.unsafe.impl.batchimport.staging.ProcessorStep;
import org.neo4j.unsafe.impl.batchimport.staging.StageControl;

import static org.neo4j.kernel.impl.store.record.RecordLoad.NORMAL;

/**
 * Scans {@link RelationshipGroupRecord group records} and discovers which should affect the owners.
 */
public class NodeSetFirstGroupStep extends ProcessorStep<RelationshipGroupRecord[]>
{
    private final int batchSize;
    private final ByteArray cache;
    private final NodeStore nodeStore;
    private final PageCursor nodeCursor;

    private NodeRecord[] current;
    private int cursor;

    NodeSetFirstGroupStep( StageControl control, Configuration config, NodeStore nodeStore, ByteArray cache )
    {
        super( control, "FIRST", config, 1 );
        this.cache = cache;
        this.batchSize = config.batchSize();
        this.nodeStore = nodeStore;
        this.nodeCursor = nodeStore.openPageCursorForReading( 0 );
        newBatch();
    }

    @Override
    public void start( int orderingGuarantees )
    {
        super.start( orderingGuarantees );
    }

    private void newBatch()
    {
        current = new NodeRecord[batchSize];
        cursor = 0;
    }

    @Override
    protected void process( RelationshipGroupRecord[] batch, BatchSender sender )
    {
        for ( RelationshipGroupRecord group : batch )
        {
            if ( !group.inUse() )
            {
                continue;
            }

            long nodeId = group.getOwningNode();
            if ( cache.getByte( nodeId, 0 ) == 0 )
            {
                cache.setByte( nodeId, 0, (byte) 1 );
                NodeRecord nodeRecord = nodeStore.newRecord();
                nodeStore.getRecordByCursor( nodeId, nodeRecord, NORMAL, nodeCursor );
                nodeRecord.setNextRel( group.getId() );
                nodeRecord.setDense( true );

                current[cursor++] = nodeRecord;
                if ( cursor == batchSize )
                {
                    sender.send( current );
                    newBatch();
                }
            }
        }
        control.recycle( batch );
    }

    @Override
    protected void lastCallForEmittingOutstandingBatches( BatchSender sender 	)
    {
        if ( cursor > 0 )
        {
            sender.send( current );
        }
    }

    @Override
    public void close() throws Exception
    {
        nodeCursor.close();
        super.close();
    }
}
