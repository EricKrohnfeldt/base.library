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

package com.herbmarshall.base.mock.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.herbmarshall.base.mock.function.RunnableMockish.error_uncalled;
import static com.herbmarshall.base.mock.function.RunnableMockish.error_unexpectedCall;

final class RunnableMockishTest {

	@Test
	void happyPath() {
		// Arrange
		RunnableMockish mock = new RunnableMockish();
		RunnableMockish expectOutput = mock.expect();
		// Act
		mock.run();
		mock.validate();
		// Assert
		Assertions.assertSame( mock, expectOutput );
	}

	@Test
	void multipleCalls() {
		// Arrange
		RunnableMockish mock = new RunnableMockish();
		RunnableMockish expectOutput = mock
			.expect()    // 1
			.expect()    // 2
			.expect()    // 3
			.expect()    // 4
			.expect();   // 5
		// Act
		mock.run();   // 1
		mock.run();   // 2
		mock.run();   // 3
		mock.run();   // 4
		mock.run();   // 5
		mock.validate();
		// Assert
		Assertions.assertSame( mock, expectOutput );
	}

	@Test
	void unexpected() {
		// Arrange
		RunnableMockish mock = new RunnableMockish();
		// Act
		try {
			mock.run();
			Assertions.fail();
		}
		// Assert
		catch ( IllegalStateException e ) {
			assertErrorMessage(
				error_unexpectedCall(),
				e
			);
		}
	}

	/** Expects but no 'called'. */
	@Test
	void validationFailed() {
		// Arrange
		UUID valueA = UUID.randomUUID();
		UUID valueB = UUID.randomUUID();
		RunnableMockish mock = new RunnableMockish();
		RunnableMockish expectOutput = mock
			.expect()    // 1
			.expect();   // 2
		// Act
		try {
			mock.validate();
			Assertions.fail();
		}
		// Assert
		catch ( AssertionError e ) {
			assertErrorMessage(
				error_uncalled( 2 ),
				e
			);
		}
		Assertions.assertSame( mock, expectOutput );
	}

	private void assertErrorMessage( String expected, Throwable e ) {
		System.out.println( "Expected error: " + e.getMessage() );
		Assertions.assertEquals(
			expected,
			e.getMessage().substring( 0, expected.length() )
		);
	}

}
