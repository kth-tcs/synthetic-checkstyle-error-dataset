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
package spoon.support.reflect.reference;

import static spoon.reflect.path.CtRole.PACKAGE_REF;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import spoon.reflect.annotations.MetamodelPropertyField;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtTypeMemberWildcardImportReference;
import spoon.reflect.visitor.CtVisitor;
import spoon.support.DerivedProperty;
import spoon.support.Experimental;
import spoon.support.UnsettableProperty;
import spoon.support.reflect.declaration.CtElementImpl;

/**
 * This class intends to be used only to represent the reference of a
 * static import of all members of a type:
 *
 * import static org.junit.Assert.*;
 */
@Experimental
public class CtTypeMemberWildcardImportReferenceImpl extends CtElementImpl implements CtTypeMemberWildcardImportReference {
	private static final long serialVersionUID = 1L;

	@MetamodelPropertyField(role = CtRole.TYPE_REF)
	private CtTypeReference<?> typeReference;

	@Override
	public CtTypeReference<?> getTypeReference() {
		return typeReference;
	}

	@Override
	public CtTypeMemberWildcardImportReferenceImpl setTypeReference(CtTypeReference<?> typeReference) {
		if (typeReference != null) {
			typeReference.setParent(this);		}
		getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, PACKAGE_REF, typeReference, this.typeReference);
		this.typeReference = typeReference;
		return this;
	}

	@Override
	public String getSimpleName() {
		return typeReference == null ? null : typeReference.getQualifiedName() + ".*";
	}

	@Override
	@UnsettableProperty
	public <T extends CtReference> T setSimpleName(String simpleName) {
		return (T) this;
	}

	@Override
	public void accept(CtVisitor visitor) {
		visitor.visitCtTypeMemberWildcardImportReference(this);
	}

	@Override
	public CtTypeMemberWildcardImportReferenceImpl clone() {
		return (CtTypeMemberWildcardImportReferenceImpl) super.clone();
	}

	@Override
	@DerivedProperty
	public CtType<?> getDeclaration() {
		return typeReference == null ? null : typeReference.getTypeDeclaration();
	}

	@Override
	@DerivedProperty
	public List<CtAnnotation<? extends Annotation>> getAnnotations() {
		return Collections.emptyList();
	}

	@Override
	@UnsettableProperty
	public <E extends CtElement> E addAnnotation(CtAnnotation<? extends Annotation> annotation) {
		return (E) this;
	}

	@Override
	@UnsettableProperty
	public boolean removeAnnotation(CtAnnotation<? extends Annotation> annotation) {
		return false;
	}

	@Override
	@UnsettableProperty
	public <E extends CtElement> E setAnnotations(List<CtAnnotation<? extends Annotation>> annotations) {
		return (E) this;
	}

	@Override
	@DerivedProperty
	public boolean isImplicit() {
		return false;
	}

	@Override
	@UnsettableProperty
	public <E extends CtElement> E setImplicit(boolean implicit) {
		return (E) this;
	}
}
