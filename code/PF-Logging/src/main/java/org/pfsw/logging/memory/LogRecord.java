// ===========================================================================
// CONTENT  : CLASS LogRecord
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 04/02/2017
// HISTORY  :
//  04/02/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.memory;

// ===========================================================================
// IMPORTS
// ===========================================================================
import org.pfsw.logging.LogLevel;

/**
 * Simple container for a single log output.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class LogRecord
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final LogLevel logLevel;
  private final String message;
  private final Throwable exception;
  
  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public LogRecord(LogLevel logLevel, String message)
  {
    this(logLevel, message, null);
  }

  public LogRecord(LogLevel logLevel, String message, Throwable exception)
  {
    super();
    this.logLevel = logLevel;
    this.message = message;
    this.exception = exception;
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================  
  public LogLevel getLogLevel()
  {
    return this.logLevel;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public Throwable getException()
  {
    return this.exception;
  }
}
