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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

final class ClassConstructor<C> {

	private final Class<C> real;

	private ClassConstructor( Class<C> real ) {
		this.real = Objects.requireNonNull( real );
	}

//	List<Class<?>> getDependencies() {
//		throw new UnsupportedOperationException();
//	}

	C getInstance( Object... dependencies ) {
		Constructor<C> constructor = getConstructor();
		try {
			return constructor.newInstance( dependencies );
		}
		catch (
			InstantiationException |
			IllegalAccessException |
			IllegalArgumentException |
			InvocationTargetException e
		) {
			throw new IllegalStateException( error_constructorError( real ), e );
		}
	}

	private Constructor<C> getConstructor() {
		Set<Constructor<C>> constructors = getConstructors();
		if ( constructors.size() > 1 )
			throw new IllegalStateException( error_multipleConstructors( real ) );
		return constructors.stream()
			.findFirst()
			.orElseThrow(
				() -> new IllegalStateException( error_missingConstructor( real ) )
			);
	}

	@SuppressWarnings( "unchecked" )
	private Set<Constructor<C>> getConstructors() {
		return Stream.of( real.getDeclaredConstructors() )
			.map( constructor -> ( Constructor<C> ) constructor )
			.filter( not( ClassConstructor::isPrivate ) )
			.collect( Collectors.toUnmodifiableSet() );
	}

	private static boolean isPrivate( Constructor<?> constructor ) {
		return Modifier.isPrivate( constructor.getModifiers() );
	}

	static <C> ClassConstructor<C> expose( Class<C> real ) {
		return new ClassConstructor<>( real );
	}

	static String error_constructorError( Class<?> real ) {
		return error_constructor( "encountered error(s) while searching for constructor", real );
	}

	static String error_missingConstructor( Class<?> real ) {
		return error_constructor( "no constructor(s) found", real );
	}

	static String error_multipleConstructors( Class<?> real ) {
		return error_constructor( "multiple constructors found", real );
	}

	private static String error_constructor( String message, Class<?> real ) {
		return "Could not instantiate, " + message + ": " + real;
	}


}
