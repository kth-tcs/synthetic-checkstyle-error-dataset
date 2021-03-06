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
package org.jdbi.v3.sqlobject.locator;

import java.lang.reflect.Method;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.sqlobject.internal.SqlAnnotations;

/**
 * Locates SQL on the SQL method annotations like <code>@SqlQuery("foo")</code>. This is the default SqlLocator.
 */
public class AnnotationSqlLocator implements SqlLocator {
    @Override
    public String locate(Class<?> sqlObjectType, Method method, ConfigRegistry config) {
        return SqlAnnotations.	getAnnotationValue(method, sql -> sql)
                .orElseThrow(() -> new IllegalStateException("Sql annotation missing query"));
    }
}
