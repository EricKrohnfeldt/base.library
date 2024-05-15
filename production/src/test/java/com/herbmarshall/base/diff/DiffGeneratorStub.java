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

package com.herbmarshall.base.diff;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

final class DiffGeneratorStub implements DiffGenerator {

	private final List<Object> expectedInputs = new ArrayList<>();
	private final List<Object> actualInputs = new ArrayList<>();
	private final Queue<String> outputs = new LinkedList<>();

	@Override
	public String diff( Object expected, Object actual ) {
		expectedInputs.add( expected );
		actualInputs.add( actual );
		return outputs.remove();
	}

	void addOuptut( String value ) {
		outputs.add( value );
	}

	void validate( List<Object> expected, List<Object> actual ) {
		Assertions.assertEquals( expected, expectedInputs );
		Assertions.assertEquals( actual, actualInputs );
	}

}
