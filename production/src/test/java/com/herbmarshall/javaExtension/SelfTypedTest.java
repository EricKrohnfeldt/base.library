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
