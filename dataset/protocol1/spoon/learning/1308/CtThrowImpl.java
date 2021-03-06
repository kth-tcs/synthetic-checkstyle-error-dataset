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
import spoon.reflect.code.CtCodeElement;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.CtVisitor;

import static spoon.reflect.path.CtRole.EXPRESSION;

public class CtThrowImpl extends CtStatementImpl implements CtThrow {
	private static final long serialVersionUID = 1L;

	@MetamodelPropertyField(role = EXPRESSION)
	CtExpression<? extends Throwable>throwExpression;

	@Override
	public void accept(CtVisitor visitor) {
		visitor.visitCtThrow(this);
	}

	@Override
	public CtExpression<? extends Throwable> getThrownExpression() {
		return throwExpression;
	}

	@Override
	public <T extends CtThrow> T setThrownExpression(CtExpression<? extends Throwable> expression) {
		if (expression != null) {
			expression.setParent(this);
		}
		getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, EXPRESSION, expression, this.throwExpression);
		this.throwExpression = expression;
		return (T) this;
	}

	@Override
	public CtThrow clone() {
		return (CtThrow) super.clone();
	}

	@Override
	public Void S() {
		return null;
	}

	public CtCodeElement getSubstitution(CtType<?> targetType) {
		return clone();
	}

}
