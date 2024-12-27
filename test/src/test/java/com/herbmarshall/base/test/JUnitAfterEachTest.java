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

package com.herbmarshall.base.test;

import com.herbmarshall.base.mock.function.RunnableMockish;
import com.herbmarshall.base.mock.function.RunnableMockishFailing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.herbmarshall.base.test.JUnitAfterEach.error_duplicate;
import static com.herbmarshall.base.test.JUnitAfterEach.error_unknown;

final class JUnitAfterEachTest {

	@Nested
	class addCallback {

		@Test
		void happyPath() {
			// Arrange
			RunnableMockish callback = new RunnableMockish( false );
			try {
				JUnitAfterEach.addCallback( callback.expect() );
				// Act
				afterEach();
				// Assert
				callback.validate();
			}
			finally {
				JUnitAfterEach.removeCallback( callback );
			}
		}

		@Test
		void failingCallback() {
			// Arrange
			RuntimeException exception = randomError();
			RunnableMockishFailing callback = new RunnableMockishFailing( false )
				.expect( exception );
			try {
				JUnitAfterEach.addCallback( callback );
				// Act
				afterEach();
				Assertions.fail();
			}
			// Assert
			catch ( RuntimeException e ) {
				Assertions.assertSame( exception, e );
			}
			finally {
				JUnitAfterEach.removeCallback( callback );
			}
		}

		@Test
		void duplicateCallback() {
			// Arrange
			RunnableMockish callback = new RunnableMockish();
			JUnitAfterEach.addCallback( callback );
			// Act
			try {
				JUnitAfterEach.addCallback( callback );
				Assertions.fail();
			}
			// Assert
			catch ( IllegalStateException e ) {
				assertErrorMessage(
					error_duplicate( callback ),
					e
				);
			}
			finally {
				JUnitAfterEach.removeCallback( callback );
			}
		}

		@Test
		void nullCallback() {
			// Act
			try {
				JUnitAfterEach.addCallback( null );
				Assertions.fail();
			}
			// Assert
			catch ( NullPointerException ignored ) {
			}
		}

	}

	@Nested
	class removeCallback {

		@Test
		void happyPath() {
			// Arrange
			RunnableMockish callback = new RunnableMockish();
			JUnitAfterEach.addCallback( callback );
			// Act
			JUnitAfterEach.removeCallback( callback );
			// Assert
			// No exceptions
		}

		@Test
		void unknown() {
			// Arrange
			RunnableMockish callback = new RunnableMockish();
			// Act
			try {
				JUnitAfterEach.removeCallback( callback );
				Assertions.fail();
			}
			// Assert
			catch ( IllegalStateException e ) {
				assertErrorMessage(
					error_unknown( callback ),
					e
				);
			}
		}

		@Test
		void nullCallback() {
			// Act
			try {
				JUnitAfterEach.removeCallback( null );
				Assertions.fail();
			}
			// Assert
			catch ( NullPointerException ignored ) {
			}
		}

	}

	// Using this private method because I don't want to repeat the comment about null everywhere.  Yea, I'm high.
	private void afterEach() {
		new JUnitAfterEach().afterEach( null );  // Using null here as we don't use context parameter
	}

	private void assertErrorMessage( String expected, Throwable e ) {
		System.out.println( "Expected error: " + e.getMessage() );
		Assertions.assertEquals(
			expected,
			e.getMessage().substring( 0, expected.length() )
		);
	}

	private RuntimeException randomError() {
		return new RuntimeException( UUID.randomUUID().toString() );
	}

}
