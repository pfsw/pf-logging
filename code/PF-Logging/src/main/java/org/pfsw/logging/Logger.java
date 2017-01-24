// ===========================================================================
// CONTENT  : INTERFACE Logger
// AUTHOR   : Manfred Duchrow
// VERSION  : 2.0 - 21/06/2014
// HISTORY  :
//  30/11/2001  duma  CREATED
//	23/08/2006	mdu		added		-->	setLogLevel()
//  21/06/2014  mdu   added   --> getName() and var-arg methods
//
// Copyright (c) 2001-2014, by Manfred Duchrow. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

// ===========================================================================
// IMPORTS
// ===========================================================================
import java.util.Properties;

/**
 * A simple interface for all necessary logging functions.
 * Different kinds of logging components like log4j or JLog or
 * java.util.logging can be wrapped in an implementation
 * that supports this interface.
 * So programming against this interface means, to stay independent 
 * of the underlying logging component.
 *
 * @author Manfred Duchrow
 * @version 2.0
 */
public interface Logger
{
  /**
   * This is the log level string representation for NONE
   */
  public static final String LL_NONE    = "NONE" ;
  /**
   * This is the log level string representation for ERROR
   */
  public static final String LL_ERROR    = "ERROR" ;
  /**
   * This is the log level string representation for WARNING
   */
  public static final String LL_WARNING    = "WARNING" ;
  /**
   * This is the log level string representation for INFO
   */
  public static final String LL_INFO    = "INFO" ;
  /**
   * This is the log level string representation for DEBUG
   */
  public static final String LL_DEBUG    = "DEBUG" ;

  // -------------------------------------------------------------------------

  /**
   * Returns the name of this logger. The returned name might be
   * an empty string, but never null.
   */
  public String getName();

  // -------------------------------------------------------------------------
  
  /**
   * Initialize the logger from the given properties settings.
   **/
  public void initialize(Properties properties);

  // -------------------------------------------------------------------------

  /**
   * Writes the given exception to the log output device(s).
   * The log level will be ignored.
   **/
  public void logException(Throwable ex);

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG the given message will be written to
   * the log output device(s).
   * 
   * @param message The message to log. It may contain placeholders like {0}, {1} etc.
   * @param params Optional parameters to be used for replacing placeholders in the message text.
   **/
  public void logDebug(String message, Object... params);

  // -------------------------------------------------------------------------

  /**
   * If the logging level is INFO or DEBUG the given message will be 
   * written to the log output device(s).
   * 
   * @param message The message to log. It may contain placeholders like {0}, {1} etc.
   * @param params Optional parameters to be used for replacing placeholders in the message text.
   **/
  public void logInfo(String message, Object... params);

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG, INFO or WARNING the given message will 
   * be written to the log output device(s).
   * 
   * @param message The message to log. It may contain placeholders like {0}, {1} etc.
   * @param params Optional parameters to be used for replacing placeholders in the message text.
   **/
  public void logWarning(String message, Object... params);

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG, INFO, WARNING or ERROR the given message 
   * will be written to the log output device(s).
   * 
   * @param message The message to log. It may contain placeholders like {0}, {1} etc.
   * @param params Optional parameters to be used for replacing placeholders in the message text.
   **/
  public void logError(String message, Object... params);

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG, INFO or WARNING the given message
   * and the exception will be written to the log output device(s).
   **/
  public void logWarning(String message, Throwable exception);

  // -------------------------------------------------------------------------

  /**
   * If the logging level is DEBUG, INFO, WARNING or ERROR the given message 
   * and the exception will be written to the log output device(s).
   **/
  public void logError(String message, Throwable exception);

  // -------------------------------------------------------------------------

  /**
   * Returns true, if debug messages will be written to the output device(s).
   **/
  public boolean isLoggingDebugs();

  // -------------------------------------------------------------------------

  /**
   * Returns true, if info messages will be written to the output device(s).
   **/
  public boolean isLoggingInfos();

  // -------------------------------------------------------------------------
  /**
   * Returns true, if warnings will be written to the output device(s).
   **/
  public boolean isLoggingWarnings();

  // -------------------------------------------------------------------------
  /**
   * Returns true, if errors will be written to the output device(s).
   **/
  public boolean isLoggingErrors();

  // -------------------------------------------------------------------------

  /**
   * Changes the log level to the specified level. Returns true if the level
   * is supported and was set, otherwise false.
   * 
   * @param logLevel One of the LL_ constants defined in this interface (e.g. {@link #LL_ERROR}).
   */
  public boolean setLogLevel(String logLevel);

  // -------------------------------------------------------------------------

} // interface Logger