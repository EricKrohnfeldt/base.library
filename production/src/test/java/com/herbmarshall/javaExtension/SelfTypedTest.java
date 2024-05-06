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

package com.herbmarshall.javaExtension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SelfTypedTest {

	@Test
	void self() {
		// Arrange
		CorrectSelf instance = new CorrectSelf();
		// Act
		CorrectSelf output = instance.self();
		// Assert
		Assertions.assertSame( instance, output );
	}

	@Test
	void self_wrongType() {
		// Arrange
		WrongSelf instance = new WrongSelf();
		// Act
		try {
			@SuppressWarnings( "unused" )
			CorrectSelf output = instance.self();
			Assertions.fail();
		}
		// Assert
		catch ( ClassCastException e ) {
			Assertions.assertTrue( e.getMessage().contains(
				"class com.herbmarshall.javaExtension.SelfTypedTest$WrongSelf " +
				"cannot be cast to class com.herbmarshall.javaExtension.SelfTypedTest$CorrectSelf"
			) );
		}
	}

	private static class CorrectSelf extends SelfTyped<CorrectSelf> {}

	private static class WrongSelf extends SelfTyped<CorrectSelf> {}

}
