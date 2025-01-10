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

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.herbmarshall.nightShift.ClassScan.DEFAULT_PACKAGE;

@SuppressWarnings( "FieldCanBeLocal" )
final class ClassScanTest {

	private final String package_happyPath = "com.herbmarshall.nightShift.test.happyPath";
	private final Set<String> class_happyPath = Set.of(
		"com.herbmarshall.nightShift.test.happyPath.AutomatedClass"
	);

	private final String package_happyPath2 = "com.herbmarshall.nightShift.test.happyPath2";
	private final Set<String> class_happyPath2 = Set.of(
		"com.herbmarshall.nightShift.test.happyPath2.AutomatedClass2"
	);

	private final String package_doesNotExist = "com.herbmarshall.nightShift.test.doesNotExist";
	private final String package_doesNotExist2 = "com.herbmarshall.nightShift.test.doesNotExist2";

	private final Set<String> class_ALL = Stream.of(
		class_happyPath,
		class_happyPath2
	)
		.flatMap( Collection::stream )
		.collect( Collectors.toUnmodifiableSet() );

	@Nested
	class scan_ {

		@Test
		void noArguments() {
			// Arrange
			// Act
			try ( ClassScan output = ClassScan.scan() ) {
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
	class create_strings {

		@Test
		void happyPath() {
			// Arrange
			String packageName = randomString();
			// Act
			try ( ClassScan output = ClassScan.scan( packageName ) ) {
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
			try ( ClassScan output = ClassScan.scan( packageA, packageB ) ) {
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
			try ( ClassScan scan = ClassScan.scan( ( String[] ) null ) ) {
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
			try ( ClassScan scan = ClassScan.scan( randomString(), null, randomString() ) ) {
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
			return safeClose( "happyPath", package_happyPath, scan -> {
				// Act
				Stream<String> output = scan.getAutomated();
				// Assert
				Assertions.assertEquals(
					class_happyPath,
					streamToSet( output )
				);
			} );
		}

		@TestFactory
		DynamicTest none() {  // Will result in same call as no arg
			// Arrange
			return safeClose( "none", Set.of(), scan -> {
				// Act
				Stream<String> output = scan.getAutomated();
				// Assert
				Assertions.assertEquals(
					class_ALL,
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
					package_happyPath,
					package_happyPath2,
					package_doesNotExist
				),
				scan -> {
					// Act
					Stream<String> output = scan.getAutomated();
					// Assert
					Assertions.assertEquals(
						Stream.of( class_happyPath, class_happyPath2 )
							.flatMap( Collection::stream )
							.collect( Collectors.toUnmodifiableSet() ),
						streamToSet( output )
					);
				}
			);
		}

		@TestFactory
		DynamicTest bad() {
			// Arrange
			return safeClose( "bad", package_doesNotExist, scan -> {
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
					package_doesNotExist,
					package_doesNotExist2
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

	private static DynamicTest safeClose( String name, String targetPackage, Consumer<ClassScan> test ) {
		return safeClose( name, Set.of( targetPackage ), test );
	}

	private static DynamicTest safeClose( String name, Set<String> targetPackage, Consumer<ClassScan> test ) {
		return DynamicTest.dynamicTest( name, () -> {
			try ( ClassScan scan = ClassScan.scan( targetPackage.toArray( new String[ 0 ] ) ) ) {
				test.accept( scan );
			}
		} );
	}

	private <E> Set<E> streamToSet( Stream<E> stream ) {
		return stream.collect( Collectors.toUnmodifiableSet() );
	}

	private static String randomString() {
		return UUID.randomUUID().toString();
	}

}
