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

package com.herbmarshall.base.diff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.UUID;
import java.util.stream.Stream;

import static com.herbmarshall.base.diff.DiffGeneratorDefault.DEFAULT_MESSAGE;
import static com.herbmarshall.base.diff.DiffGeneratorDefault.INSTANCE;

final class DiffGeneratorDefaultTest {

	/** TODO Remove this test on 2.0 <a href="https://herbmarshall.atlassian.net/browse/UTIL-353">UTIL-353</a>. */
	@TestFactory
	Stream<DynamicTest> default_quantify() {
		String actual = randomString();
		String expected = randomString();
		return Stream.of(
			default_quantify( "both null",  null,     null,             1.0 ),
			default_quantify( "left null",  null,     actual,           0.0 ),
			default_quantify( "right null", expected, null,             0.0 ),
			default_quantify( "wrong type", expected, new Object(),     0.0 ),
			default_quantify( "different",  expected, actual,           0.0 ),
			default_quantify( "same",       expected, copy( expected ), 1.0 )
		);
	}

	private DynamicTest default_quantify( String name, Object expected, Object actual, double expectedOutput ) {
		return DynamicTest.dynamicTest( name, () -> {
			// Arrange
			DiffVisualizer.setGenerator( ( e, a ) -> {
				throw new UnsupportedOperationException();
			} );
			// Act
			double output = DiffVisualizer.quantify( expected, actual );
			// Assert
			Assertions.assertEquals( expectedOutput, output );
		} );
	}

	@TestFactory
	Stream<DynamicTest> diff() {
		String actual = randomString();
		String expected = randomString();
		return Stream.of(
			diff( "both null",  null,     null             ),
			diff( "left null",  null,     actual           ),
			diff( "right null", expected, null             ),
			diff( "wrong type", expected, new Object()     ),
			diff( "different",  expected, actual           ),
			diff( "same",       expected, copy( expected ) )
		);
	}

	private DynamicTest diff( String name, Object expected, Object actual ) {
		return DynamicTest.dynamicTest( name, () -> {
			// Arrange
			// Act
			String output = INSTANCE.diff( expected, actual );
			// Assert
			Assertions.assertEquals( DEFAULT_MESSAGE, output );
		} );
	}

	@TestFactory
	Stream<DynamicTest> quantify() {
		String actual = randomString();
		String expected = randomString();
		return Stream.of(
			quantify( "both null",  null,     null,             1.0 ),
			quantify( "left null",  null,     actual,           0.0 ),
			quantify( "right null", expected, null,             0.0 ),
			quantify( "wrong type", expected, new Object(),     0.0 ),
			quantify( "different",  expected, actual,           0.0 ),
			quantify( "same",       expected, copy( expected ), 1.0 )
		);
	}

	private DynamicTest quantify( String name, Object expected, Object actual, double expectedOutput ) {
		return DynamicTest.dynamicTest( name, () -> {
			// Arrange
			// Act
			double output = INSTANCE.quantify( expected, actual );
			// Assert
			Assertions.assertEquals( expectedOutput, output );
		} );
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

	private String copy( String value ) {
		//noinspection StringOperationCanBeSimplified
		return new String( value );
	}

}
