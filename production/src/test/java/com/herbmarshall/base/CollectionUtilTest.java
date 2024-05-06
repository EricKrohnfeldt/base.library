/*
 * XThis file is part of herbmarshall.com: base.library  ( hereinafter "base.library" ).
 *
 * base.library is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any
 * later version.
 *
 * base.library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with base.library.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.herbmarshall.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

class CollectionUtilTest {

	@Nested
	class merge_set {

		@Test
		void happyPath() {
			// Arrange
			Object a = random();
			Object b = random();
			Object c = random();
			Set<Object> setA = Set.of( a, b );
			Set<Object> setB = Set.of( c );
			// Act
			Set<Object> output = CollectionUtil.merge( setA, setB );
			// Assert
			Assertions.assertEquals( Set.of( a, b, c ), output );
		}

		@Test
		void emptyA() {
			// Arrange
			Object a = random();
			Object b = random();
			Set<Object> setA = Set.of();
			Set<Object> setB = Set.of( a, b );
			// Act
			Set<Object> output = CollectionUtil.merge( setA, setB );
			// Assert
			Assertions.assertEquals( Set.of( a, b ), output );
			Assertions.assertNotSame( setB, output );
		}

		@Test
		void emptyB() {
			// Arrange
			Object a = random();
			Object b = random();
			Set<Object> setA = Set.of( a, b );
			Set<Object> setB = Set.of();
			// Act
			Set<Object> output = CollectionUtil.merge( setA, setB );
			// Assert
			Assertions.assertEquals( Set.of( a, b ), output );
			Assertions.assertNotSame( setA, output );
		}

		@Test
		void nullA() {
			// Arrange
			Set<Object> set = Set.of( random(), random() );
			// Act
			try {
				CollectionUtil.merge( null, set );
			}
			// Assert
			catch ( NullPointerException ignored ) {
			}
		}

		@Test
		void nullB() {
			// Arrange
			Set<Object> set = Set.of( random(), random() );
			// Act
			try {
				CollectionUtil.merge( set, null );
			}
			// Assert
			catch ( NullPointerException ignored ) {
			}
		}

	}

	@Nested
	class merge_list {

		@Test
		void happyPath() {
			// Arrange
			Object a = random();
			Object b = random();
			Object c = random();
			List<Object> listA = List.of( a, b );
			List<Object> listB = List.of( c );
			// Act
			List<Object> output = CollectionUtil.merge( listA, listB );
			// Assert
			Assertions.assertEquals( List.of( a, b, c ), output );
		}

		@Test
		void emptyA() {
			// Arrange
			Object a = random();
			Object b = random();
			List<Object> listA = List.of();
			List<Object> listB = List.of( a, b );
			// Act
			List<Object> output = CollectionUtil.merge( listA, listB );
			// Assert
			Assertions.assertEquals( List.of( a, b ), output );
			Assertions.assertNotSame( listB, output );
		}

		@Test
		void emptyB() {
			// Arrange
			Object a = random();
			Object b = random();
			List<Object> listA = List.of( a, b );
			List<Object> listB = List.of();
			// Act
			List<Object> output = CollectionUtil.merge( listA, listB );
			// Assert
			Assertions.assertEquals( List.of( a, b ), output );
			Assertions.assertNotSame( listA, output );
		}

		@Test
		void nullA() {
			// Arrange
			List<Object> list = List.of( random(), random() );
			// Act
			try {
				CollectionUtil.merge( null, list );
			}
			// Assert
			catch ( NullPointerException ignored ) {
			}
		}

		@Test
		void nullB() {
			// Arrange
			List<Object> list = List.of( random(), random() );
			// Act
			try {
				CollectionUtil.merge( list, null );
			}
			// Assert
			catch ( NullPointerException ignored ) {
			}
		}

	}

	@Nested
	class last_list {

		@Test
		void happyPath() {
			// Arrange
			Object itemA = random();
			Object itemB = random();
			Object itemC = random();
			List<Object> list = List.of( itemA, itemB, itemC );
			// Act
			Optional<Object> output = CollectionUtil.last( list );
			// Assert
			Assertions.assertTrue( output.isPresent() );
			Assertions.assertSame( itemC, output.get() );
		}

		@Test
		void emptyList() {
			// Arrange
			List<Object> list = List.of();
			// Act
			Optional<Object> output = CollectionUtil.last( list );
			// Assert
			Assertions.assertTrue( output.isEmpty() );
		}

		@Test
		void nullElement() {
			// Arrange
			List<Object> list = new ArrayList<>();
			list.add( null );
			// Act
			Optional<Object> output = CollectionUtil.last( list );
			// Assert
			Assertions.assertTrue( output.isEmpty() );
		}

	}

	@Nested
	class last_collection {

		@Test
		void happyPath() {
			// Arrange
			Object itemA = random();
			Object itemB = random();
			Object itemC = random();
			Collection<Object> collection = new LinkedHashSet<>( List.of( itemA, itemB, itemC ) );
			// Act
			Optional<Object> output = CollectionUtil.last( collection );
			// Assert
			Assertions.assertTrue( output.isPresent() );
			Assertions.assertSame( itemC, output.get() );
		}

		@Test
		void emptyList() {
			// Arrange
			Collection<Object> collection = Set.of();
			// Act
			Optional<Object> output = CollectionUtil.last( collection );
			// Assert
			Assertions.assertTrue( output.isEmpty() );
		}

		@Test
		void nullElement() {
			// Arrange
			Collection<Object> collection = new HashSet<>();
			collection.add( null );
			// Act
			Optional<Object> output = CollectionUtil.last( collection );
			// Assert
			Assertions.assertTrue( output.isEmpty() );
		}

	}

	private Object random() {
		return UUID.randomUUID();
	}

}
