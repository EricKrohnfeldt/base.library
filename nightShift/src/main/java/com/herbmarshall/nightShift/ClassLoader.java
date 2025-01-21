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

import java.util.Objects;
import java.util.function.Function;

final class ClassLoader implements Function<String, Class<?>> {

	private final java.lang.ClassLoader loader = Thread.currentThread().getContextClassLoader();

	@Override
	public Class<?> apply( String target ) {
		try {
			return loader.loadClass( Objects.requireNonNull( target ) );
		}
		catch ( ClassNotFoundException exception ) {
			throw new RuntimeException(
				error_badClassName( target ),
				exception
			);
		}
	}

	static String error_badClassName( String name ) {
		return "Cannot load class with name " + name;
	}

}
