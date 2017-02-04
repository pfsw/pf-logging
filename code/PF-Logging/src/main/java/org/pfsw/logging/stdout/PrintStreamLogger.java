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

// ===========================================================================
// IMPORTS
// ===========================================================================
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
  public static final int LEVEL_NONE     = 0 ;
	/**
	 * This log level specifies that only error messages will be logged
	 */
	public static final int LEVEL_ERROR    = 1 ;
	/**
	 * This log level specifies that only error and warning messages will be 
	 * logged
	 */
	public static final int LEVEL_WARN     = 2 ;
	/**
	 * This log level specifies that only error, warning and info messages will 
	 * be logged
	 */
	public static final int LEVEL_INFO     = 3 ;
	/**
	 * This log level specifies that all messages will be logged
	 */
	public static final int LEVEL_DEBUG    = 4 ;

	/**
	 * The property that specifies a filename to redirect the log output
	 * <p>"logging.printstream.file"<p>
	 */
	public static final String PROP_OUTPUT_FILE    = "logging.printstream.file" ;
	/**
	 * The property to set the log level. The value must be one of the following 
	 * strings: "NONE", "ERROR", "WARNING", "INFO", "DEBUG"
	 * <p>"logging.level"<p>
	 */
	public static final String PROP_LOG_LEVEL    	= "logging.level" ;
	/**
	 * The property to set the the name of the logger.
	 * <p>"logging.logger.name"<p>
	 */
	public static final String PROP_LOGGER_NAME	= "logging.logger.name" ;
  
  private static final String[] LEVEL_INDICATOR = { "", "E", "W", "I", "D", "X" } ;

  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private int logLevel = LEVEL_INFO;
  /**
   * Returns the current log level
   */  
  public int getLogLevel() { return logLevel ; }
  /**
   * Set the current log level of this logger.
   * <br>
   * The default is LEVEL_ERROR.
   * @param newLevel The new log level (i.e. one of the LEVEL_ constants of this class)
   */
  public void setLogLevel( int newLevel ) { logLevel = newLevel ; }
  
  // -------------------------------------------------------------------------
  
  private PrintStream printStream = System.out ;
  protected PrintStream getPrintStream() { return printStream ; }
  protected void setPrintStream( PrintStream newValue ) { printStream = newValue ; } 
    
  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  /**
   * Initialize the new instance with default values.
   */
  public PrintStreamLogger()
  {
    super() ;
  } // PrintStreamLogger() 

  // -------------------------------------------------------------------------
  
  /**
   * Initialize the new instance with a logger name.
   */
  public PrintStreamLogger(String loggerName)
  {
    super(loggerName) ;
  } // PrintStreamLogger() 
  
  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
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
      if (!this.isNullOrBlank(value))
      {        
        this.initPrintStream(value.trim());
      }

      value = properties.getProperty(PROP_LOG_LEVEL);
      if (!this.isNullOrBlank(value))
      {        
        this.initLogLevel(value.trim());
      }
      
      value = properties.getProperty(PROP_LOGGER_NAME);
      if (!this.isNullOrBlank(value))
      {        
        this.setLoggerName(value.trim());
      }
    }
  } // initialize() 

  // -------------------------------------------------------------------------

  /**
   * Writes the given exception to the log output device(s).
   * The log level will be ignored.
   **/
  @Override
  public void logException(Throwable ex)
  {
    this.printException(ex);
  } // logException() 

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG the given message will be written to
   * the log output device(s).
   **/
  @Override
  public void logDebug(String message, Object... params)
  {
    if (this.isLoggingDebugs())
    {
      this.println(LEVEL_DEBUG, message, params);
    }
  } // logDebug() 

  // -------------------------------------------------------------------------

  /**
   * If the logging level is INFO or DEBUG the given message will be 
   * written to the log output device(s).
   **/
  @Override
  public void logInfo(String message, Object... params)
  {
    if (this.isLoggingInfos())
    {
      this.println(LEVEL_INFO, message, params);
    }
  } // logInfo() 

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG, INFO or WARNING the given message will 
   * be written to the log output device(s).
   **/
  @Override
  public void logWarning(String message, Object... params)
  {
    if (this.isLoggingWarnings())
    {
      this.println(LEVEL_WARN, message, params);
    }
  } // logWarning() 

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG, INFO, WARNING or ERROR the given message 
   * will be written to the log output device(s).
   **/
  @Override
  public void logError(String message, Object... params)
  {
    if (this.isLoggingErrors())
    {
      this.println(LEVEL_ERROR, message, params);
    }
  } // logError() 

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG, INFO or WARNING the given message
   * and the exception will be written to the log output device(s).
   **/
  @Override
  public void logWarning(String message, Throwable exception)
  {
    if (this.isLoggingWarnings())
    {
      this.println(LEVEL_WARN, message);
      this.printException(exception);
    }
  } // logWarning() 

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG, INFO, WARNING or ERROR the given message 
   * and the exception will be written to the log output device(s).
   **/
  @Override
  public void logError(String message, Throwable exception)
  {
    if (this.isLoggingErrors())
    {
      this.println(LEVEL_ERROR, message);
      this.printException(exception);
    }
  } // logError() 

  // -------------------------------------------------------------------------

  /**
   * Returns true, if debug messages will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingDebugs()
  {
    return (this.getLogLevel() >= LEVEL_DEBUG);
  } // isLoggingDebugs() 

  // -------------------------------------------------------------------------

  /**
   * Returns true, if info messages will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingInfos()
  {
    return (this.getLogLevel() >= LEVEL_INFO);
  } // isLoggingInfos() 

  // -------------------------------------------------------------------------
  /**
   * Returns true, if warnings will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingWarnings()
  {
    return (this.getLogLevel() >= LEVEL_WARN);
  } // isLoggingWarnings() 

  // -------------------------------------------------------------------------

  /**
   * Returns true, if errors will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingErrors()
  {
    return (this.getLogLevel() >= LEVEL_ERROR);
  } // isLoggingErrors() 

  // -------------------------------------------------------------------------

  /**
   * Changes the log level to the specified level. Returns true if the level
   * is supported and was set, otherwise false.
   * 
   * @return Always false for this logger
   */
  @Override
  public boolean setLogLevel(String logLevel)
  {
    return this.initLogLevel(logLevel);
  } // setLogLevel() 

  // -------------------------------------------------------------------------

  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected void print(String text)
  {
    this.getPrintStream().print(text);
  } // print() 
  
  // -------------------------------------------------------------------------

  protected void print(int level, String message, Object... params)
  {
    String text;
    
    text = this.replacePlaceholders(message, params);
    
    if (this.useLevelIndicators())
    {
      this.print(this.getLevelIndicator(level));
      this.print(" ");
    }
    this.printLoggerNameIfSet();
    this.print(text);
  } // print() 

  // -------------------------------------------------------------------------

  protected void println(int level, String text, Object... params)
  {
    this.print(level, text, params);
    this.println();
  } // println() 

  // -------------------------------------------------------------------------

  protected void println()
  {
    this.print("\n");
  } // println() 

  // -------------------------------------------------------------------------

  protected void printLoggerNameIfSet()
  {
    if (!this.isNullOrEmpty(this.getName()))
    {
      this.print(this.getName());
      this.print(" ");
    }
  } // printLoggerNameIfSet() 
  
  // -------------------------------------------------------------------------
  
  protected void printException(Throwable ex)
  {
    this.printLoggerNameIfSet();
    ex.printStackTrace(this.getPrintStream());
  } // printException() 

  // -------------------------------------------------------------------------

  protected String getLevelIndicator(int level)
  {
    if ((level < 0) || (level >= LEVEL_INDICATOR.length))
    {
      return LEVEL_INDICATOR[LEVEL_INDICATOR.length - 1];
    }
    return LEVEL_INDICATOR[level];
  } // getLevelIndicator() 

  protected boolean useLevelIndicators()
  {
    return true;
  } // useLevelIndicators() 

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
        this.setPrintStream(ps);
      }
      catch (IOException ex)
      {
        this.logError("Failed to create file '" + filename + "' for logging", ex);
      }
    }
  } // initPrintStream() 

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
      this.setLogLevel(LEVEL_NONE);
    }
    else if (LL_ERROR.equals(levelName))
    {
      this.setLogLevel(LEVEL_ERROR);
    }
    else if (LL_WARNING.equals(levelName))
    {
      this.setLogLevel(LEVEL_WARN);
    }
    else if (LL_INFO.equals(levelName))
    {
      this.setLogLevel(LEVEL_INFO);
    }
    else if (LL_DEBUG.equals(levelName))
    {
      this.setLogLevel(LEVEL_DEBUG);
    }
    else
    {
      return false;
    }

    return true;
  } // initLogLevel()  
} 
