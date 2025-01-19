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

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

final class NightShiftLogoTest {

	@TestFactory
	Stream<DynamicTest> render() {
		return Stream.of( NightShiftLogo.values() )
			.map( logo -> shouldNotFailTest( "render", logo ) );
	}

	@TestFactory
	DynamicTest random() {
		return shouldNotFailTest( "random", NightShiftLogo.random() );
	}

	private DynamicTest shouldNotFailTest( String name, NightShiftLogo logo ) {
		return DynamicTest.dynamicTest( logo.toString(), () -> {
			System.out.println( "\n\n" + name + ":  " + logo );
			System.out.println( "-----------------------------------------------------------------------------------" );
			System.out.println( logo.render() );
			System.out.println( "-----------------------------------------------------------------------------------" );
		} );
	}

}
