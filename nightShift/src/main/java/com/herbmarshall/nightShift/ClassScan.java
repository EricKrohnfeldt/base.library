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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class ClassScan implements AutoCloseable {

	static final String DEFAULT_PACKAGE = "com.herbmarshall";

	private final ScanResult scan;
	private final Set<String> packages;

	private ClassScan( ScanResult scan, Set<String> packages ) {
		this.scan = Objects.requireNonNull( scan );
		this.packages = Objects.requireNonNull( packages );
	}

	Stream<String> getPackages() {
		return packages.stream();
	}

	Stream<String> getAutomated() {
		return scan
			.getClassesWithAnnotation( Automated.class )
			.stream()
			.map( ClassInfo::getName );
	}

	@Override
	public void close() {
		scan.close();
	}

	/** Will scan the provided packages for {@link Automated} classes using {@link ClassScan#DEFAULT_PACKAGE}. */
	public static ClassScan scan() {
		return scan( DEFAULT_PACKAGE );
	}

	/**
	 * Will scan the provided packages for {@link Automated} classes.
	 * @param packages The package names to scan, example: {@link ClassScan#DEFAULT_PACKAGE}.
	 */
	public static ClassScan scan( String... packages ) {
		return new ClassScan(
			new ClassGraph()
				//.verbose()
				.enableClassInfo()
				.enableAnnotationInfo()
				.ignoreClassVisibility()
				.acceptPackages( packages )
				.scan(),
			Stream.of( packages ).collect( Collectors.toUnmodifiableSet() )
		);
	}

}
