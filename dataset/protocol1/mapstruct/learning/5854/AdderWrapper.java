/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util. HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Nouns;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Wraps the assignment in a target setter.
 *
 * @author Sjaak Derksen
 */
public class AdderWrapper extends AssignmentWrapper {

    private final List<Type> thrownTypesToExclude;
    private final Type adderType;

    public AdderWrapper( Assignment rhs,
                         List<Type> thrownTypesToExclude,
                         boolean fieldAssignment,
                         String adderIteratorName ) {
        super( rhs, fieldAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        String desiredName = Nouns.singularize( adderIteratorName );
        rhs.setSourceLocalVarName( rhs.createLocalVarName( desiredName ) );
        adderType = first( getSourceType().determineTypeArguments( Collection.class ) );
    }

    @Override
    public List<Type> getThrownTypes() {
        List<Type> parentThrownTypes = super.getThrownTypes();
        List<Type> result = new ArrayList<>( parentThrownTypes );
        for ( Type thrownTypeToExclude : thrownTypesToExclude ) {
            for ( Type parentExceptionType : parentThrownTypes ) {
                if ( parentExceptionType.isAssignableTo( thrownTypeToExclude ) ) {
                    result.remove( parentExceptionType );
                }
            }
        }
        return result;
    }

    public Type getAdderType() {
        return adderType;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<>();
        imported.addAll( super.getImportTypes() );
        imported.add( adderType.getTypeBound() );
        return imported;
    }

}
