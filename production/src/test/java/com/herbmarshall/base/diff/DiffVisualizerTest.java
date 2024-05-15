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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

final class DiffVisualizerTest {

	@TestFactory
	Stream<DynamicTest> defaultDiff() {
		String actual = randomString();
		String expected = randomString();
		return Stream.of(
			defaultDiff( "Same", expected, expected ),
			defaultDiff( "Different", expected, actual ),
			defaultDiff( "Wrong Type", randomUUID(), actual ),
			defaultDiff( "Null Actual", expected, null ),
			defaultDiff( "Null expected", null, actual ),
			defaultDiff( "Both null", null, null )
		);
	}

	private DynamicTest defaultDiff( String name, Object expected, Object actual ) {
		return DynamicTest.dynamicTest( name, () -> {
			// Arrange
			DiffVisualizer.setGenerator( null );
			// Act
			String output = DiffVisualizer.generate( expected, actual );
			// Assert
			Assertions.assertEquals( DiffVisualizer.DEFAULT_MESSAGE, output );
		} );
	}

	@TestFactory
	Stream<DynamicTest> customDiff() {
		String actual = randomString();
		String expected = randomString();
		return Stream.of(
			customDiff( "Same", expected, expected ),
			customDiff( "Different", expected, actual ),
			customDiff( "Wrong Type", randomUUID(), actual ),
			customDiff( "Null Actual", expected, null ),
			customDiff( "Null expected", null, actual ),
			customDiff( "Both null", null, null )
		);
	}

	private DynamicTest customDiff( String name, Object expected, Object actual ) {
		return DynamicTest.dynamicTest( name, () -> {
			// Arrange
			DiffGeneratorStub generator = new DiffGeneratorStub();
			DiffVisualizer.setGenerator( generator );
			String expectedOutput = randomString() + expected  + actual;
			generator.addOuptut( expectedOutput );
			// Act
			String output = DiffVisualizer.generate( expected, actual );
			// Assert
			Assertions.assertEquals( expectedOutput, output );
			generator.validate( wrap( expected ), wrap( actual ) );
		} );
	}

	private List<Object> wrap( Object obj ) {
		List<Object> list = new ArrayList<>();
		list.add( obj );
		return list;
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

	private UUID randomUUID() {
		return UUID.randomUUID();
	}

}
