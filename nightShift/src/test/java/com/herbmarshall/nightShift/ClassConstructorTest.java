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

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Stream;

final class ClassConstructorTest {

	private static final Set<Class<?>> classes = Set.of(
		Object.class,
		Integer.class,
		String.class,
		List.class
	);

	@Nested
	class construct {

		@Test
		void real_null() {
			// Arrange
			// Act
			try {
				//noinspection ResultOfMethodCallIgnored
				ClassConstructor.expose( null );
				Assertions.fail();
			}
			// Arrange
			catch ( NullPointerException npe ) {
				System.out.println( "Pass: " + npe.getMessage() );
			}
		}

	}

	@Nested
	class getInstance {

		@TestFactory
		Stream<DynamicTest> tests() {
			return Stream.of(
				happy( "empty", StubEmpty.class ),
				happy( "primitive", StubPrimary.class, randomInt() ),
				happy( "object", StubObject.class, randomUuid() ),
				happy( "generic", StubGeneric.class, randomList() ),
				happy( "variable empty", StubVariable.class, ( Object ) toArray() ),
				happy( "variable single", StubVariable.class, ( Object ) toArray( randomUuid() ) ),
				happy( "variable multiple", StubVariable.class, ( Object ) toArray( randomUuid(), randomUuid() ) ),
				sad(
					"wrong type",
					StubObject.class,
					ClassConstructor::error_constructorError,
					Instant.now()
				),
				sad(
					"failing",
					StubFailing.class,
					ClassConstructor::error_constructorError
				),
				sad(
					"multiple",
					StubMultiple.class,
					ClassConstructor::error_multipleConstructors
				),
				sad(
					"private",
					StubPrivate.class,
					ClassConstructor::error_missingConstructor
				)
			);
		}

		private <T> DynamicTest happy( String name, Class<T> type, Object... arguments ) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				ClassConstructor<T> wrapper = ClassConstructor.expose( type );
				List<Object> argumentList = Arrays.asList( arguments );
				// Act
				T output = wrapper.conjure( argumentList );
				// Assert
				Assertions.assertNotNull( output );
			} );
		}

		private <T> DynamicTest sad(
			String name,
			Class<T> type,
			Function<Class<T>, String> message,
			Object... arguments
		) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				ClassConstructor<T> wrapper = ClassConstructor.expose( type );
				List<Object> argumentList = Arrays.asList( arguments );
				// Act
				try {
					wrapper.conjure( argumentList );
					Assertions.fail();
				}
				// Assert
				catch ( IllegalStateException error ) {
					Assertions.assertEquals(
						message.apply( type ),
						error.getMessage()
					);
				}
			} );
		}

	}

	// *** Happy ***

	private static final class StubEmpty {
		StubEmpty() {}
	}

	private static final class StubPrimary {
		StubPrimary( int x ) {}
	}

	private static final class StubObject {
		StubObject( UUID x ) {}
		/** Here to show private constructors are ignored. */
		private StubObject( UUID x, int y ) {}
	}

	private static final class StubGeneric {
		StubGeneric( List<UUID> x ) {}
	}

	private static final class StubVariable {
		StubVariable( UUID... x ) {}
	}

	// *** Sad ***

	private static final class StubMultiple {
		StubMultiple() {}
		StubMultiple( UUID x ) {}
	}

	private static final class StubPrivate {
		private StubPrivate() {}
	}

	private static final class StubFailing {
		StubFailing() {
			throw new RuntimeException();
		}
	}

	// *** Helpers ***

	private UUID[] toArray( UUID... values ) {
		return Stream.of( values ).toList().toArray( new UUID[ 0 ] );
	}

	private int randomInt() {
		return ThreadLocalRandom.current().nextInt();
	}

	private String randomString() {
		return randomUuid().toString();
	}

	private UUID randomUuid() {
		return UUID.randomUUID();
	}

//	private List<UUID> randomList() {
//		return List.of( randomUuid(), randomUuid(), randomUuid() );
//	}
	private List<String> randomList() {
		return List.of( randomString(), randomString(), randomString() );
	}

}
