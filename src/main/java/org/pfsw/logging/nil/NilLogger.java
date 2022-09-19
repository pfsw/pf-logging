// ===========================================================================
// CONTENT  : CLASS NilLogger
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.2 - 21/06/2014
// HISTORY  :
//  30/11/2001  duma  CREATED
//	23/08/2006	mdu		added		-->	setLogLevel()
//  21/06/2014  mdu   added   --> getName() and var-arg methods
//
// Copyright (c) 2001-2014, by Manfred Duchrow. All rights reserved.
// ===========================================================================
package org.pfsw.logging.nil;

import java.util.Properties;

import org.pfsw.logging.Logger;

/**
 * This class implements the Logger interface but doesn't write anything
 * to any output device.
 * It could be used to provide a dummy logger implementation for an
 * application that wants to use logging, but has no logging component
 * available (in the classpath).
 *
 * @author Manfred Duchrow
 * @version 1.2
 */
public class NilLogger implements Logger
{
  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  /**
   * Initialize the new instance with default values.
   */
  public NilLogger()
  {
    super();
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public String getName()
  {
    return "NilLogger";
  }

  /**
   * Initialize the logger from the given properties settings.
   **/
  @Override
  public void initialize(Properties properties)
  {
    // Template method that can be overridden by subclasses
  }

  /**
   * Writes the given exception to the log output device(s).
   * The log level will be ignored.
   **/
  @Override
  public void logException(Throwable ex)
  {
    // Template method that can be overridden by subclasses
  }

  /**
   * If the logging level is DEBUG the given message will be written to
   * the log output device(s).
   **/
  @Override
  public void logDebug(String message, Object... params)
  {
    // Template method that can be overridden by subclasses
  }

  /**
   * If the logging level is INFO or DEBUG the given message will be 
   * written to the log output device(s).
   **/
  @Override
  public void logInfo(String message, Object... params)
  {
    // Template method that can be overridden by subclasses
  }

  /**
   * If the logging level is DEBUG, INFO or WARNING the given message will 
   * be written to the log output device(s).
   **/
  @Override
  public void logWarning(String message, Object... params)
  {
    // Template method that can be overridden by subclasses
  }

  /**
   * If the logging level is DEBUG, INFO, WARNING or ERROR the given message 
   * will be written to the log output device(s).
   **/
  @Override
  public void logError(String message, Object... params)
  {
    // Template method that can be overridden by subclasses
  }

  /**
   * If the logging level is DEBUG, INFO or WARNING the given message
   * and the exception will be written to the log output device(s).
   **/
  @Override
  public void logWarning(String message, Throwable exception)
  {
    // Template method that can be overridden by subclasses
  }

  /**
   * If the logging level is DEBUG, INFO, WARNING or ERROR the given message 
   * and the exception will be written to the log output device(s).
   **/
  @Override
  public void logError(String message, Throwable exception)
  {
    // Template method that can be overridden by subclasses
  }

  /**
   * Returns true, if debug messages will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingDebugs()
  {
    return false;
  }

  /**
   * Returns true, if info messages will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingInfos()
  {
    return false;
  }

  /**
   * Returns true, if warnings will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingWarnings()
  {
    return false;
  }

  /**
   * Returns true, if errors will be written to the output device(s).
   **/
  @Override
  public boolean isLoggingErrors()
  {
    return false;
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
    return false;
  }

}
