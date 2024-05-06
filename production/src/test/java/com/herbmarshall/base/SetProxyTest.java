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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

class SetProxyTest {

	private final Random random = new Random();

	SetMock<Object> mock;
	SetProxy<Object> proxy;

	@BeforeEach
	void setup() {
		mock = new SetMock<>();
		proxy = new SetProxy<>( mock ) {};
	}

	@Test
	void size() {
		// Arrange
		int expected = randomInt();
		mock.size_output = expected;
		Assertions.assertFalse( mock.size_called );
		// Act
		int output = proxy.size();
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertTrue( mock.size_called );
	}

	@Test
	void isEmpty() {
		// Arrange
		boolean expected = randomBoolean();
		mock.isEmpty_output = expected;
		Assertions.assertFalse( mock.isEmpty_called );
		// Act
		boolean output = proxy.isEmpty();
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertTrue( mock.isEmpty_called );
	}

	@Test
	void contains() {
		// Arrange
		Object input = randomObject();
		boolean expected = randomBoolean();
		mock.contains_output = expected;
		Assertions.assertNull( mock.contains_input );
		Assertions.assertFalse( mock.contains_called );
		// Act
		boolean output = proxy.contains( input );
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertSame( input, mock.contains_input );
		Assertions.assertTrue( mock.contains_called );
	}

	@Test
	void iterator() {
		// Arrange
		Iterator<Object> expected = new IteratorStub<>();
		mock.iterator_output = expected;
		Assertions.assertFalse( mock.iterator_called );
		// Act
		Iterator<Object> output = proxy.iterator();
		// Assert
		Assertions.assertSame( expected, output );
		Assertions.assertTrue( mock.iterator_called );
	}

	@Test
	void toArray() {
		// Arrange
		Object[] expected = new Object[] { randomObject(), randomObject() };
		mock.toArray_output = expected;
		Assertions.assertNull( mock.toArray_input );
		Assertions.assertFalse( mock.toArray_called );
		// Act
		Object[] output = proxy.toArray();
		// Assert
		Assertions.assertSame( expected, output );
		Assertions.assertNull( mock.toArray_input );
		Assertions.assertTrue( mock.toArray_called );
	}

	@Test
	void toArray_input() {
		// Arrange
		Object[] input = new Object[] { randomObject(), randomObject() };
		Object[] expected = new Object[] { randomObject(), randomObject() };
		mock.toArray_output = expected;
		Assertions.assertNull( mock.toArray_input );
		Assertions.assertFalse( mock.toArray_called );
		// Act
		Object[] output = proxy.toArray( input );
		// Assert
		Assertions.assertSame( expected, output );
		Assertions.assertSame( input, mock.toArray_input );
		Assertions.assertTrue( mock.toArray_called );
	}

	@Test
	void add() {
		// Arrange
		Object input = randomObject();
		boolean expected = randomBoolean();
		mock.add_output = expected;
		Assertions.assertNull( mock.add_input );
		Assertions.assertFalse( mock.add_called );
		// Act
		boolean output = proxy.add( input );
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertSame( input, mock.add_input );
		Assertions.assertTrue( mock.add_called );
	}

	@Test
	void remove() {
		// Arrange
		Object input = randomObject();
		boolean expected = randomBoolean();
		mock.remove_output = expected;
		Assertions.assertNull( mock.remove_input );
		Assertions.assertFalse( mock.remove_called );
		// Act
		boolean output = proxy.remove( input );
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertSame( input, mock.remove_input );
		Assertions.assertTrue( mock.remove_called );
	}

	@Test
	void containsAll() {
		// Arrange
		Collection<?> input = Set.of( randomObject(), randomObject()  );
		boolean expected = randomBoolean();
		mock.containsAll_output = expected;
		Assertions.assertNull( mock.containsAll_input );
		Assertions.assertFalse( mock.containsAll_called );
		// Act
		boolean output = proxy.containsAll( input );
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertSame( input, mock.containsAll_input );
		Assertions.assertTrue( mock.containsAll_called );
	}

	@Test
	void addAll() {
		// Arrange
		Collection<Object> input = Set.of( randomObject(), randomObject()  );
		boolean expected = randomBoolean();
		mock.addAll_output = expected;
		Assertions.assertNull( mock.addAll_input );
		Assertions.assertFalse( mock.addAll_called );
		// Act
		boolean output = proxy.addAll( input );
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertSame( input, mock.addAll_input );
		Assertions.assertTrue( mock.addAll_called );
	}

	@Test
	void retainAll() {
		// Arrange
		Collection<Object> input = Set.of( randomObject(), randomObject()  );
		boolean expected = randomBoolean();
		mock.retainAll_output = expected;
		Assertions.assertNull( mock.retainAll_input );
		Assertions.assertFalse( mock.retainAll_called );
		// Act
		boolean output = proxy.retainAll( input );
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertSame( input, mock.retainAll_input );
		Assertions.assertTrue( mock.retainAll_called );
	}

	@Test
	void removeAll() {
		// Arrange
		Collection<Object> input = Set.of( randomObject(), randomObject()  );
		boolean expected = randomBoolean();
		mock.removeAll_output = expected;
		Assertions.assertNull( mock.removeAll_input );
		Assertions.assertFalse( mock.removeAll_called );
		// Act
		boolean output = proxy.removeAll( input );
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertSame( input, mock.removeAll_input );
		Assertions.assertTrue( mock.removeAll_called );
	}

	@Test
	void clear() {
		// Arrange
		Assertions.assertFalse( mock.clear_called );
		// Act
		proxy.clear();
		// Assert
		Assertions.assertTrue( mock.clear_called );
	}

	@Test
	void equals() {
		// Arrange
		Object input = randomObject();
		boolean expected = randomBoolean();
		mock.equals_output = expected;
		Assertions.assertNull( mock.equals_input );
		Assertions.assertFalse( mock.equals_called );
		// Act
		boolean output = proxy.equals( input );
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertSame( input, mock.equals_input );
		Assertions.assertTrue( mock.equals_called );
	}

	@Test
	void hashCode_test() {
		// Arrange
		int expected = randomInt();
		mock.hashCode_output = expected;
		Assertions.assertFalse( mock.hashCode_called );
		// Act
		int output = proxy.hashCode();
		// Assert
		Assertions.assertEquals( expected, output );
		Assertions.assertTrue( mock.hashCode_called );
	}

	private boolean randomBoolean() {
		return random.nextBoolean();
	}

	private int randomInt() {
		return random.nextInt();
	}

	private Object randomObject() {
		return UUID.randomUUID();
	}

}
