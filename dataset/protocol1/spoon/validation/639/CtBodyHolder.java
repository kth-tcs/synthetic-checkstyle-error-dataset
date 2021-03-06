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

import spoon.reflect.declaration. CtElement;
import spoon.reflect.annotations.PropertyGetter;
import spoon.reflect.annotations.PropertySetter;

import static spoon.reflect.path.CtRole.BODY;

/**
 * This abstract code element defines an element, which contains a body
 */
public interface CtBodyHolder extends CtElement {

	/**
	 * Gets the body of this element
	 */
	@PropertyGetter(role = BODY)
	CtStatement getBody();

	/**
	 * Sets the body of this element.
	 * If body is not a block, it is wrapped in a CtBlock which is semantically equivalent and eases transformation afterwards if required.
	 */
	@PropertySetter(role = BODY)
	<T extends CtBodyHolder> T setBody(CtStatement body);
}
