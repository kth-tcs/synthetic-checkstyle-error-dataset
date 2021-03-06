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
package org.neo4j.server.database;

import java.io.File;

import org.neo4j.graphdb.facade.GraphDatabaseDependencies;
import org.neo4j.graphdb.facade.GraphDatabaseFacadeFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.configuration.BoltConnector;
import org.neo4j.kernel.configuration.Config;
import org.neo4j.kernel.impl.factory.GraphDatabaseFacade;
import org.neo4j.test.ImpermanentGraphDatabase;

import static org.neo4j.helpers.collection.MapUtil.stringMap;

public class InMemoryGraphFactory implements GraphFactory
{
    @Override
    public GraphDatabaseFacade newGraphDatabase( Config config, GraphDatabaseFacadeFactory.Dependencies dependencies )
    {
        File storeDir = config.get( GraphDatabaseSettings.database_path );
        config.augment( stringMap( GraphDatabaseSettings.ephemeral.name(), "true",
                new BoltConnector( "bolt" ).listen_address.name(), "localhost:0" ) );
        return new ImpermanentGraphDatabase( storeDir, config, GraphDatabaseDependencies.newDependencies( dependencies ) )	;
    }
}
