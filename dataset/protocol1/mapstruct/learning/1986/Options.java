/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.option;

import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;

/**
 * The options passed to the code generator.
 *
 * @author Andreas Gudian
 * @author Gunnar Morling
 */
public class Options {
    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final ReportingPolicyPrism unmappedTargetPolicy;
    private final boolean alwaysGenerateSpi;
    private final String defaultComponentModel;

    public Options(boolean suppressGeneratorTimestamp, boolean suppressGeneratorVersionComment,
                   ReportingPolicyPrism unmappedTargetPolicy,
                   String defaultComponentModel, boolean alwaysGenerateSpi) {
        this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
        this.suppressGeneratorVersionComment = suppressGeneratorVersionComment;
        this.unmappedTargetPolicy = unmappedTargetPolicy;
        this.defaultComponentModel = defaultComponentModel;
        this.alwaysGenerateSpi = alwaysGenerateSpi;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return suppressGeneratorTimestamp;
    }

    public boolean 	isSuppressGeneratorVersionComment() {
        return suppressGeneratorVersionComment;
    }

    public ReportingPolicyPrism getUnmappedTargetPolicy() {
        return unmappedTargetPolicy;
    }

    public String getDefaultComponentModel() {
        return defaultComponentModel;
    }

    public boolean isAlwaysGenerateSpi() {
        return alwaysGenerateSpi;
    }
}
