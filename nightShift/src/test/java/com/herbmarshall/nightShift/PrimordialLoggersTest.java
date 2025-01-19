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
import org.junit.jupiter.api.TestFactory;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

final class PrimordialLoggersTest {

	@TestFactory
	Stream<DynamicTest> happyPath() {
		return Stream.of( PrimordialLoggers.values() )
			.flatMap( this::happyPath );
	}

	private Stream<DynamicTest> happyPath( PrimordialLogger logger ) {
		return Stream.of(
			happyPath( logger + " out", logger, PrimordialLogger::out ),
			happyPath( logger + " err", logger, PrimordialLogger::err )
		);
	}

	private DynamicTest happyPath( String name, PrimordialLogger logger, BiConsumer<PrimordialLogger, String> action ) {
		return DynamicTest.dynamicTest( name, () ->
			Assertions.assertDoesNotThrow( () ->
				action.accept( logger, randomString() + "\n" )
			)
		);
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

}
