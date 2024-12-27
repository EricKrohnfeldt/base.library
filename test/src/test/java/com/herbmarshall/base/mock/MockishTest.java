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

package com.herbmarshall.base.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Super class test module for a non-failing {@link Mockish} implementations.
 * @param <M> The {@link Mockish} implementation to test
 */
public abstract non-sealed class MockishTest<M extends Mockish>
	extends MockishTestAbstract<M> {

	private final Consumer<M> directValidation = Mockish::validate;
	private final Consumer<M> indirectValidation = m -> Mockish.executeValidation();

	/** Set up an expectation and return a {@link Runnable} to 'call' lambda and resolve it. */
	protected abstract Runnable prepareMock( M mock );

	@Nested
	class autoValidate {

		@TestFactory
		Stream<DynamicTest> happyPath() {
			return constructAuto()
				.entrySet()
				.stream()
				.flatMap( entry -> happyPath( entry.getValue() + " auto", entry.getKey() ) );
		}

		private Stream<DynamicTest> happyPath( String name, Supplier<M> constructor ) {
			return Stream.of(
				passing( name + " direct",           constructor, directValidation   ),
				passing( name + " indirect",         constructor, indirectValidation ),
				failing( name + " direct failing",   constructor, directValidation   ),
				failing( name + " indirect failing", constructor, indirectValidation )
			);
		}

		private DynamicTest passing( String name, Supplier<M> constructor, Consumer<M> validate ) {
			return DynamicTest.dynamicTest( name, () ->
				Assertions.assertDoesNotThrow( () -> {
					// Arrange
					M mock = constructor.get();
					prepareMock( mock ).run();
					// Act
					validate.accept( mock );
					// Assert
					// Does not throw
				} )
			);
		}

		private DynamicTest failing( String name, Supplier<M> constructor, Consumer<M> validate ) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				M mock = constructor.get();
				prepareMock( mock );
				// Act
				try {
					validate.accept( mock );
					throw new Exception( "Expected exception" );  // Cannot throw AssertionError
				}
				// Assert
				catch ( AssertionError ignored ) {
				}
				finally {
					//noinspection deprecation
					mock.disable();
				}
			} );
		}

	}

	@Nested
	class manualValidate {

		@TestFactory
		Stream<DynamicTest> happyPath() {
			return constructManual()
				.entrySet()
				.stream()
				.flatMap( entry -> happyPath( entry.getValue() + " manual", entry.getKey() ) );
		}

		private Stream<DynamicTest> happyPath( String name, Supplier<M> constructor ) {
			return Stream.of(
				passing      ( name + " direct",           constructor, directValidation ),
				directFailing( name + " direct failing",   constructor                   ),
				indirect     ( name + " indirect",         constructor                   ),
				indirect     ( name + " indirect failing", constructor                   )
			);
		}

		private DynamicTest passing( String name, Supplier<M> constructor, Consumer<M> validate ) {
			return DynamicTest.dynamicTest( name, () ->
				Assertions.assertDoesNotThrow( () -> {
					// Arrange
					M mock = constructor.get();
					prepareMock( mock ).run();
					// Act
					validate.accept( mock );
					// Assert
					// Does not throw
				} )
			);
		}

		private DynamicTest directFailing( String name, Supplier<M> constructor ) {
			return DynamicTest.dynamicTest( name, () -> {
				// Arrange
				M mock = constructor.get();
				prepareMock( mock );
				// Act
				try {
					directValidation.accept( mock );
					throw new Exception( "Expected exception" );  // Cannot throw AssertionError
				}
				// Assert
				catch ( AssertionError ignored ) {
				}
				finally {
					//noinspection deprecation
					mock.disable();
				}
			} );
		}

		private DynamicTest indirect( String name, Supplier<M> constructor ) {
			return DynamicTest.dynamicTest( name, () ->
				Assertions.assertDoesNotThrow( () -> {
					// Arrange
					M mock = constructor.get();
					prepareMock( mock );
					// Act
					indirectValidation.accept( mock );
					// Assert
					// Does not throw
				} )
			);
		}

	}

}
