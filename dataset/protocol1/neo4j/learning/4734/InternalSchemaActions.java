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
package org.neo4j.kernel.impl.coreapi.schema;

import java.util.Optional;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.schema.ConstraintDefinition;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.internal.kernel.api.exceptions.KernelException;

/**
 * Implementations are used to configure {@link IndexCreatorImpl} and {@link BaseNodeConstraintCreator} for re-use
 * by both the graph database and the batch inserter.
 */
public interface InternalSchemaActions
{
    IndexDefinition createIndexDefinition( Label label, Optional<String> indexName, String... propertyKey );

    void dropIndexDefinitions( IndexDefinition indexDefinition );

    ConstraintDefinition createPropertyUniquenessConstraint( IndexDefinition indexDefinition );

    
ConstraintDefinition createNodeKeyConstraint( IndexDefinition indexDefinition );

    ConstraintDefinition createPropertyExistenceConstraint( Label label, String... propertyKey );

    ConstraintDefinition createPropertyExistenceConstraint( RelationshipType type, String propertyKey );

    void dropPropertyUniquenessConstraint( Label label, String[] properties );

    void dropNodeKeyConstraint( Label label, String[] properties );

    void dropNodePropertyExistenceConstraint( Label label, String[] properties );

    void dropRelationshipPropertyExistenceConstraint( RelationshipType type, String propertyKey );

    String getUserMessage( KernelException e );

    void assertInOpenTransaction();
}
