// ===========================================================================
// CONTENT  : CLASS PrintStreamLogger
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.3 - 21/06/2014
// HISTORY  :
//  30/11/2001  duma  CREATED
//	17/10/2003	duma	changed	-->	log level constants and methods are now public
//	06/11/2003	duma	changed	-->	Check properties == null in initialize()
//	20/12/2003	duma	changed	-->	Visibility of setLogLevel() from protected to public
//  21/06/2014  mdu   added   --> getName() and var-arg methods
//
// Copyright (c) 2001-2014, by Manfred Duchrow. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.pfsw.logging.internal.AbstractLogger;

/**
 * This logger supports simple output to a print stream. By default that
 * print stream is stdout. But it can changed by setting the property
 * 'logging.printstream.file' to a filename. Then it will open that file
 * at first access and appends all output to it.
 * <p>
 * The initial log level is ERROR. It can be changed via the property 
 * 'logging.level' (e.g. logging.level=WARNING). 
 *
 * @author Manfred Duchrow
 * @version 1.3
 */
public class PrintStreamLogger extends AbstractLogger
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================
  /**
   * This log level specifies that no message at all will be logged
   */
  public static final int LEVEL_NONE = 0;
  /**
   * This log level specifies that only error messages will be logged
   */
  public static final int LEVEL_ERROR = 1;
  /**
   * This log level specifies that only error and warning messages will be 
   * logged
   */
  public static final int LEVEL_WARN = 2;
  /**
   * This log level specifies that only error, warning and info messages will 
   * be logged
   */
  public static final int LEVEL_INFO = 3;
  /**
   * This log level specifies that all messages will be logged
   */
  public static final int LEVEL_DEBUG = 4;

  /**
   * The property that specifies a filename to redirect the log output
   * <p>"logging.printstream.file"<p>
   */
  public static final String PROP_OUTPUT_FILE = "logging.printstream.file";
  /**
   * The property to set the log level. The value must be one of the following 
   * strings: "NONE", "ERROR", "WARNING", "INFO", "DEBUG"
   * <p>"logging.level"<p>
   */
  public static final String PROP_LOG_LEVEL = "logging.level";
  /**
   * The property to set the the name of the logger.
   * <p>"logging.logger.name"<p>
   */
  public static final String PROP_LOGGER_NAME = "logging.logger.name";

  private static final String[] LEVEL_INDICATOR = { "", "E", "W", "I", "D", "X" };

  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private int logLevel = LEVEL_INFO;
  private PrintStream printStream = System.out;

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  /**
   * Initialize the new instance with default values.
   */
  public PrintStreamLogger()
  {
    super();
  }

  /**
   * Initialize the new instance with a logger name.
   */
  public PrintStreamLogger(String loggerName)
  {
    super(loggerName);
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  /**
   * Returns the current log level
   */
  public int getLogLevel()
  {
    return logLevel;
  }

  /**
   * Set the current log level of this logger.
   * <br>
   * The default is LEVEL_ERROR.
   * @param newLevel The new log level (i.e. one of the LEVEL_ constants of this class)
   */
  public void setLogLevel(int newLevel)
  {
    logLevel = newLevel;
  }

  /**
   * Initialize the logger from the given properties settings.
   * Currently the following properties are supported:
   * <dl>
   * <dt>logging.printstream.file</dt>
   * <dd>The name of a file to which all logging should be redirected</dd>
   * <dt>logging.level</dt>
   * <dd>The log level. Must be one of "NONE", "ERROR", "WARNING", "INFO", "DEBUG"</dd>
   * </dl>
   **/
  @Override
  public void initialize(Properties properties)
  {
    String value = null;

    if (properties != null)
    {
      value = properties.getProperty(PROP_OUTPUT_FILE);
      if (!isNullOrBlank(value))
      {
        initPrintStream(value.trim());
      }

      value = properties.getProperty(PROP_LOG_LEVEL);
      if (!isNullOrBlank(value))
      {
        initLogLevel(value.trim());
      }

      value = properties.getProperty(PROP_LOGGER_NAME);
      if (!isNullOrBlank(value))
      {
        setLoggerName(value.trim());
      }
    }
  }

  /**
   * Writes the given exception to the log output device(s).
   * The log level will be ignored.
   **/
  @Override
  public void logException(Throwable ex)
  {
    printException(ex);
  }

  /**
   * If the logging level is DEBUG the given message will be written to
   * the log output device(s).
   **/
  @Override
  public void logDebug(String message, Object... params)
  {
    if (isLoggingDebugs())
    {
      println(LEVEL_DEBUG, message, params);
    }
  }

  /**
   * If the logging level is INFO or DEBUG the given message will be 
   * written to the log output device(s).
   **/
  @Override
  public void logInfo(String message, Object... params)
  {
    if (isLoggingInfos())
    {
      println(LEVEL_INFO, message, params);
    }
  }

  /**
   * If the logging level is DEBUG, INFO or WARNING the given message will 
   * be written to the log output device(s).
   **/
  @Override
  public void logWarning(String message, Object... params)
  {
    if (isLoggingWarnings())
    {
      println(LEVEL_WARN, message, params);
    }
  }

  /**
   * If the logging level is DEBUG, INFO, WARNING or ERROR the given message 
   * will be written to the log output device(s).
   **/
  @Override
  public void logError(String message, Object... params)
  {
    if (isLoggingErrors())
    {
      println(LEVEL_ERROR, message, params);
    }
  }

  /**
   * If the logging level is DEBUG, INFO or WARNING the given message
   * and the exception will be written to the log output device(s).
   **/
  @Override
  public void logWarning(String message, Throwable exception)
  {
    if (isLoggingWarnings())
    {
      println(LEVEL_WARN, message);
      printException(exception);
    }
  }

  /**
   * If the logging level is DEBUG, INFO, WARNING or ERROR the given message 
   * and the exception will be written to the log output device(s).
   **/
  @Override
  public void logError(String message, Throwable exception)
  {
    if (isLoggingErrors())
    {
      println(LEVEL_ERROR, message);
      printException(exception);
    }
  }

  /**
   * Returns true, if debug messages will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingDebugs()
  {
    return (getLogLevel() >= LEVEL_DEBUG);
  }

  /**
   * Returns true, if info messages will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingInfos()
  {
    return (getLogLevel() >= LEVEL_INFO);
  }

  /**
   * Returns true, if warnings will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingWarnings()
  {
    return (getLogLevel() >= LEVEL_WARN);
  }

  /**
   * Returns true, if errors will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingErrors()
  {
    return (getLogLevel() >= LEVEL_ERROR);
  }

  /**
   * Changes the log level to the specified level. Returns true if the level
   * is supported and was set, otherwise false.
   * 
   * @return Always false for this logger
   */
  @Override
  public boolean setLogLevel(String logLevel)
  {
    return initLogLevel(logLevel);
  }

  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected void print(String text)
  {
    getPrintStream().print(text);
  }

  protected void print(int level, String message, Object... params)
  {
    String text;

    text = replacePlaceholders(message, params);

    if (useLevelIndicators())
    {
      print(getLevelIndicator(level));
      print(" ");
    }
    printLoggerNameIfSet();
    print(text);
  }

  protected void println(int level, String text, Object... params)
  {
    print(level, text, params);
    println();
  }

  protected void println()
  {
    print("\n");
  }

  protected void printLoggerNameIfSet()
  {
    if (!isNullOrEmpty(getName()))
    {
      print(getName());
      print(" ");
    }
  }

  protected void printException(Throwable ex)
  {
    printLoggerNameIfSet();
    ex.printStackTrace(getPrintStream());
  }

  protected String getLevelIndicator(int level)
  {
    if ((level < 0) || (level >= LEVEL_INDICATOR.length))
    {
      return LEVEL_INDICATOR[LEVEL_INDICATOR.length - 1];
    }
    return LEVEL_INDICATOR[level];
  }

  protected boolean useLevelIndicators()
  {
    return true;
  }

  @SuppressWarnings("resource")
  protected void initPrintStream(String filename)
  {
    File file = null;
    FileOutputStream os = null;
    PrintStream ps = null;

    if (filename != null)
    {
      try
      {
        file = new File(filename);
        os = new FileOutputStream(file);
        ps = new PrintStream(os);
        setPrintStream(ps);
      }
      catch (IOException ex)
      {
        logError("Failed to create file '" + filename + "' for logging", ex);
      }
    }
  }

  protected boolean initLogLevel(String level)
  {
    String levelName;

    if (level == null)
    {
      return false;
    }

    levelName = level.toUpperCase();

    if (LL_NONE.equals(levelName))
    {
      setLogLevel(LEVEL_NONE);
    }
    else if (LL_ERROR.equals(levelName))
    {
      setLogLevel(LEVEL_ERROR);
    }
    else if (LL_WARNING.equals(levelName))
    {
      setLogLevel(LEVEL_WARN);
    }
    else if (LL_INFO.equals(levelName))
    {
      setLogLevel(LEVEL_INFO);
    }
    else if (LL_DEBUG.equals(levelName))
    {
      setLogLevel(LEVEL_DEBUG);
    }
    else
    {
      return false;
    }

    return true;
  }

  protected PrintStream getPrintStream()
  {
    return this.printStream;
  }

  protected void setPrintStream(PrintStream newValue)
  {
    this.printStream = newValue;
  }
}
