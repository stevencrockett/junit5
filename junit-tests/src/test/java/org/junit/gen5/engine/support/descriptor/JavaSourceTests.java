/*
 * Copyright 2015-2016 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.engine.support.descriptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.gen5.api.Assertions.assertThrows;

import java.lang.reflect.Method;

import org.junit.gen5.api.Test;
import org.junit.gen5.api.TestInfo;
import org.junit.gen5.commons.util.PreconditionViolationException;

/**
 * Unit tests for {@link JavaPackageSource}, {@link JavaClassSource}, and
 * {@link JavaMethodSource}.
 *
 * @since 5.0
 */
class JavaSourceTests extends AbstractTestSourceTests {

	@Test
	void packageSourceFromNullPackageName() {
		assertThrows(PreconditionViolationException.class, () -> new JavaPackageSource((String) null));
	}

	@Test
	void packageSourceFromEmptyPackageName() {
		assertThrows(PreconditionViolationException.class, () -> new JavaPackageSource("  "));
	}

	@Test
	void packageSourceFromPackageName() {
		String testPackage = getClass().getPackage().getName();
		JavaPackageSource source = new JavaPackageSource(testPackage);

		assertThat(source.getPackageName()).isEqualTo(testPackage);
	}

	@Test
	void packageSourceFromNullPackageReference() {
		assertThrows(PreconditionViolationException.class, () -> new JavaPackageSource((Package) null));
	}

	@Test
	void packageSourceFromPackageReference() {
		Package testPackage = getClass().getPackage();
		JavaPackageSource source = new JavaPackageSource(testPackage);

		assertThat(source.getPackageName()).isEqualTo(testPackage.getName());
	}

	@Test
	void classSource() {
		Class<?> testClass = getClass();
		JavaClassSource source = new JavaClassSource(testClass);

		assertThat(source.getJavaClass()).isEqualTo(testClass);
	}

	@Test
	void methodSource() throws Exception {
		Method testMethod = getExampleMethod();
		JavaMethodSource source = new JavaMethodSource(testMethod);

		assertThat(source.getJavaClass()).isEqualTo(getClass());
		assertThat(source.getJavaMethodName()).isEqualTo(testMethod.getName());
		assertThat(source.getJavaMethodParameterTypes()).containsExactly(String.class);
	}

	@Test
	void equalsAndHashCodeForJavaPackageSource() {
		Package testPackage = getClass().getPackage();
		assertEqualsAndHashCode(new JavaPackageSource(testPackage), new JavaPackageSource(testPackage));
	}

	@Test
	void equalsAndHashCodeForJavaClassSource() {
		Class<?> testClass = getClass();
		assertEqualsAndHashCode(new JavaClassSource(testClass), new JavaClassSource(testClass));
	}

	@Test
	void equalsAndHashCodeForJavaMethodSource(TestInfo testInfo) throws Exception {
		Method testMethod = getExampleMethod();
		assertEqualsAndHashCode(new JavaMethodSource(testMethod), new JavaMethodSource(testMethod));
	}

	void exampleMethod(String text) {
	}

	private Method getExampleMethod() throws Exception {
		return getClass().getDeclaredMethod("exampleMethod", String.class);
	}

}
