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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum PackagesForTesting {

	happyPath(
		"com.herbmarshall.nightShift.test.happyPath",
		java.util.Set.of( "AutomatedClass" )
	),
	happyPath2(
		"com.herbmarshall.nightShift.test.happyPath2",
		Set.of( "AutomatedClass2" )
	),
	doesNotExist( "com.herbmarshall.nightShift.test.doesNotExist" ),
	doesNotExist2( "com.herbmarshall.nightShift.test.doesNotExist2" );

	private static final Set<String> allAutomated;

	private final String name;
	private final Set<String> automated;

	PackagesForTesting( String name ) {
		this( name, Set.of() );
	}

	PackagesForTesting( String name, Set<String> automated ) {
		this.name = Objects.requireNonNull( name );
		this.automated = Objects.requireNonNull( automated );
	}

	String getName() {
		return name;
	}

	Stream<String> getAutomated() {
		return automated.stream()
			.map( auto -> String.join( ".", name, auto ) );
	}

	@Override
	public String toString() {
		return getName();
	}

	static Stream<String> getAllAutomated() {
		return allAutomated.stream();
	}

	static {
		allAutomated = Stream.of( values() )
			.flatMap( PackagesForTesting::getAutomated )
			.collect( Collectors.toUnmodifiableSet() );
	}
}
