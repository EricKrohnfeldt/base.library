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

package com.herbmarshall.base.data.pair;

final class PairDefault<A, B> implements Pair<A, B> {

	private final A a;

	private final B b;

	private PairDefault( A a, B b ) {
		this.a = a;
		this.b = b;
	}

	@Override
	public A getA() {
		return a;
	}

	@Override
	public B getB() {
		return b;
	}

	static <A, B> Pair<A, B> from( A a, B b ) {
		return new PairDefault<>( a, b );
	}

}
