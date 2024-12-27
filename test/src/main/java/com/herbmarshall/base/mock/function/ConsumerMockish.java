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

package com.herbmarshall.base.mock.function;

import com.herbmarshall.base.mock.Mockish;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A {@link Mockish} for {@link Consumer} interface.
 * @param <T> The type of object to be consumed.
 */
public final class ConsumerMockish<T>
	extends Mockish
	implements Consumer<T> {

	private final List<T> expected = new ArrayList<>();

	/**
	 * Create new instance with {@code autoValidate} turned on.
	 * @see Mockish
	 */
	public ConsumerMockish() {
	}

	/**
	 * Create new instance.
	 * Set {@code autoValidate} on by passing {@code true}, turn off by passing {@code false}.
	 * @see Mockish
	 */
	public ConsumerMockish( boolean autoValidate ) {
		super( autoValidate );
	}

	/** Prepare for expected call. */
	public ConsumerMockish<T> expect( T value ) {
		expected.add( value );
		return this;
	}

	@Override
	public void accept( T t ) {
		Assertions.assertTrue(
			expected.remove( t ),
			error_unexpectedCall( t )
		);
	}

	@Override
	public void validate() {
		Assertions.assertTrue(
			expected.isEmpty(),
			error_uncalled( expected )
		);
	}

	static String error_unexpectedCall( Object value ) {
		return "Did not find expected value: " + value;
	}

	static String error_uncalled( List<?> values ) {
		return "Did not call 'accept' for all expectations: " + values;
	}

}
