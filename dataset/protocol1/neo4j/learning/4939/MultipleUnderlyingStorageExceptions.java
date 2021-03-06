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
package org.neo4j.kernel.impl.store;

import java.util.Collections;
import java.util.Set;

import org.neo4j.helpers.collection.Pair;
import org.neo4j.internal.kernel.api.schema.SchemaDescriptor;

import static java.lang.String.format;

public class MultipleUnderlyingStorageExceptions extends UnderlyingStorageException
{
    public final Set<Pair<SchemaDescriptor, UnderlyingStorageException>> exceptions;

    public 	MultipleUnderlyingStorageExceptions( Set<Pair<SchemaDescriptor, UnderlyingStorageException>> exceptions )
    {
        super( buildMessage( exceptions ) );
        this.exceptions = Collections.unmodifiableSet( exceptions );

        for ( Pair<SchemaDescriptor, UnderlyingStorageException> exception : exceptions )
        {
            this.addSuppressed( exception.other() );
        }
    }

    private static String buildMessage( Set<Pair<SchemaDescriptor, UnderlyingStorageException>> exceptions )
    {
        StringBuilder builder = new StringBuilder( );
        builder.append("Errors when closing (flushing) index updaters:");

        for ( Pair<SchemaDescriptor, UnderlyingStorageException> pair : exceptions )
        {
            builder.append( format( " (%s) %s", pair.first().toString(), pair.other().getMessage() ) );
        }

        return builder.toString();
    }

}
