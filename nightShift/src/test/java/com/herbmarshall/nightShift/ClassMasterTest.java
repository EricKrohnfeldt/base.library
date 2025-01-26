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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

final class ClassMasterTest {

	@Nested
	class constructor {

		@Test
		void real_null() {
			// Arrange
			// Act
			try {
				//noinspection ResultOfMethodCallIgnored
				ClassMaster.scrutinize( null );
				Assertions.fail();
			}
			catch ( NullPointerException npe ) {
				System.out.println( "Pass: " + npe.getMessage() );
			}
		}

	}

	@Nested
	class isJob {

		@TestFactory
		Stream<DynamicTest> happy() {
			return Stream.of(
				happy( Stub.class, false ),
				happy( StubAutomatedJob.class, true )
			);
		}

		private <T> DynamicTest happy( Class<T> type, boolean expected ) {
			return DynamicTest.dynamicTest( String.valueOf( expected ), () -> {
				// Arrange
				ClassMaster<T> master = ClassMaster.scrutinize( type );
				// Act
				boolean output = master.isJob();
				// Assert
				Assertions.assertEquals( expected, output );
			} );
		}

	}

	private static final class Stub {}

	private static final class StubAutomatedJob implements AutomatedJob {
		@Override public void endure() {}
	}

}
