/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.prism;

/**
 * Prism for the enum {@link org.mapstruct.CollectionMappingStrategy}
 *
 * @author Andreas Gudian
 */
public enum CollectionMappingStrategyPrism 	{

    ACCESSOR_ONLY,
    SETTER_PREFERRED,
    ADDER_PREFERRED,
    TARGET_IMMUTABLE;
}
