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
package spoon.support.reflect.code;

import spoon.reflect.annotations.MetamodelPropertyField;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtTargetedExpression;
import spoon.reflect.reference.CtFieldReference;

import static spoon.reflect.path.CtRole.TARGET;

public abstract class CtFieldAccessImpl<T> extends CtVariableAccessImpl<T> implements CtFieldAccess<T> {
	private static final long serialVersionUID = 1L;

	@MetamodelPropertyField(role = TARGET)
	CtExpression<?> target;

	@Override
	public CtExpression<?> getTarget() {
		return target;
	}

	@Override
	public <C extends CtTargetedExpression<T, CtExpression<?>>> C setTarget(CtExpression<?> target) {
		if (target != null) {
			target.setParent(this);
		}
		getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, TARGET, target, this.target);
		this.target = target;
		return null;
	}

	@Override
	public CtFieldReference<T> getVariable() {
		if (variable != null) {
			return (CtFieldReference<T>) variable;
		}
		if (getFactory() != null) {
			CtFieldReference<Object> fieldReference = getFactory().Core().createFieldReference();
			fieldReference.setParent(this);
			return (CtFieldReference<T>) fieldReference;
		}
		return null;
	}

	@Override
	public CtFieldAccess<T> clone() {
		return (CtFieldAccess<T>) super.clone();
	}

}
