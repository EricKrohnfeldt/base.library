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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

class ConcurrentHashSetTest {

	private static final int RANDOM_BASIS = 999;
	private final Random random = new Random();

	@Nested
	class constructor_initialCapacity {

		@Test
		void happyPath() {
			// Arrange
			int input = randomPositive();
			// Act / Assert
			Assertions.assertDoesNotThrow(
				() -> new ConcurrentHashSet<>( input )
			);
		}

		@Test
		void zeroCapacity() {
			// Arrange
			// Act / Assert
			Assertions.assertDoesNotThrow(
				() -> new ConcurrentHashSet<>( 0 )
			);
		}

		@Test
		void negativeCapacity() {
			// Arrange
			int input = randomNegative();
			// Act / Assert
			try {
				new ConcurrentHashSet<>( input );
			}
			catch ( IllegalArgumentException ignored ) {
			}
		}

	}

	@Nested
	class constructor_elements {

		@Test
		void happyPath() {
			// Arrange
			Object objA = randomObject();
			Object objB = randomObject();
			List<Object> input = List.of( objA, objB );
			// Act
			ConcurrentHashSet<Object> output = new ConcurrentHashSet<>( input );
			// Assert
			Assertions.assertEquals(
				Set.of( objA, objB ),
				output
			);
		}

		@Test
		void emptyElements() {
			// Arrange
			List<Object> input = List.of();
			// Act
			ConcurrentHashSet<Object> output = new ConcurrentHashSet<>( input );
			// Assert
			Assertions.assertTrue( output.isEmpty() );
		}

		@Test
		void nullElements() {
			// Arrange
			List<Object> input = new ArrayList<>();
			input.add( null );
			// Act
			try {
				new ConcurrentHashSet<>( input );
			}
			// Assert
			catch ( NullPointerException ignored ) {
			}
		}

	}

	private int randomPositive() {
		return random.nextInt( RANDOM_BASIS ) + 1;
	}

	private int randomNegative() {
		return -1 * random.nextInt( RANDOM_BASIS ) + 1;
	}

	private Object randomObject() {
		return UUID.randomUUID();
	}

}
