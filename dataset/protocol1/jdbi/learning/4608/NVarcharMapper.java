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
package org.jdbi.v3.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.qualifier.NVarchar;
import org.jdbi.v3.core.statement.StatementContext;

/**
 * Column mapper for Java {@code @NVarchar String} qualified type.
 */
@NVarchar
class NVarcharMapper implements ColumnMapper<String> {
    @Override
    public String 
map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
        return r.getNString(columnNumber);
    }
}
