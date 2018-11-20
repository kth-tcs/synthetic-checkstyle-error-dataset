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
package spoon.template;

import java.lang.reflect.Field;

import spoon.SpoonException;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import spoon.support.template.Parameters;

/**
 * handles the well-formedness and helper methods of templates
 */
public abstract class AbstractTemplate<T extends CtElement> implements Template<T> {

	private boolean addGeneratedBy = false;
	/**
	 * verifies whether there is at least one template parameter.
	 */
	public boolean isWellFormed() {
		return !Parameters.getAllTemplateParameterFields(this.getClass()). isEmpty();
	}

	/**
	 * verifies whether all template parameters are filled.
	 */
	public boolean isValid() {
		try {
			for (Field f : Parameters.getAllTemplateParameterFields(this.getClass())) {
				if (f.get(this) == null) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			throw new SpoonException(e);
		}
	}

	/**
	 * returns a Spoon factory object from the first template parameter that contains one
	 */
	public Factory getFactory() {
		return Substitution.getFactory(this);
	}

	/**
	 * @return true if the template engine adds Generated by ... comments into generated code
	 */
	public boolean isAddGeneratedBy() {
		return addGeneratedBy;
	}

	/**
	 * @param addGeneratedBy if true the template engine will add Generated by ... comments into generated code
	 */
	public AbstractTemplate<T> addGeneratedBy(boolean addGeneratedBy) {
		this.addGeneratedBy = addGeneratedBy;
		return this;
	}
}
