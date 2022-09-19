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
    return getLogLevel() == LogLevel.DEBUG;
  }

  @Override
  public boolean isLoggingInfos()
  {
    return (getLogLevel() == LogLevel.INFO) || isLoggingDebugs();
  }

  @Override
  public boolean isLoggingWarnings()
  {
    return (getLogLevel() == LogLevel.WARN) || isLoggingInfos();
  }

  @Override
  public boolean isLoggingErrors()
  {
    return (getLogLevel() == LogLevel.ERROR) || isLoggingWarnings();
  }

  @Override
  public void logDebug(String message, Object... params)
  {
    if (isLoggingDebugs())
    {
      add(LogLevel.DEBUG, message, params);
    }
  }

  @Override
  public void logInfo(String message, Object... params)
  {
    if (isLoggingInfos())
    {
      add(LogLevel.INFO, message, params);
    }
  }

  @Override
  public void logWarning(String message, Object... params)
  {
    if (isLoggingWarnings())
    {
      add(LogLevel.WARN, message, params);
    }
  }

  @Override
  public void logWarning(String message, Throwable exception)
  {
    if (isLoggingWarnings())
    {
      add(LogLevel.WARN, exception, message);
    }
  }

  @Override
  public void logError(String message, Object... params)
  {
    if (isLoggingErrors())
    {
      add(LogLevel.ERROR, message, params);
    }
  }

  @Override
  public void logError(String message, Throwable exception)
  {
    if (isLoggingErrors())
    {
      add(LogLevel.ERROR, exception, message);
    }
  }

  @Override
  public void logException(Throwable exception)
  {
    add(getLogLevel(), exception, "");
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
    setLogLevel(pfLevel);
    return false;
  }

  public int size()
  {
    return getLogEntries().size();
  }

  public boolean isEmpty()
  {
    return getLogEntries().isEmpty();
  }

  public void clear()
  {
    getLogEntries().clear();
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
    for (LogRecord logRecord : getLogEntries())
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
    return logEntries;
  }
  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected synchronized void add(LogLevel level, String message, Object... params)
  {
    add(level, null, message, params);
  }

  protected synchronized void add(LogLevel level, Throwable exception, String message, Object... params)
  {
    LogRecord record;
    String text;

    text = replacePlaceholders(message, params);
    record = new LogRecord(level, text, exception);
    getLogEntries().add(record);
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
