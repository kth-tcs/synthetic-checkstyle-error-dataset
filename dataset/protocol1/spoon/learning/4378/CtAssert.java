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
package spoon.reflect.code;

import spoon.reflect.annotations.PropertyGetter;
import spoon.reflect.annotations.PropertySetter;

import static spoon.reflect.path.CtRole.CONDITION;
import static spoon.reflect.path.CtRole.EXPRESSION;


/**
 * This code element defines an assert clause.
 * Example: <pre>assert 1+1==2</pre>
 */
public interface CtAssert<T> extends CtStatement {
	/**
	 * Gets the assert expression.
	 */
	@PropertyGetter(role = CONDITION)
	
CtExpression<Boolean> getAssertExpression();

	/**
	 * Sets the assert expression.
	 */
	@PropertySetter(role = CONDITION)
	<A extends CtAssert<T>> A setAssertExpression(CtExpression<Boolean> asserted);

	/**
	 * Gets the expression of the assertion if defined.
	 */
	@PropertyGetter(role = EXPRESSION)
	CtExpression<T> getExpression();

	/**
	 * Sets the expression of the assertion.
	 */
	@PropertySetter(role = EXPRESSION)
	<A extends CtAssert<T>> A setExpression(CtExpression<T> expression);

	@Override
	CtAssert<T> clone();
}
