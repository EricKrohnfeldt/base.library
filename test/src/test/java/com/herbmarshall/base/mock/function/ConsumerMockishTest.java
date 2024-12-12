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

import java.util.List;
import java.util.UUID;

import static com.herbmarshall.base.mock.function.ConsumerMockish.error_uncalled;
import static com.herbmarshall.base.mock.function.ConsumerMockish.error_unexpectedCall;

final class ConsumerMockishTest {

	@Test
	void happyPath() {
		// Arrange
		UUID value = UUID.randomUUID();
		ConsumerMockish<UUID> mock = new ConsumerMockish<>();
		ConsumerMockish<UUID> expectOutput = mock.expect( value );
		// Act
		mock.accept( value );
		mock.validate();
		// Assert
		Assertions.assertSame( mock, expectOutput );
	}

	/** Actual 'called' without 'expects'. */
	@Test
	void unexpectedValue() {
		// Arrange
		UUID value = UUID.randomUUID();
		ConsumerMockish<UUID> mock = new ConsumerMockish<>();
		// Act
		try {
			mock.accept( value );
			Assertions.fail();
		}
		// Assert
		catch ( AssertionError e ) {
			assertErrorMessage(
				error_unexpectedCall( value ),
				e
			);
		}
	}

	@Test
	void nullValue() {
		// Arrange
		ConsumerMockish<UUID> mock = new ConsumerMockish<>();
		ConsumerMockish<UUID> expectOutput = mock.expect( null );
		// Act
		mock.accept( null );
		mock.validate();
		// Assert
		Assertions.assertSame( mock, expectOutput );
	}

	@Test
	void nullValueMultiple() {
		// Arrange
		UUID value = UUID.randomUUID();
		ConsumerMockish<UUID> mock = new ConsumerMockish<>();
		ConsumerMockish<UUID> expectOutput = mock
			.expect( null )
			.expect( null );
		// Act
		mock.accept( null );
		mock.accept( null );
		mock.validate();
		// Assert
		Assertions.assertSame( mock, expectOutput );
	}

	/** Expects but no 'called'. */
	@Test
	void validationFailed() {
		// Arrange
		UUID valueA = UUID.randomUUID();
		UUID valueB = UUID.randomUUID();
		ConsumerMockish<UUID> mock = new ConsumerMockish<>();
		ConsumerMockish<UUID> expectOutput = mock
			.expect( valueA )
			.expect( valueB );
		// Act
		try {
			mock.validate();
			Assertions.fail();
		}
		// Assert
		catch ( AssertionError e ) {
			assertErrorMessage(
				error_uncalled(
					List.of( valueA, valueB )
				),
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
