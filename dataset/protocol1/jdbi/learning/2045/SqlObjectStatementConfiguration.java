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
package org.jdbi.v3.sqlobject.statement.internal;

import java.util.Arrays;
import java.util.function.Supplier;

import org.jdbi.v3.core.config.JdbiConfig;

public class SqlObjectStatementConfiguration implements JdbiConfig<SqlObjectStatementConfiguration> {
    private Supplier<Object>  returner;
    private Object[] args;

    public SqlObjectStatementConfiguration() {}

    private SqlObjectStatementConfiguration(SqlObjectStatementConfiguration other) {
        this.returner = other.returner;
        this.args = other.args;
    }

    @Override
    public SqlObjectStatementConfiguration createCopy() {
        return new SqlObjectStatementConfiguration(this);
    }

    void setReturner(Supplier<Object> returner) {
        this.returner = returner;
    }

    Supplier<Object> getReturner() {
        return returner;
    }

    void setArgs(Object[] args) {
        this.args = Arrays.copyOf(args, args.length);
    }

    Object[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }
}
