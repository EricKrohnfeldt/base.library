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

import static com.herbmarshall.base.mock.function.RunnableMockishFailing.error_uncalled;
import static com.herbmarshall.base.mock.function.RunnableMockishFailing.error_unexpectedCall;

final class RunnableMockishFailingTest {

	@Test
	void happyPath() {
		// Arrange
		RuntimeException exception = randomException();
		RunnableMockishFailing mock = new RunnableMockishFailing();
		RunnableMockishFailing expectOutput = mock.expect( exception );
		// Act
		try {
			mock.run();
			Assertions.fail();
		}
		catch ( RuntimeException e ) {
			Assertions.assertSame( exception, e );
		}
		// Assert
		mock.validate();
		Assertions.assertSame( mock, expectOutput );
	}

	@Test
	void nullException() {
		// Arrange
		RunnableMockishFailing mock = new RunnableMockishFailing();
		// Act
		try {
			mock.expect( null );
			Assertions.fail();
		}
		// Assert
		catch ( NullPointerException ignored ) {
		}
	}

	@Test
	void multipleCalls() {
		// Arrange
		RuntimeException exceptionA = randomException();
		RuntimeException exceptionB = randomException();
		RuntimeException exceptionC = randomException();
		RunnableMockishFailing mock = new RunnableMockishFailing();
		RunnableMockishFailing expectOutput = mock
			.expect( exceptionA )
			.expect( exceptionB )
			.expect( exceptionC );
		// Act
		run( mock, exceptionA );
		run( mock, exceptionB );
		run( mock, exceptionC );
		// Assert
		mock.validate();
		Assertions.assertSame( mock, expectOutput );
	}

	@Test
	void unexpected() {
		// Arrange
		RunnableMockishFailing mock = new RunnableMockishFailing();
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
		RuntimeException exceptionA = randomException();
		RuntimeException exceptionB = randomException();
		RunnableMockishFailing mock = new RunnableMockishFailing();
		RunnableMockishFailing expectOutput = mock
			.expect( exceptionA )
			.expect( exceptionB );
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

	private void run( RunnableMockishFailing mock, RuntimeException exception ) {
		try {
			mock.run();
			Assertions.fail();
		}
		catch ( RuntimeException e ) {
			Assertions.assertSame( exception, e );
		}
	}

	private void assertErrorMessage( String expected, Throwable e ) {
		System.out.println( "Expected error: " + e.getMessage() );
		Assertions.assertEquals(
			expected,
			e.getMessage().substring( 0, expected.length() )
		);
	}

	private RuntimeException randomException() {
		return new RuntimeException( UUID.randomUUID().toString() );
	}

}
