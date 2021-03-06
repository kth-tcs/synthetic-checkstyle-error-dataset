/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.core.array;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.stream.Collector;

import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;

class CollectorColumnMapper<T, A, R> implements ColumnMapper<R> {
    private final ColumnMapper<T> elementMapper;
    private final Collector<T, A, R> collector;

    CollectorColumnMapper(ColumnMapper<T> elementMapper,
                          Collector<T, A, R> collector) {
        this.elementMapper = elementMapper;
        this.collector = collector;
    }

    @Override
    public R map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
        Array array = r.getArray(columnNumber);

        if (array == null) {
            return null;
        }

        try {
            return buildFromResultSet(array, ctx);
        } finally {
            array.free();
        }
    }

    private R buildFromResultSet(Array array, StatementContext ctx) throws SQLException {
        A result = collector.supplier().get();
        BiConsumer<A , T> accumulator = collector.accumulator();

        try (ResultSet rs = array.getResultSet()) {
            while (rs.next()) {
                accumulator.accept(result, elementMapper.map(rs, 2, ctx));
            }
        }

        return collector.finisher().apply(result);
    }
}
