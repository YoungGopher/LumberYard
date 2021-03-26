/*
 * MIT License
 *
 * Copyright (c) 2021 YoungGopher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.InmortuumStudios.LumberYard;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
public class Log {
	private static Log defaultLog;
	/**
	 * Gets the default log.
	 *
	 * @return the default log
	 */
	public static Log getDefaultLog() {
		if (defaultLog == null) {
			defaultLog = new Log();
		}
		return defaultLog;
	}
	/**
	 * Write an exception to the default {@link com.InmortuumStudios.LumberYard.Log
	 * Log} using a specified
	 * {@link com.InmortuumStudios.LumberYard.LogItem.Category Category}.
	 *
	 * @param category
	 *                 the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 *                 Category}
	 * @param e
	 *                 an exception
	 */
	public static void write(Category category, Exception e) {
		getDefaultLog().addLogItem(category, e);
	}
	/**
	 * Write a string message to the default
	 * {@link com.InmortuumStudios.LumberYard.Log Log} using a specified
	 * {@link com.InmortuumStudios.LumberYard.LogItem.Category Category}.
	 *
	 * @param category
	 *                 the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 *                 Category}
	 * @param message
	 *                 the message
	 */
	public static void write(Category category, String message) {
		getDefaultLog().addLogItem(category, message);
	}
	/**
	 * Write an exception to the default {@link com.InmortuumStudios.LumberYard.Log
	 * Log}.
	 *
	 * @param e
	 *          an exception
	 */
	public static void write(Exception e) {
		getDefaultLog().addLogItem(e);
	}
	/**
	 * Write a message to the default {@link com.InmortuumStudios.LumberYard.Log
	 * Log}.
	 *
	 * @param logItem
	 *                the message
	 */
	public static void write(String logItem) {
		getDefaultLog().addLogItem(logItem);
	}
	private ArrayList<Category> consoleCategories = Category.defaultConsole();
	private TimeStamp           createdTime;
	private PrintWriter         logFile           = null;
	private String              name              = "log", logDirectory;
	private boolean             verbose           = false;
	/**
	 * Instantiates a new log.
	 */
	public Log() {
		this(null);
	}
	/**
	 * Instantiates a new log with a given name.
	 *
	 * @param name
	 *             the name
	 */
	public Log(String name) {
		this(name, null);
	}
	/**
	 * Instantiates a new log with a given name in a specified directory.
	 *
	 * @param name
	 *                  the name
	 * @param directory
	 *                  the directory
	 */
	public Log(String name, String directory) {
		createdTime = TimeStamp.now();
		logDirectory = logDirectory == null ? LogUtil.defaultLogDirectory() : directory;
		this.name = name == null ? "log" : name;
		bindFile();
		registerShutdownHook();
	}
	/**
	 * Write an exception to the log using a specified
	 * {@link com.InmortuumStudios.LumberYard.LogItem.Category Category}.
	 *
	 * @param category
	 *                 the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 *                 Category}
	 * @param e
	 *                 an exception
	 */
	public void addLogItem(Category category, Exception e) {
		if (category == Category.VERBOSE && !verbose) {
			return;
		}
		LogItem item = new LogItem(category, e);
		addLogItem(item);
	}
	/**
	 * Write a string message to the log using a specified
	 * {@link com.InmortuumStudios.LumberYard.LogItem.Category Category}.
	 *
	 * @param category
	 *                 the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 *                 Category}
	 * @param message
	 *                 the message
	 */
	public void addLogItem(Category category, String message) {
		if (category == Category.VERBOSE && !verbose) {
			return;
		}
		LogItem item = new LogItem(category, message);
		addLogItem(item);
	}
	/**
	 * Write an exception to the log.
	 *
	 * @param e
	 *          an exception
	 */
	public void addLogItem(Exception e) {
		LogItem item = new LogItem(e);
		addLogItem(item);
	}
	/**
	 * Adds the {@link com.InmortuumStudios.LumberYard.LogItem LogItem} to the log.
	 *
	 * @param logItem
	 *                the {@link com.InmortuumStudios.LumberYard.LogItem LogItem}
	 */
	public void addLogItem(LogItem logItem) {
		if (logItem.getCategory().intValue() == Category.DEBUG.intValue()) {
			toString();
		} else if (logItem.getCategory().intValue() == Category.VERBOSE.intValue()) {
			if (!verbose) {
				return;
			}
		}
		if (logFile == null) {
			bindFile();
		}
		logItem.writeTo(logFile);
		if (isConsoleVisible(logItem.getCategory())) {
			logItem.writeTo(System.out);
		}
	}
	/**
	 * Write a message to the log.
	 *
	 * @param message
	 *                the message
	 */
	public void addLogItem(String message) {
		LogItem item = new LogItem(Category.INFO, message);
		addLogItem(item);
	}
	/**
	 * Initiates a new log file using the current system time for future
	 * {@link com.InmortuumStudios.LumberYard.LogItem LogItems} to be written to.
	 *
	 * @return true, if successful
	 */
	public boolean bindFile() {
		try {
			File directory = new File(getLogDirectory());
			if (!directory.exists()) {
				directory.mkdirs();
			}
			logFile = new PrintWriter(getFilePath());
			return true;
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
			logFile = null;
		}
		return false;
	}
	/**
	 * Enable or disable verbose logging.
	 *
	 * @param verbose
	 *                true for enable
	 */
	public void enableVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	/**
	 * Flush the raw string representation to the log.
	 *
	 * @param logItem
	 *                the raw string
	 */
	public void flush(String logItem) {
		if (logFile == null) {
			bindFile();
		}
		if (logFile != null) {
			logFile.append(logItem);
			logFile.flush();
		}
	}
	/**
	 * Gets the name of the log's file.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return name + "_" + createdTime.getDateFormat().format(createdTime.getPointer()) + "_"
				+ new String(createdTime.getTimeFormat().format(createdTime.getPointer())).replace(":", "-")
						.replace(" ", "_")
				+ ".log";
	}
	/**
	 * Gets file path of the logs file.
	 *
	 * @return the string
	 */
	public String getFilePath() {
		return logDirectory + System.getProperty("file.separator") + getFileName();
	}
	/**
	 * Gets the logs directory.
	 *
	 * @return the logs directory
	 */
	public String getLogDirectory() {
		return new String(logDirectory);
	}
	/**
	 * Hides any future {@link com.InmortuumStudios.LumberYard.LogItem LogItems} of
	 * a specified {@link com.InmortuumStudios.LumberYard.LogItem.Category Category}
	 * from the console.
	 *
	 * @param category
	 *                 the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 *                 Category}
	 */
	public void hideFromConsole(Category category) {
		if (!consoleCategories.contains(category)) {
			consoleCategories.add(category);
		}
	}
	/**
	 * Checks if the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 * Category} is printed to the console by the log.
	 *
	 * @param category
	 *                 the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 *                 Category}
	 * @return true, if the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 *         Category} is being printed to the console
	 */
	public boolean isConsoleVisible(Category category) {
		return consoleCategories.contains(category);
	}
	/**
	 * Makes the log the default log.
	 */
	public void makeDefault() {
		defaultLog = this;
	}
	/**
	 * Show any future {@link com.InmortuumStudios.LumberYard.LogItem LogItems} of a
	 * specified {@link com.InmortuumStudios.LumberYard.LogItem.Category Category}
	 * to the console.
	 *
	 * @param category
	 *                 the {@link com.InmortuumStudios.LumberYard.LogItem.Category
	 *                 Category}
	 */
	public void showToConsole(Category category) {
		if (!consoleCategories.contains(category)) {
			consoleCategories.add(category);
		}
	}
	/**
	 * Checks if verbose logging is enabled.
	 *
	 * @return true, if enabled
	 */
	public boolean verbose() {
		return verbose;
	}
	/**
	 * Adds a ShutdownHook to the Runtime that flushes and closes the PrintWriter
	 * for the log's file upon Runtime shutdown.
	 */
	private void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				setName("LumberYard - Shutdown Hook");
				addLogItem(Category.INFO, "Saving " + getFileName());
				logFile.flush();
				logFile.close();
			}
		});
	}
}
