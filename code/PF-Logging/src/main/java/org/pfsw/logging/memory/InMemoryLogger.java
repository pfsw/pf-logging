// ===========================================================================
// CONTENT  : CLASS InMemoryLogger
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.pfsw.logging.LogLevel;
import org.pfsw.logging.internal.AbstractLogger;

/**
 * This logger keeps all log output in an internal list on the heap.
 * <p>
 * This of course is not reasonable for a production system, but could be
 * helpful for unit testing.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class InMemoryLogger extends AbstractLogger
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================

  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final List<LogRecord> logEntries = new ArrayList<LogRecord>();
  private LogLevel logLevel = LogLevel.INFO;

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public InMemoryLogger(String loggerName)
  {
    super(loggerName);
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public void initialize(Properties properties)
  {
    // Nothing yet
  }

  @Override
  public boolean isLoggingDebugs()
  {
    return this.getLogLevel() == LogLevel.DEBUG;
  }

  @Override
  public boolean isLoggingInfos()
  {
    return (this.getLogLevel() == LogLevel.INFO) || isLoggingDebugs();
  }

  @Override
  public boolean isLoggingWarnings()
  {
    return (this.getLogLevel() == LogLevel.WARN) || isLoggingInfos();
  }

  @Override
  public boolean isLoggingErrors()
  {
    return (this.getLogLevel() == LogLevel.ERROR) || isLoggingWarnings();
  }

  @Override
  public void logDebug(String message, Object... params)
  {
    if (this.isLoggingDebugs())
    {
      this.add(LogLevel.DEBUG, message, params);
    }
  }

  @Override
  public void logInfo(String message, Object... params)
  {
    if (this.isLoggingInfos())
    {
      this.add(LogLevel.INFO, message, params);
    }
  }

  @Override
  public void logWarning(String message, Object... params)
  {
    if (this.isLoggingWarnings())
    {
      this.add(LogLevel.WARN, message, params);
    }
  }

  @Override
  public void logWarning(String message, Throwable exception)
  {
    if (this.isLoggingWarnings())
    {
      this.add(LogLevel.WARN, exception, message);
    }
  }

  @Override
  public void logError(String message, Object... params)
  {
    if (this.isLoggingErrors())
    {
      this.add(LogLevel.ERROR, message, params);
    }
  }

  @Override
  public void logError(String message, Throwable exception)
  {
    if (this.isLoggingErrors())
    {
      this.add(LogLevel.ERROR, exception, message);
    }
  }

  @Override
  public void logException(Throwable exception)
  {
    this.add(this.getLogLevel(), exception, "");
  }

  @Override
  public boolean setLogLevel(String level)
  {
    LogLevel pfLevel;

    pfLevel = LogLevel.getByPFLevel(level);
    if (pfLevel == null)
    {
      return false;
    }
    this.setLogLevel(pfLevel);
    return false;
  }

  public int size() 
  {
    return this.getLogEntries().size();
  }
  
  public boolean isEmpty() 
  {
    return this.getLogEntries().isEmpty();
  }
  
  public void clear() 
  {
    this.getLogEntries().clear();
  }
  
  /**
   * Returns a list of all log records with a message that contains 
   * the given text.
   * 
   * @param text The text to search for (must not be null).
   * @return A list of found records (never null).
   */
  public List<LogRecord> findEntriesContaining(String text) 
  {
    List<LogRecord> result;
    
    result = new ArrayList<LogRecord>();
    for (LogRecord logRecord : this.getLogEntries())
    {
      if (logRecord.getMessage().contains(text))
      {
        result.add(logRecord);
      }
    }
    return result;
  }
  
  public List<LogRecord> getLogEntries()
  {
    return this.logEntries;
  }
  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected synchronized void add(LogLevel level, String message, Object... params)
  {
    this.add(level, null, message, params);
  }

  protected synchronized void add(LogLevel level, Throwable exception, String message, Object... params)
  {
    LogRecord record;
    String text;

    text = this.replacePlaceholders(message, params);
    record = new LogRecord(level, text, exception);
    this.getLogEntries().add(record);
  }

  protected LogLevel getLogLevel()
  {
    return this.logLevel;
  }

  protected void setLogLevel(LogLevel logLevel)
  {
    this.logLevel = logLevel;
  }

}
