/*
 * This file is part of herbmarshall.com: base.library  ( hereinafter "base.library" ).
 *
 * base.library is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * base.library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with base.library.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.herbmarshall.nightShift;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.herbmarshall.nightShift.ClassLoader.error_badClassName;

final class ClassLoaderTest {

	@Test
	void happyPath() {
		// Arrange
		ClassLoader loader = loader();
		Class<?> expected = PackagesForTesting.happyPath
			.getAutomatedClasses()
			.findFirst()
			.orElseThrow();
		String className = expected.getCanonicalName();
		// Act
		Class<?> output = loader.apply( className );
		// Assert
		Assertions.assertSame( expected, output );
	}

	@Test
	void classNotFound() {
		// Arrange
		ClassLoader loader = loader();
		String className = randomString();
		// Act
		try {
			loader.apply( className );
		}
		// Assert
		catch ( RuntimeException exception ) {
			Assertions.assertEquals(
				error_badClassName( className ),
				exception.getMessage()
			);
		}
	}

	@Test
	void null_target() {
		// Arrange
		ClassLoader loader = loader();
		// Act
		try {
			loader.apply( null );
		}
		// Assert
		catch ( NullPointerException npe ) {
			System.out.println( "Pass: " + npe.getMessage() );
		}
	}

	private ClassLoader loader() {
		return new ClassLoader();
	}

	private static String randomString() {
		return UUID.randomUUID().toString();
	}

}
