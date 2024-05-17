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

import com.herbmarshall.base.Equals;

final class DiffGeneratorDefault implements DiffGenerator {

	static final String DEFAULT_MESSAGE = "No diff generated, please set DiffGenerator";
	static final DiffGeneratorDefault INSTANCE = new DiffGeneratorDefault();

	private DiffGeneratorDefault() {}

	@Override
	public String diff( Object expected, Object actual ) {
		return DEFAULT_MESSAGE;
	}

	@Override
	public double quantify( Object expected, Object actual ) {
		return Equals.evaluate( expected, actual ) ? 1.0 : 0.0;
	}

}
