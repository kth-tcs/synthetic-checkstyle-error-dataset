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
package org.jdbi.v3.core.collector;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Collector;

import static org.jdbi.v3.core.collector.OptionalCollectors.toOptional;
import static org.jdbi.v3.core.generic.GenericTypes.findGenericParameter;
import static org.jdbi.v3.core.generic.GenericTypes.getErasedType;

class OptionalCollectorFactory implements CollectorFactory {
    @Override
    public boolean accepts(Type containerType) {
        return containerType instanceof ParameterizedType && Optional.class.equals(getErasedType(containerType));
    }

    @Override
    public Optional<Type> elementType(Type containerType) {
        Class<?> erasedType = getErasedType(containerType);
        return findGenericParameter(containerType, 
erasedType);
    }

    @Override
    public Collector<?, ?, ?> build(Type containerType) {
        return toOptional();
    }
}
