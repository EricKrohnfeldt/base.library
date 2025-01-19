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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings( { "checkstyle:LineLength", "checkstyle:RegexpSinglelineJava" } )
enum NightShiftLogo {

	/** <a href="https://patorjk.com/software/taag/#p=display&h=3&v=0&f=Bloody&t=Night%20Shift%20Systems">Source</a>. */
	bleeding( """
	 ███▄    █ ██▓ ▄████ ██░ ██▄▄▄█████▓     ██████ ██░ ██ ██▓ █████▄▄▄█████▓     █████▓██   ██▓ ██████▄▄▄█████▓█████ ███▄ ▄███▓ ██████
	 ██ ▀█   █▓██▒██▒ ▀█▓██░ ██▓  ██▒ ▓▒   ▒██    ▒▓██░ ██▓██▓██   ▒▓  ██▒ ▓▒   ▒██    ▒▒██  ██▒██    ▒▓  ██▒ ▓▓█   ▀▓██▒▀█▀ ██▒██    ▒
	▓██  ▀█ ██▒██▒██░▄▄▄▒██▀▀██▒ ▓██░ ▒░   ░ ▓██▄  ▒██▀▀██▒██▒████ ░▒ ▓██░ ▒░   ░ ▓██▄   ▒██ ██░ ▓██▄  ▒ ▓██░ ▒▒███  ▓██    ▓██░ ▓██▄
	▓██▒  ▐▌██░██░▓█  ██░▓█ ░██░ ▓██▓ ░      ▒   ██░▓█ ░██░██░▓█▒  ░░ ▓██▓ ░      ▒   ██▒░ ▐██▓░ ▒   ██░ ▓██▓ ░▒▓█  ▄▒██    ▒██  ▒   ██▒
	▒██░   ▓██░██░▒▓███▀░▓█▒░██▓ ▒██▒ ░    ▒██████▒░▓█▒░██░██░▒█░     ▒██▒ ░    ▒██████▒▒░ ██▒▓▒██████▒▒ ▒██▒ ░░▒████▒██▒   ░██▒██████▒▒
	░ ▒░   ▒ ▒░▓  ░▒   ▒ ▒ ░░▒░▒ ▒ ░░      ▒ ▒▓▒ ▒ ░▒ ░░▒░░▓  ▒ ░     ▒ ░░      ▒ ▒▓▒ ▒ ░ ██▒▒▒▒ ▒▓▒ ▒ ░ ▒ ░░  ░░ ▒░ ░ ▒░   ░  ▒ ▒▓▒ ▒ ░
	░ ░░   ░ ▒░▒ ░ ░   ░ ▒ ░▒░ ░   ░       ░ ░▒  ░ ░▒ ░▒░ ░▒ ░░         ░       ░ ░▒  ░ ▓██ ░▒░░ ░▒  ░ ░   ░    ░ ░  ░  ░      ░ ░▒  ░ ░
	   ░   ░ ░ ▒ ░ ░   ░ ░  ░░ ░ ░         ░  ░  ░  ░  ░░ ░▒ ░░ ░     ░         ░  ░  ░ ▒ ▒ ░░ ░  ░  ░   ░        ░  ░      ░  ░  ░  ░
	         ░ ░       ░ ░  ░  ░                 ░  ░  ░  ░░                          ░ ░ ░          ░            ░  ░      ░        ░
	                                                                                    ░ ░
	"""
	),
	/** <a href="https://patorjk.com/software/taag/#p=display&h=3&v=0&f=3D-ASCII&t=Night%20Shift%20Systems">Source</a>. */
	threeD(
	"""
	 ________   ___  ________  ___  ___  _________        ________  ___  ___  ___  ________ _________        ________       ___    ___ ________  _________  _______   _____ ______   ________
	|\\   ___  \\|\\  \\|\\   ____\\|\\  \\|\\  \\|\\___   ___\\     |\\   ____\\|\\  \\|\\  \\|\\  \\|\\  _____|\\___   ___\\     |\\   ____\\     |\\  \\  /  /|\\   ____\\|\\___   ___|\\  ___ \\ |\\   _ \\  _   \\|\\   ____\\
	\\ \\  \\\\ \\  \\ \\  \\ \\  \\___|\\ \\  \\\\\\  \\|___ \\  \\_|     \\ \\  \\___|\\ \\  \\\\\\  \\ \\  \\ \\  \\__/\\|___ \\  \\_|     \\ \\  \\___|_    \\ \\  \\/  / \\ \\  \\___|\\|___ \\  \\_\\ \\   __/|\\ \\  \\\\\\__\\ \\  \\ \\  \\___|_
	 \\ \\  \\\\ \\  \\ \\  \\ \\  \\  __\\ \\   __  \\   \\ \\  \\       \\ \\_____  \\ \\   __  \\ \\  \\ \\   __\\    \\ \\  \\       \\ \\_____  \\    \\ \\    / / \\ \\_____  \\   \\ \\  \\ \\ \\  \\_|/_\\ \\  \\\\|__| \\  \\ \\_____  \\
	  \\ \\  \\\\ \\  \\ \\  \\ \\  \\|\\  \\ \\  \\ \\  \\   \\ \\  \\       \\|____|\\  \\ \\  \\ \\  \\ \\  \\ \\  \\_|     \\ \\  \\       \\|____|\\  \\    \\/  /  /   \\|____|\\  \\   \\ \\  \\ \\ \\  \\_|\\ \\ \\  \\    \\ \\  \\|____|\\  \\
	   \\ \\__\\\\ \\__\\ \\__\\ \\_______\\ \\__\\ \\__\\   \\ \\__\\        ____\\_\\  \\ \\__\\ \\__\\ \\__\\ \\__\\       \\ \\__\\        ____\\_\\  \\ __/  / /       ____\\_\\  \\   \\ \\__\\ \\ \\_______\\ \\__\\    \\ \\__\\____\\_\\  \\
	    \\|__| \\|__|\\|__|\\|_______|\\|__|\\|__|    \\|__|       |\\_________\\|__|\\|__|\\|__|\\|__|        \\|__|       |\\_________|\\___/ /       |\\_________\\   \\|__|  \\|_______|\\|__|     \\|__|\\_________\\
	                                                        \\|_________|                                       \\|_________\\|___|/        \\|_________|                                  \\|_________|
	"""
	);


	private final List<String> words;

	NightShiftLogo( String... words ) {
		this.words = Arrays.asList( words );
	}

	String render() {
		return String.join( "", words );
	}

	static NightShiftLogo random() {
		return values()[ ThreadLocalRandom.current().nextInt( values().length ) ];
	}

}
