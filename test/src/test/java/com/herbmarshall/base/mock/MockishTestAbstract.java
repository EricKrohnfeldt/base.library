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

package com.herbmarshall.base.mock;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Trying to simulate 'protected' interface.
 * Used to keep this java doc in one place.
 * Am Hi.
 * @param <M> The {@link Mockish} type to test.
 */
abstract sealed class MockishTestAbstract<M extends Mockish>
	permits
		MockishTest,
		MockishFailingTest {

	/**
	 * Return a new map of constructor ( auto validating ) to constructor name.
	 * <p>
	 *     Example:
	 *     {@code Map.of( RunnableMockish::new, "No Arg", () -> new RunnableMockish( true ), "Boolean Arg" );}
	 * </p>
	 * @see Mockish
	 */
	protected abstract Map<Supplier<M>, String> constructAuto();

	/**
	 * Return a new map of constructor ( manual validating ) to constructor name.
	 * <p>Example {@code Map.of( () -> new RunnableMockish( false ), "Boolean Arg" );}</p>
	 * @see Mockish
	 */
	protected abstract Map<Supplier<M>, String> constructManual();

}
