package LumberYard;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
/**
 * LumberYard Log class.
 */
public class Log{
	private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss +SSS'ms' '['z']'").withZone(ZoneId.systemDefault());
	private static final String DEFAULT_DIR = System.getProperty("user.dir")+System.getProperty("file.separator")+"lumberyard";
	/**
	 * Get the default directory used when creating a log without a specified directory.
	 *
	 * @return the string
	 */
	public static String getDefaultDirectory(){
		return DEFAULT_DIR;
	}
	/**
	 * Convert a throwable into a structured string.
	 *
	 * @param e the throwable
	 * @return the structured string
	 */
	public static String parseThrowable(Throwable e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.getClass().getName()+(e.getMessage()!=null?": "+e.getMessage():""));
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append("\n\tat ");
			sb.append(element.toString());
		}
		if(e.getCause()!=null){
			sb.append("\nCaused by: ");
			sb.append(parseThrowable(e.getCause()));
		}
		return sb.toString();
	}
	private static Log defaultLog = null;
	/**
	 * Get the default log.
	 *
	 * @return the default log
	 */
	public static Log getDefault(){
		if(defaultLog == null){
			new Log(Thread.currentThread().getName());
		}
		return defaultLog;
	}
	private String name, dir;
	private Path path;
	/**
	 * Flag to mirror new log entries to the System.out console after the log file.
	 */
	public boolean console = true;
	/**
	 * Instantiates a new unnamed Log in the default directory.
	 */
	public Log(){
		this(null);
	}
	/**
	 * Instantiates a new Log with a specified name.
	 *
	 * @param name the name
	 */
	public Log(String name){
		this(DEFAULT_DIR, name);
	}
	/**
	 * Instantiates a new Log with a specified name in a specified directory.
	 *
	 * @param dir  the dir
	 * @param name the name
	 */
	public Log(String dir, String name){
		this.name = name;
		this.dir = dir;
		timestampName();
		generatePath();
		if(defaultLog==null){
			makeDefault();
		}
	}
	private void generatePath(){
		if(name == null){
			timestampName();
		}
		if(dir == null){
			dir = getDefaultDirectory();
		}
		path = Path.of(getDirectory() + System.getProperty("file.separator") + getName() + ".log");
	}
	/**
	 * Get the directory of the log.
	 *
	 * @return the directory
	 */
	public String getDirectory(){
		return dir;
	}
	/**
	 * Get the name of the log.
	 *
	 * @return the name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Get the full path of the log.
	 *
	 * @return the path
	 */
	public Path getPath(){
		return path;
	}
	private void timestampName(){
		DateTimeFormatter stamp = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").withZone(ZoneId.systemDefault());
		name = ((name !=null) ? name + "_" : "") + stamp.format(Instant.now());
	}
	/**
	 * Write a categorized message to the log.
	 *
	 * @param category the category
	 * @param message  the message
	 */
	public void write(Category category, String message){
		if(category.isEnabled()){
			println(category + " | "+ message);
		}
	}
	/**
	 * Write a throwable to the log.
	 *
	 * @param e the throwable
	 */
	public void write(Throwable e){
		if(Category.ERROR.isEnabled()){
			println(Category.ERROR +" | "+ parseThrowable(e));
		}
	}
	/**
	 * Write a blank line to the log.
	 */
	protected void println(){
		try{
			Files.write(path, "\n".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch(Exception e){
			e.printStackTrace();
		}
		if(console){
			System.out.println();
		}
	}
	/**
	 * Timestamp and write multiple lines to the log.
	 *
	 * @param lines the lines
	 */
	protected void println(String...lines){
		for(String line:lines){
			println(line);
		}
	}
	/**
	 * Timestamp and write a line to the log.
	 *
	 * @param line the message
	 */
	protected void println(String line){
		line = TIMESTAMP_FORMATTER.format(Instant.now()) + " | Thread: " +Thread.currentThread().getName()+" | " + line + "\n";
		try{
			Files.write(path, line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch(Exception e){
			e.printStackTrace();
		}
		if(console){
			System.out.print(line);
		}
	}
	/**
	 * Make the log the default log.
	 */
	public void makeDefault(){
		defaultLog = this;
	}
	/**
	 * Categories of log messages.
	 */
	public enum Category{
		/**
		 * Info category. Enabled by default.
		 */
		INFO(),
		/**
		 * Warning category. Enabled by default.
		 */
		WARNING(),
		/**
		 * Error category. Enabled by default.
		 */
		ERROR(),
		/**
		 * Debug category. Disabled by default.
		 */
		DEBUG(false),
		/**
		 * Verbose category. Disabled by default.
		 */
		VERBOSE(false);
		private boolean enabled;
		Category(){
			this(true);
		}
		Category(boolean enabled){
			this.enabled = enabled;
		}
		/**
		 * Enable the category.
		 */
		public void enable(){
			this.enabled = true;
		}
		/**
		 * Disable the category.
		 */
		public void disable(){
			this.enabled = false;
		}
		/**
		 * Check if the category is enabled.
		 *
		 * @return true if the category is enabled.
		 */
		public boolean isEnabled(){
			return enabled;
		}
	}
}
