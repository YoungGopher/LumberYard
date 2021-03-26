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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class LogUtil {
	/**
	 * Delete all logs in the log directory.
	 */
	public static void clearLogDirectory(String directoryPath) {
		if (directoryPath == null) {
			directoryPath = defaultLogDirectory();
		}
		File logDir = new File(directoryPath);
		if (logDir.exists()) {
			for (File file : logDir.listFiles()) {
				file.delete();
			}
		}
	}
	/**
	 * Gets the default log directory pertaining to a .lumberyard folder within the
	 * working directory.
	 *
	 * @return the default log directory
	 */
	public static String defaultLogDirectory() {
		return System.getProperty("user.dir") + System.getProperty("file.separator") + ".lumberyard";
	}
	/**
	 * Removes the category from a log file.
	 *
	 * @param log
	 *                 the log file
	 * @param category
	 *                 the category to remove
	 */
	public static void removeCategoryFromLogFile(File log, Category category) {
		if (log.exists()) {
			File tempLog = new File(log.getAbsoluteFile() + "_tmp");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(log));
				tempLog.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempLog));
				String line;
				while ((line = reader.readLine()) != null) {
					if (!line.contains(category.toString())) {
						writer.append(line);
						writer.append("\n");
					}
				}
				reader.close();
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String logPath = log.getAbsolutePath();
			log.delete();
			tempLog.renameTo(new File(logPath));
			tempLog.setReadable(true);
			tempLog.setReadOnly();
		}
	}
}
