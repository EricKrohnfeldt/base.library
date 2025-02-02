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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class ClassType implements Predicate<Type> {

	private static final String tab = "\t";

	private final PrimordialLogger log;
	private final Class<?> real;
	private final List<ClassType> generics;

	private ClassType( PrimordialLogger log, Type real ) {
		this( log, real, 0 );
	}

	private ClassType( PrimordialLogger log, Type real, int index ) {
		this.log = Objects.requireNonNull( log );
		Objects.requireNonNull( real );

		log( index, "Class: " + real );
		this.real = findRaw( real );
		this.generics = real instanceof Class ?
			List.of() :
			readGenerics( index, ( ParameterizedType ) real );
	}

	private Class<?> findRaw( Type type ) {
		if ( type instanceof ParameterizedType )
			return findRaw( ( ( ParameterizedType ) type ).getRawType() );
		if ( type instanceof Class )
			return ( Class<?> ) type;
		throw new IllegalArgumentException( error_unsupportedType( type ) );
	}

	private List<ClassType> readGenerics( int index, ParameterizedType type ) {
		log( index, "Generics:" );
		return Stream.of( type.getActualTypeArguments() )
			.map( generic -> new ClassType( log, generic, index + 1 ) )
			.toList();
	}

	@Override
	public boolean test( Type type ) {
		return test( 0, Objects.requireNonNull( type ) );
	}

	private boolean test( int index, Type type ) {
		if ( type.equals( real ) ) {
			log( index, "Class " + type + " -> Pass" );
			return true;
		}

		if ( type instanceof Class )
			return test( index, ( Class<?> ) type );

		if ( type instanceof ParameterizedType )
			return test( index, ( ParameterizedType ) type );

		throw new IllegalArgumentException( error_unsupportedType( type ) );
	}

	private boolean test( int index, Class<?> type ) {
		Type[] faces = type.getGenericInterfaces();
		Type parent = type.getGenericSuperclass();

		if ( faces.length == 0 && parent == null ) {
			log( index, "Class " + type + " -> Fail" );
			return false;
		}

		boolean pass = false;
		log( index, "Class " + type + " -> Checking" );
		if ( faces.length > 0 ) {
			log( index + 1, "Interfaces" );
			if ( Stream.of( faces ).anyMatch( face -> test( index + 2, face ) ) ) {
				pass = true;
			}
		}
		if ( ! pass && parent != null ) {
			log( index + 1, "Super" );
			pass = test( index + 2, parent );
		}

		log( index, "Class " + type + " -> " + ( pass ? "Pass" : "Fail" ) );
		return pass;
	}

	private boolean test( int index, ParameterizedType type ) {
		log( index, "Generic: " + type + " -> Checking" );
		if ( ! test( index + 1, type.getRawType() ) ) {
			log( index, "Generic: " + type + " -> Fail" );
			return false;
		}

		boolean pass = true;
		Type[] typeArguments = type.getActualTypeArguments();
		for ( int i = 0; i < typeArguments.length; ++i ) {
			log( index + 1, "Parameter " + i + ": " + typeArguments[ i ] + " -> Checking" );
			if ( ! generics.get( i ).test( index + 2, typeArguments[ i ] ) )
				pass = false;
			log( index + 1, "Parameter " + i + ": " + typeArguments[ i ] + " -> " + ( pass ? "Pass" : "Fail" ) );
			if ( ! pass ) break;
		}

		log( index, "Generic " + type + " -> " + ( pass ? "Pass" : "Fail" ) );
		return pass;
	}

	private void log( int index, String message ) {
		log.err( tab.repeat( index ) + message );
	}

	static ClassType expose( PrimordialLogger logger, Type real ) {
		return new ClassType( logger, real );
	}

	static String error_unsupportedType( Type type ) {
		return "Supported " + Type.class.getSimpleName() + ": " + type.getClass();
	}

}
