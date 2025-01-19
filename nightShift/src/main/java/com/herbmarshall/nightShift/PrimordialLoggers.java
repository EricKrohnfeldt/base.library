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

import java.io.PrintStream;
import java.util.function.Consumer;

enum PrimordialLoggers implements PrimordialLogger {

	            /*  null = No operation     */
	            /*                          */
	            /*  Stdout      Stderr      */
	STANDARD    (   System.out, null         ),
	VERBOSE     (   System.out, System.err   ),
	ERROR       (   null,       System.err   ),
	REDIRECT_OUT(   System.out, System.out   ),
	REDIRECT_ERR(   System.err, System.err   ),
	QUIET       (   null,       null         );

	private final Consumer<String> stdout;
	private final Consumer<String> stderr;

	PrimordialLoggers( PrintStream stdout, PrintStream stderr ) {
		this.stdout = wrap( stdout );
		this.stderr = wrap( stderr );
	}

	@Override
	public void out( String message ) {
		stdout.accept( message );
	}

	@Override
	public void err( String message ) {
		stderr.accept( message );
	}

	private static Consumer<String> wrap( PrintStream stream ) {
		return stream == null ?
			message -> { /* Do nothing */ } :
			stream::print;
	}

}
