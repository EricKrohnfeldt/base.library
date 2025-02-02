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

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.herbmarshall.nightShift.ClassType.error_unsupportedType;
import static com.herbmarshall.nightShift.PrimordialLoggers.VERBOSE;

final class ClassTypeTest {

	private final PrimordialLogger logger = VERBOSE.autoLine();

	private final Type typeCustomSuper = readParameterType( "methodCustomSuper" );
	private final Type typeUuid = readParameterType( "methodUuid" );
	private final Type typeSet_UUID = readParameterType( "methodSet_Uuid" );
	private final Type typeFunction_String_UUID = readParameterType( "methodFunction_String_Uuid" );
	private final Type typeFunction_String_Set_UUID = readParameterType( "methodFunction_String_Set_Uuid" );

	private final Type badType = new Type() {
		@Override
		public String getTypeName() {
			return UUID.randomUUID().toString();
		}
	};

	@Nested
	class constructor {

		@Test
		void badType() {
			// Arrange
			// Act
			try {
				ClassType.expose( logger, badType );
				Assertions.fail();
			}
			// Assert
			catch ( IllegalArgumentException e ) {
				Assertions.assertEquals(
					error_unsupportedType( badType ),
					e.getMessage()
				);
			}
		}

		@TestFactory
		Stream<DynamicTest> nullValues() {
			return Stream.of(
				nulls( "log",  null,   typeUuid ),
				nulls( "real", logger, null     )
			);
		}

		private DynamicTest nulls( String name, PrimordialLogger log, Type real ) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				// Act
				try {
					ClassType.expose( log, real );
					Assertions.fail();
				}
				// Assert
				catch ( NullPointerException npe ) {
					logger.out( "Pass " + npe.getMessage() );
				}
			} );
		}

	}

	@Nested
	class test {

		@TestFactory
		Stream<DynamicTest> happyPath() {
			return Stream.of(
				happyPath( "None", String.class, typeUuid, false ),
				happyPath( "None", UUID.class, typeUuid, true ),
				happyPath( "None Custom", UUID.class, typeCustomSuper, false ),
				happyPath( "None Custom", Custom.class, typeCustomSuper, true ),

				happyPath( "Single Class", ListUUID.class, typeSet_UUID, false ),
				happyPath( "Single Generic", SetString.class, typeSet_UUID, false ),
				happyPath( "Single", SetUUID.class, typeSet_UUID, true ),

				happyPath( "Multi Class", ListUUID.class, typeFunction_String_UUID, false ),
				happyPath( "Multi Generic A", FunctionUUIDUUID.class, typeFunction_String_UUID, false ),
				happyPath( "Multi Generic B", FunctionStringString.class, typeFunction_String_UUID, false ),
				happyPath( "Multi Pass", FunctionStringUUID.class, typeFunction_String_UUID, true ),

				happyPath( "Multi Class", SetString.class, typeFunction_String_Set_UUID, false ),
				happyPath( "Multi Generic A", FunctionUUIDSetUUID.class, typeFunction_String_Set_UUID, false ),
				happyPath( "Multi Generic B", FunctionStringListUUID.class, typeFunction_String_Set_UUID, false ),
				happyPath( "Multi Generic BA", FunctionStringSetString.class, typeFunction_String_Set_UUID, false ),
				happyPath( "Multi", FunctionStringSetUUID.class, typeFunction_String_Set_UUID, true )
			);
		}

		private <C> DynamicTest happyPath( String name, Class<C> cls, Type type, boolean expected ) {
			return DynamicTest.dynamicTest( name + ( expected ? " Pass" : " Fail" ), () -> {
				// Arrange
				log( "Expose: " + type );
				ClassType wrapper = ClassType.expose( logger, type );
				// Act
				log( "Test: " + cls );
				boolean output = wrapper.test( cls );
				// Assert
				Assertions.assertEquals( expected, output );
			} );
		}

		@Test
		void badType() {
			// Arrange
			ClassType wrapper = ClassType.expose( logger, typeUuid );
			// Act
			try {
				wrapper.test( badType );
				Assertions.fail();
			}
			// Assert
			catch ( IllegalArgumentException e ) {
				Assertions.assertEquals(
					error_unsupportedType( badType ),
					e.getMessage()
				);
			}
		}

	}

	private abstract static class Custom extends CustomSuper {}
	private abstract static class CustomSuper {}
	private abstract static class SetUUID implements Set<UUID> {}
	private abstract static class ListUUID implements List<UUID> {}
	private abstract static class SetString implements Set<String> {}
	private abstract static class FunctionStringString implements Function<String, String> {}
	private abstract static class FunctionUUIDUUID implements Function<UUID, UUID> {}
	private abstract static class FunctionStringUUID implements Function<String, UUID> {}
	private abstract static class FunctionStringListUUID implements Function<String, List<UUID>> {}
	private abstract static class FunctionUUIDSetUUID implements Function<UUID, Set<UUID>> {}
	private abstract static class FunctionStringSetString implements Function<String, Set<String>> {}
	private abstract static class FunctionStringSetUUID implements Function<String, Set<UUID>> {}

	@SuppressWarnings( "unused" )
	private void methodCustomSuper( CustomSuper x ) {}
	@SuppressWarnings( "unused" )
	private void methodUuid( UUID x ) {}
	@SuppressWarnings( "unused" )
	private void methodSet_Uuid( Set<UUID> x ) {}
	@SuppressWarnings( "unused" )
	private void methodFunction_String_Uuid( Function<String, UUID> x ) {}
	@SuppressWarnings( "unused" )
	private void methodFunction_String_Set_Uuid( Function<String, Set<UUID>> x ) {}

	private static Type readParameterType( String name ) {
		return Objects.requireNonNull(
			readMethod( name )
				.getGenericParameterTypes()[ 0 ]
		);
	}

	private static Method readMethod( String name ) {
		return Stream.of( ClassTypeTest.class.getDeclaredMethods() )
			.filter( method -> method.getName().equals( name ) )
			.findAny()
			.orElseThrow();
	}

	private void log( String message ) {
		logger.out( message );
	}


}
