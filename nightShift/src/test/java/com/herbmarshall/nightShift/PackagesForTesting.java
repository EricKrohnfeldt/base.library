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

import com.herbmarshall.nightShift.test.happyPath.AutomatedClass;
import com.herbmarshall.nightShift.test.happyPath2.AutomatedClass2;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

enum PackagesForTesting {

	happyPath(
		"com.herbmarshall.nightShift.test.happyPath",
		Set.of( AutomatedClass.class )
	),
	happyPath2(
		"com.herbmarshall.nightShift.test.happyPath2",
		Set.of( AutomatedClass2.class )
	),
	doesNotExist( "com.herbmarshall.nightShift.test.doesNotExist" ),
	doesNotExist2( "com.herbmarshall.nightShift.test.doesNotExist2" );

	private final String name;
	private final Set<Class<?>> automated;

	PackagesForTesting( String name ) {
		this( name, Set.of() );
	}

	PackagesForTesting( String name, Set<Class<?>> automated ) {
		this.name = Objects.requireNonNull( name );
		this.automated = Set.copyOf( Objects.requireNonNull( automated ) );
		this.automated.forEach(  // Double check packages
			classs -> Assertions.assertEquals( name, classs.getPackageName() )
		);
	}

	String getName() {
		return name;
	}

	Stream<Class<?>> getAutomatedClasses() {
		return automated.stream();
	}

	Stream<String> getAutomatedNames() {
		return readNames( getAutomatedClasses() );
	}

	@Override
	public String toString() {
		return getName();
	}

	static Stream<Class<?>> getAllAutomatedClasses() {
		return Stream.of( values() )
			.flatMap( PackagesForTesting::getAutomatedClasses );
	}

	static Stream<String> getAllAutomatedNames() {
		return readNames( getAllAutomatedClasses() );
	}

	private static Stream<String> readNames( Stream<Class<?>> classStream ) {
		return classStream.map( Class::getCanonicalName );
	}

}
