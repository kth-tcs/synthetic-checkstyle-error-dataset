/**
 * Copyright (C) 2006-2018 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.reflect.declaration;

import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtEnumValue;
import spoon.reflect.visitor.CtVisitor;
import spoon.support.DerivedProperty;

public class CtEnumValueImpl<T> extends CtFieldImpl<T> implements CtEnumValue<T> {
	
@Override
	public void accept(CtVisitor v) {
		v.visitCtEnumValue(this);
	}

	@Override
	public CtEnumValue clone() {
		return (CtEnumValue) super.clone();
	}

	@DerivedProperty
	@Override
	public CtExpression<T> getAssignment() {
		return null;
	}
}
