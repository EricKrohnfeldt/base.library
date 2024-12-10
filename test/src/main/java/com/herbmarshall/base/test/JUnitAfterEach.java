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

package com.herbmarshall.base.test;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** Class to execute an 'after each' method for each JUnit test. */
public final class JUnitAfterEach implements AfterEachCallback {

	private static final Set<Runnable> callbacks = new LinkedHashSet<>();

	/**
	 * Add a callback to execute after each unit test.
	 * All calls will be executed in insertion order.
	 * @param callback The callback to execute
	 * @throws IllegalStateException If callback is registered more than once
	 */
	public static void addCallback( Runnable callback ) {
		if ( ! callbacks.add( Objects.requireNonNull( callback ) ) )
			throw new IllegalStateException( error_duplicate( callback ) );
	}

	/**
	 * Remove a callback from the execute plan.
	 * @param callback The callback to remove
	 * @throws IllegalStateException If callback not registered
	 */
	public static void removeCallback( Runnable callback ) {
		if ( ! callbacks.remove( Objects.requireNonNull( callback ) ) )
			throw new IllegalStateException( error_unknown( callback ) );
	}

	@Override
	public void afterEach( ExtensionContext context ) {
		callbacks.forEach( Runnable::run );
	}

	static String error_duplicate( Runnable callback ) {
		return "Duplicate callbacks registered; Offender: %s".formatted( callback );
	}

	static String error_unknown( Runnable callback ) {
		return "Cannot remove unknown callback; Offender: %s".formatted( callback );
	}

}
