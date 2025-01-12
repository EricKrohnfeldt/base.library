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
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.herbmarshall.nightShift.ClassScanner.DEFAULT_PACKAGE;
import static com.herbmarshall.nightShift.PackagesForTesting.*;

@SuppressWarnings( "FieldCanBeLocal" )
final class ClassScannerTest {

	@Nested
	class scan_ {

		@Test
		void noArguments() {
			// Arrange
			// Act
			try ( ClassScanner output = ClassScanner.scan() ) {
				// Assert
				Assertions.assertNotNull( output );
				Assertions.assertEquals(
					Set.of( DEFAULT_PACKAGE ),
					streamToSet( output.getPackages() )
				);
			}
		}

	}

	@Nested
	class scan_strings {

		@Test
		void happyPath() {
			// Arrange
			String packageName = randomString();
			// Act
			try ( ClassScanner output = ClassScanner.scan( packageName ) ) {
				// Assert
				Assertions.assertEquals(
					Set.of( packageName ),
					streamToSet( output.getPackages() )
				);
			}
		}

		@Test
		void multiple() {
			// Arrange
			String packageA = randomString();
			String packageB = randomString();
			// Act
			try ( ClassScanner output = ClassScanner.scan( packageA, packageB ) ) {
				// Assert
				Assertions.assertEquals(
					Set.of( packageA, packageB ),
					streamToSet( output.getPackages() )
				);
			}
		}

		@Test
		void packages_null() {
			// Arrange
			// Act
			try ( ClassScanner scan = ClassScanner.scan( ( String[] ) null ) ) {
				Assertions.fail();
			}
			// Assert
			catch ( NullPointerException npe ) {
				System.out.println( "Pass: " + npe.getMessage() );
			}
		}

		@Test
		void multiple_null() {
			// Arrange
			// Act
			try ( ClassScanner scan = ClassScanner.scan( randomString(), null, randomString() ) ) {
				Assertions.fail();
			}
			// Assert
			catch ( NullPointerException npe ) {
				System.out.println( "Pass: " + npe.getMessage() );
			}
		}

	}

	@Nested
	class getAutomated {

		@TestFactory
		DynamicTest happyPath() {
			// Arrange
			return safeClose( "happyPath", happyPath, scan -> {
				// Act
				Stream<String> output = scan.getAutomated();
				// Assert
				Assertions.assertEquals(
					streamToSet( happyPath.getAutomatedNames() ),
					streamToSet( output )
				);
			} );
		}

		@Test
		void none_noArg() {
			// Arrange
			try ( ClassScanner scan = ClassScanner.scan() ) {
				// Act
				Stream<String> output = scan.getAutomated();
				// Assert
				Assertions.assertEquals(
					streamToSet( getAllAutomatedNames() ),
					streamToSet( output )
				);
			}
		}

		@TestFactory
		DynamicTest none() {  // Will result in same call as no arg
			// Arrange
			return safeClose( "none", Set.of(), scan -> {
				// Act
				Stream<String> output = scan.getAutomated();
				// Assert
				Assertions.assertEquals(
					streamToSet( getAllAutomatedNames() ),
					streamToSet( output )
				);
			} );
		}

		@TestFactory
		DynamicTest multiple() {
			// Arrange
			return safeClose(
				"multiple",
				Set.of(
					happyPath,
					happyPath2,
					doesNotExist
				),
				scan -> {
					// Act
					Stream<String> output = scan.getAutomated();
					// Assert
					Assertions.assertEquals(
						Stream.of( happyPath, happyPath2 )
							.flatMap( PackagesForTesting::getAutomatedNames )
							.collect( Collectors.toUnmodifiableSet() ),
						streamToSet( output )
					);
				}
			);
		}

		@TestFactory
		DynamicTest bad() {
			// Arrange
			return safeClose( "bad", doesNotExist, scan -> {
				// Act
				Stream<String> output = scan.getAutomated();
				// Assert
				Assertions.assertEquals(
					Set.of(),
					streamToSet( output )
				);
			} );
		}

		@TestFactory
		DynamicTest multiple_bad() {
			// Arrange
			return safeClose(
				"multiple_bad",
				Set.of(
					doesNotExist,
					doesNotExist2
				),
				scan -> {
					// Act
					Stream<String> output = scan.getAutomated();
					// Assert
					Assertions.assertEquals(
						Set.of(),
						streamToSet( output )
					);
				}
			);
		}

	}

	private static DynamicTest safeClose(
		String name,
		PackagesForTesting targetPackage,
		Consumer<ClassScanner> test
	) {
		return safeClose( name, Set.of( targetPackage ), test );
	}

	private static DynamicTest safeClose(
		String name,
		Set<PackagesForTesting> targetPackages,
		Consumer<ClassScanner> test
	) {
		return DynamicTest.dynamicTest( name, () -> {
			try ( ClassScanner scan = ClassScanner.scan( setToArray( targetPackages ) ) ) {
				test.accept( scan );
			}
		} );
	}

	private static String[] setToArray( Set<PackagesForTesting> targetPackages ) {
		return targetPackages.stream()
			.map( PackagesForTesting::getName )
			.toArray( String[]::new );
	}

	private <E> Set<E> streamToSet( Stream<E> stream ) {
		return stream.collect( Collectors.toUnmodifiableSet() );
	}

	private static String randomString() {
		return UUID.randomUUID().toString();
	}

}
