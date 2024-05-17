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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.herbmarshall.base.diff.DiffGeneratorDefault.DEFAULT_MESSAGE;

final class DiffVisualizerTest {

	@Nested
	class generate {

		@TestFactory
		Stream<DynamicTest> defaultValue() {
			String actual = randomString();
			String expected = randomString();
			return Stream.of(
				defaultValue( "Both null",     null,         null             ),
				defaultValue( "Null Actual",   expected,     null             ),
				defaultValue( "Null expected", null,         actual           ),
				defaultValue( "Wrong Type",    randomUUID(), actual           ),
				defaultValue( "Different",     expected,     actual           ),
				defaultValue( "Same ref",      expected,     expected         ),
				defaultValue( "Same ref",      expected,     copy( expected ) )
			);
		}

		private DynamicTest defaultValue( String name, Object expected, Object actual ) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				DiffVisualizer.setGenerator( null );
				// Act
				String output = DiffVisualizer.generate( expected, actual );
				// Assert
				Assertions.assertEquals( DEFAULT_MESSAGE, output );
			} );
		}

		@TestFactory
		Stream<DynamicTest> custom() {
			String actual = randomString();
			String expected = randomString();
			return Stream.of(
				custom( "Both null",     null,         null             ),
				custom( "Null Actual",   expected,     null             ),
				custom( "Null expected", null,         actual           ),
				custom( "Wrong Type",    randomUUID(), actual           ),
				custom( "Different",     expected,     actual           ),
				custom( "Same ref",      expected,     expected         ),
				custom( "Same ref",      expected,     copy( expected ) )
			);
		}

		private DynamicTest custom( String name, Object expected, Object actual ) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				DiffGeneratorStub generator = new DiffGeneratorStub();
				DiffVisualizer.setGenerator( generator );
				String expectedOutput = randomString() + expected + actual;
				generator.addOutput( expectedOutput );
				// Act
				String output = DiffVisualizer.generate( expected, actual );
				// Assert
				Assertions.assertEquals( expectedOutput, output );
				generator.validateDiff( wrap( expected ), wrap( actual ) );
			} );
		}

	}

	@Nested
	class quantify {

		@TestFactory
		Stream<DynamicTest> defaultValue() {
			String actual = randomString();
			String expected = randomString();
			return Stream.of(
				defaultValue( "Both null",     null,         null,             1.0 ),
				defaultValue( "Null Actual",   expected,     null,             0.0 ),
				defaultValue( "Null expected", null,         actual,           0.0 ),
				defaultValue( "Wrong Type",    randomUUID(), actual,           0.0 ),
				defaultValue( "Different",     expected,     actual,           0.0 ),
				defaultValue( "Same ref",      expected,     expected,         1.0 ),
				defaultValue( "Same value",    expected,     copy( expected ), 1.0 )
			);
		}

		private DynamicTest defaultValue( String name, Object expected, Object actual, double expectedOutput ) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				DiffVisualizer.setGenerator( null );
				// Act
				double output = DiffVisualizer.quantify( expected, actual );
				// Assert
				Assertions.assertEquals( expectedOutput, output );
			} );
		}

		@TestFactory
		Stream<DynamicTest> custom() {
			String actual = randomString();
			String expected = randomString();
			return Stream.of(
				custom( "Both null",     null,         null             ),
				custom( "Null Actual",   expected,     null             ),
				custom( "Null expected", null,         actual           ),
				custom( "Wrong Type",    randomUUID(), actual           ),
				custom( "Different",     expected,     actual           ),
				custom( "Same ref",      expected,     expected         ),
				custom( "Same ref",      expected,     copy( expected ) )
			);
		}

		private DynamicTest custom( String name, Object expected, Object actual ) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				DiffGeneratorStub generator = new DiffGeneratorStub();
				DiffVisualizer.setGenerator( generator );
				double expectedOutput = randomDouble();
				generator.addOutput( expectedOutput );
				// Act
				double output = DiffVisualizer.quantify( expected, actual );
				// Assert
				Assertions.assertEquals( expectedOutput, output );
				generator.validateQuantify( wrap( expected ), wrap( actual ) );
			} );
		}

	}

	private List<Object> wrap( Object obj ) {
		List<Object> list = new ArrayList<>();
		list.add( obj );
		return list;
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

	private String copy( String value ) {
		//noinspection StringOperationCanBeSimplified
		return new String( value );
	}

	private UUID randomUUID() {
		return UUID.randomUUID();
	}

	private double randomDouble() {
		return Math.random();
	}

}
