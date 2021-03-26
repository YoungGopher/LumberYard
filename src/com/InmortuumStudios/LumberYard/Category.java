/*
 * MIT License Copyright (c) 2020 Tim Ostrom (tostrom@inmortuumstudios.com)
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions: The above copyright
 * notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS",
 * WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.InmortuumStudios.LumberYard;
import java.util.ArrayList;
public class Category {
	/**
	 * The debug category for logging technical information needed during debugging.
	 */
	public final static Category DEBUG     = new Category(3, "[DEBUG]");
	/**
	 * The error category for logging errors.
	 */
	public final static Category ERROR     = new Category(1, "[ERROR]");
	/**
	 * The exception category for logging thrown exceptions.
	 */
	public final static Category EXCEPTION = new Category(0, "[EXCEPTION]");
	/**
	 * The info category for logging standard information.
	 */
	public final static Category INFO      = new Category(2, "[INFO]");
	/**
	 * The verbose category for logging additional non-critical information. Verbose
	 * items are ignored by default.
	 */
	public final static Category VERBOSE   = new Category(4, "[VERBOSE]");
	/**
	 * Default console categories.
	 *
	 * @return an array list
	 */
	public static ArrayList<Category> defaultConsole() {
		ArrayList<Category> console = new ArrayList<>();
		console.add(INFO);
		console.add(ERROR);
		console.add(EXCEPTION);
		return console;
	}
	/** The integer representation. */
	private int    integer;
	/** The String representation. */
	private String string;
	/**
	 * Instantiates a new category.
	 *
	 * @param integer
	 *                the integer representation
	 * @param string
	 *                the string representation
	 */
	public Category(int integer, String string) {
		this.string = string;
		this.integer = integer;
	}
	/**
	 * Gets an integer representation of the category for sorting and visibility.
	 *
	 * @return the integer value
	 */
	public int intValue() {
		return integer;
	}
	/**
	 * Returns the string representation of the category.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return string;
	}
}
