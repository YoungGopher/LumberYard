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
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Calendar;
/**
 * The LogItem Class.
 */
public class LogItem {
	private Category  category   = Category.INFO;
	private Exception e          = null;
	private String    threadName = "Unknown Thread", message = "";
	private TimeStamp timeStamp;
	/**
	 * Instantiates a new log item.
	 *
	 * @param time
	 *                   the time stamp
	 * @param threadName
	 *                   the name of the thread
	 * @param e
	 *                   an exception
	 */
	public LogItem(Calendar time, String threadName, Exception e) {
		this(e);
		timeStamp = new TimeStamp(time);
		this.threadName = threadName;
	}
	/**
	 * Instantiates a new log item.
	 *
	 * @param category
	 *                 the category
	 * @param e
	 *                 an exception
	 */
	public LogItem(Category category, Exception e) {
		this(category, e.getMessage());
		this.e = e;
	}
	/**
	 * Instantiates a new log item.
	 *
	 * @param category
	 *                 the category
	 * @param message
	 *                 the message
	 */
	public LogItem(Category category, String message) {
		this(TimeStamp.now(), Thread.currentThread().getName(), category, message);
	}
	/**
	 * Instantiates a new log item.
	 *
	 * @param e
	 *          an exception
	 */
	public LogItem(Exception e) {
		this(Category.EXCEPTION, e.getMessage());
		this.e = e;
	}
	/**
	 * Instantiates a new log item.
	 *
	 * @param time
	 *                   the time
	 * @param threadName
	 *                   the name of the thread
	 * @param category
	 *                   the category
	 * @param e
	 *                   an exception
	 */
	protected LogItem(TimeStamp time, String threadName, Category category, Exception e) {
		this.threadName = threadName;
		this.category = category;
		message = e.getMessage();
		this.e = e;
		timeStamp = time;
	}
	/**
	 * Instantiates a new log item.
	 *
	 * @param time
	 *                   the timestamp
	 * @param threadName
	 *                   the name of the thread
	 * @param category
	 *                   the category
	 * @param message
	 *                   the message
	 */
	protected LogItem(TimeStamp time, String threadName, Category category, String message) {
		this.threadName = threadName;
		this.category = category;
		this.message = message;
		timeStamp = time;
	}
	/**
	 * Gets the category of the log item.
	 *
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * Gets the name of the thread that created the log item.
	 *
	 * @return the thread name
	 */
	public String getThread() {
		return threadName;
	}
	@Override
	public String toString() {
		return timeStamp + " | " + category + " | " + threadName + " | " + message;
	}
	/**
	 * Write the log item to a specified print stream.
	 *
	 * @param printStream
	 *                    the print stream
	 */
	public void writeTo(PrintStream printStream) {
		String logEntry = toString() + "\n";
		if (printStream != null) {
			printStream.append(logEntry);
			printStream.flush();
			if (e != null) {
				e.printStackTrace(printStream);
				printStream.flush();
			}
		}
	}
	/**
	 * Write the log item to a specified print writer.
	 *
	 * @param printWriter
	 *                    the print writer
	 */
	public void writeTo(PrintWriter printWriter) {
		String logEntry = toString() + "\n";
		if (printWriter != null) {
			printWriter.append(logEntry);
			printWriter.flush();
			if (e != null) {
				e.printStackTrace(printWriter);
				printWriter.flush();
			}
		}
	}
}
