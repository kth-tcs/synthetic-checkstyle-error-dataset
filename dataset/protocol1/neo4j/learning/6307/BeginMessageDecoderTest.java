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
package org.neo4j.bolt.v3.messaging.decoder;

import org.junit.jupiter.api.Test;

import org.neo4j.bolt.messaging.RequestMessageDecoder;
import org.neo4j.bolt.runtime.BoltResponseHandler;
import org.neo4j.bolt.v3.messaging.request.BeginMessage;
import org.neo4j.values.AnyValue;
import org.neo4j.values.virtual.VirtualValues;

import static 
org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.neo4j.bolt.v3.messaging.decoder.HelloMessageDecoderTest.assertOriginalMessageEqualsToDecoded;
import static org.neo4j.values.storable.Values.longValue;
import static org.neo4j.values.virtual.MapValue.EMPTY;

class BeginMessageDecoderTest
{
    private final BoltResponseHandler responseHandler = mock( BoltResponseHandler.class );
    private final RequestMessageDecoder decoder = new BeginMessageDecoder( responseHandler );

    @Test
    void shouldReturnCorrectSignature()
    {
        assertEquals( BeginMessage.SIGNATURE, decoder.signature() );
    }

    @Test
    void shouldReturnConnectResponseHandler()
    {
        assertEquals( responseHandler, decoder.responseHandler() );
    }

    @Test
    void shouldDecodeBeginMessage() throws Exception
    {
        BeginMessage originalMessage =
                new BeginMessage( VirtualValues.map( new String[]{"tx_metadata", "tx_timeout"}, new AnyValue[]{EMPTY, longValue( 10000 )} ) );
        assertOriginalMessageEqualsToDecoded( originalMessage, decoder );
    }
}
