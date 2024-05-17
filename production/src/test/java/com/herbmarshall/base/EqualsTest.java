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

package com.herbmarshall.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

final class EqualsTest {

	@Nested
	class evaluate {

		@TestFactory
		Stream<DynamicTest> pojo() {
			return runTest(
				"pojo",
				EqualsTest::randomUUID,
				EqualsTest::copy
			);
		}

		@TestFactory
		Stream<DynamicTest> array() {
			return runTest(
				"array",
				EqualsTest::randomArray,
				EqualsTest::copy
			);
		}

		private <T> Stream<DynamicTest> runTest( String baseName, Supplier<T> supplier, UnaryOperator<T> copy ) {
			T same = supplier.get();
			return Stream.of(
				runTest( baseName, "both null",  null,           null,               true  ),
				runTest( baseName, "left null",  null,           supplier.get(),     false ),
				runTest( baseName, "right null", supplier.get(), null,               false ),
				runTest( baseName, "wrong type", supplier.get(), new Object(),       false ),
				runTest( baseName, "different",  supplier.get(), supplier.get(),     false ),
				runTest( baseName, "same ref",   same,           same,               true  ),
				runTest( baseName, "same value", same,           copy.apply( same ), true  )
			);
		}

		private DynamicTest runTest( String basename, String name, Object a, Object b, boolean expected ) {
			return DynamicTest.dynamicTest( basename + " - " + name, () -> {
				// Arrange
				// Act
				boolean output = Equals.evaluate( a, b );
				// Assert
				Assertions.assertEquals( expected, output );
			} );
		}

	}

	private static UUID randomUUID() {
		return UUID.randomUUID();
	}

	private static UUID[] randomArray() {
		return new UUID[] {
			randomUUID(),
			randomUUID(),
			randomUUID()
		};
	}

	private static UUID copy( UUID source ) {
		return UUID.fromString( source.toString() );
	}

	private static UUID[] copy( UUID[] source ) {
		return Arrays.copyOf( source, source.length );
	}

}
