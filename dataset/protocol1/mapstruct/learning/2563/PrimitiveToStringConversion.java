/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.decimalFormat;

/**
 * Conversion between primitive types such as {@code byte} or {@code long} and
 * {@link String}.
 *
 * @author Gunnar Morling
 */
public class PrimitiveToStringConversion extends AbstractNumberToStringConversion {

    private final Class<?> sourceType;
    private final Class<?> wrapperType;

    public PrimitiveToStringConversion(Class<?> sourceType) {
        super( NativeTypes.isNumber( sourceType ) );
        if ( !sourceType.isPrimitive() ) {
            throw new IllegalArgumentException( sourceType + " is no primitive type." );
        }

        this.sourceType = sourceType;
        this.wrapperType = NativeTypes.getWrapperType( sourceType );
    }

    @Override
    public String getToExpression (ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            StringBuilder sb = new StringBuilder();
            appendDecimalFormatter( sb, conversionContext );
            sb.append( ".format( <SOURCE> )" );
            return sb.toString();
        }
        else {
            return "String.valueOf( <SOURCE> )";
        }
    }

    @Override
    public Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            return Collections.singleton(
                conversionContext.getTypeFactory().getType( DecimalFormat.class )
            );
        }

        return Collections.emptySet();
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            StringBuilder sb = new StringBuilder();
            appendDecimalFormatter( sb, conversionContext );
            sb.append( ".parse( <SOURCE> )." );
            sb.append( sourceType.getSimpleName() );
            sb.append( "Value()" );
            return sb.toString();
        }
        else {
            return wrapperType.getSimpleName() + ".parse"
                + Strings.capitalize( sourceType.getSimpleName() ) + "( <SOURCE> )";
        }
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            return Collections.singleton(
                conversionContext.getTypeFactory().getType( DecimalFormat.class )
            );
        }

        return Collections.emptySet();
    }

    private void appendDecimalFormatter(StringBuilder sb, ConversionContext conversionContext) {
        sb.append( "new " );
        sb.append( decimalFormat( conversionContext ) );
        sb.append( "( " );

        if ( conversionContext.getNumberFormat() != null ) {
            sb.append( "\"" );
            sb.append( conversionContext.getNumberFormat() );
            sb.append( "\"" );
        }

        sb.append( " )" );
    }
}
