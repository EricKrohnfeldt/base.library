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

package com.herbmarshall.base.pair;

import com.herbmarshall.base.data.pair.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

final class PairDefaultTest {

	@Nested
	class getA {

		@Test
		void happyPath() {
			// Arrange
			UUID expected = UUID.randomUUID();
			Pair<UUID, Object> pair = Pair.from(
				expected,
				randomUUID()
			);
			// Act
			UUID output = pair.getA();
			// Assert
			Assertions.assertSame( expected, output );
		}

		@Test
		void nullValue() {
			// Arrange
			Pair<UUID, Object> pair = Pair.from(
				null,
				randomUUID()
			);
			// Act
			UUID output = pair.getA();
			// Assert
			Assertions.assertNull( output );
		}

	}

	@Nested
	class getB {

		@Test
		void happyPath() {
			// Arrange
			UUID expected = UUID.randomUUID();
			Pair<Object, UUID> pair = Pair.from(
				randomUUID(),
				expected
			);
			// Act
			UUID output = pair.getB();
			// Assert
			Assertions.assertSame( expected, output );
		}

		@Test
		void nullValue() {
			// Arrange
			Pair<Object, UUID> pair = Pair.from(
				randomUUID(),
				null
			);
			// Act
			UUID output = pair.getB();
			// Assert
			Assertions.assertNull( output );
		}

	}

	private UUID randomUUID() {
		return UUID.randomUUID();
	}

}
