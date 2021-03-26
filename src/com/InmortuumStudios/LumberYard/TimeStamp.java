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
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
public class TimeStamp {
	/**
	 * The DateFormat Enum.
	 */
	public enum DateFormat {
		DDMMYYYY, MMDDYYYY, YYYYMMDD;
		/**
		 * Formats the date components of a calendar pointer into a string.
		 *
		 * @param calendar
		 *                 the calendar pointer
		 * @return the string
		 */
		public String format(Calendar calendar) {
			if (this == DDMMYYYY) {
				return String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-"
						+ String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-"
						+ String.format("%04d", calendar.get(Calendar.YEAR));
			} else if (this == MMDDYYYY) {
				return String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-"
						+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-"
						+ String.format("%04d", calendar.get(Calendar.YEAR));
			} else if (this == YYYYMMDD) {
				return String.format("%04d", calendar.get(Calendar.YEAR)) + "-"
						+ String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-"
						+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
			}
			return "??????????";
		}
		/**
		 * Returns a string representation on the current system date.
		 *
		 * @return the string
		 */
		public String now() {
			Calendar now = Calendar.getInstance();
			return format(now);
		}
		@Override
		public String toString() {
			switch (this) {
				case DDMMYYYY:
					return "DD-MM-YYYY";
				case MMDDYYYY:
					return "MM-DD-YYYY";
				case YYYYMMDD:
					return "YYYY-MM-DD";
				default:
					break;
			}
			return "??????????";
		}
	}
	/**
	 * The TimeFormat Enum.
	 */
	public enum TimeFormat {
		/** The 12 hour time format. */
		H12,
		/** The 24 hour time format. */
		H24;
		/**
		 * Formats the time components of a calendar pointer into a string.
		 *
		 * @param calendar
		 *                 the calendar pointer
		 * @return the string
		 */
		public String format(Calendar calendar) {
			if (this == H12) {
				return String.format("%02d", calendar.get(Calendar.HOUR)) + ":"
						+ String.format("%02d", calendar.get(Calendar.MINUTE)) + ":"
						+ String.format("%02d", calendar.get(Calendar.SECOND)) + ""
						+ (calendar.get(Calendar.AM_PM) == Calendar.PM ? "PM" : "AM") + " " + calendar.getTimeZone()
								.getDisplayName(false, TimeZone.SHORT, Locale.getDefault(Locale.Category.DISPLAY));
			} else if (this == H24) {
				return String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":"
						+ String.format("%02d", calendar.get(Calendar.MINUTE)) + ":"
						+ String.format("%02d", calendar.get(Calendar.SECOND)) + " " + calendar.getTimeZone()
								.getDisplayName(false, TimeZone.SHORT, Locale.getDefault(Locale.Category.DISPLAY));
			}
			return "00:00:00";
		}
		/**
		 * Returns a string representation on the current system time.
		 *
		 * @return the string
		 */
		public String now() {
			Calendar now = Calendar.getInstance();
			return format(now);
		}
		@Override
		public String toString() {
			switch (this) {
				case H12:
					return "HH:MM:SS AM/PM TZ";
				case H24:
					return "HH:MM:SS TZ";
				default:
					break;
			}
			return "??:??:??";
		}
	}
	/**
	 * Creates a TimeStamp representing the current system date and time.
	 *
	 * @return the time stamp
	 */
	public static TimeStamp now() {
		return new TimeStamp();
	}
	private DateFormat dateFormat = DateFormat.YYYYMMDD;
	private Calendar   pointer;
	private TimeFormat timeFormat = TimeFormat.H24;
	/**
	 * Instantiates a new time stamp representing the current time at creation.
	 */
	public TimeStamp() {
		pointer = Calendar.getInstance();
	}
	/**
	 * Instantiates a new time stamp representing a given calendar pointer.
	 *
	 * @param pointer
	 *                the pointer
	 */
	public TimeStamp(Calendar pointer) {
		this.pointer = pointer;
	}
	/**
	 * Gets the date format.
	 *
	 * @return the date format
	 */
	public DateFormat getDateFormat() {
		return dateFormat;
	}
	/**
	 * Gets the calendar pointer of the TimeStamp.
	 *
	 * @return the pointer
	 */
	public Calendar getPointer() {
		return (Calendar) pointer.clone();
	}
	/**
	 * Gets the time format.
	 *
	 * @return the time format
	 */
	public TimeFormat getTimeFormat() {
		return timeFormat;
	}
	/**
	 * Sets the date format
	 *
	 * @param dateFormat
	 *                   the new date format
	 */
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	/**
	 * Sets the time format.
	 *
	 * @param timeFormat
	 *                   the new time format
	 */
	public void setTimeFormat(TimeFormat timeFormat) {
		this.timeFormat = timeFormat;
	}
	@Override
	public String toString() {
		return dateFormat.format(pointer) + " " + timeFormat.format(pointer);
	}
}