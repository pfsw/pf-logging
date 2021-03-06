// ===========================================================================
// CONTENT  : CLASS Logger2Logger
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 05/01/2017
// HISTORY  :
//  05/01/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

// ===========================================================================
// IMPORTS
// ===========================================================================
import java.util.Properties;

/**
 * This class is a wrapper around a {@link Logger} implementation instance
 * that offers the {@link Logger2} interface on top of the {@link Logger}
 * interface.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class Logger2Logger implements Logger, Logger2
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final Logger delegateLogger;
  private LogLevel logLevel = null;

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public Logger2Logger(Logger logger)
  {
    super();
    delegateLogger = logger;
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================

  // -------------------------------------------------------------------------
  // Logger interface 
  // -------------------------------------------------------------------------
  @Override
  public String getName()
  {
    return this.getDelegateLogger().getName();
  }

  @Override
  public void initialize(Properties properties)
  {
    this.getDelegateLogger().initialize(properties);
  }

  @Override
  public boolean isLoggingDebugs()
  {
    return this.getDelegateLogger().isLoggingDebugs();
  }

  @Override
  public boolean isLoggingInfos()
  {
    return this.getDelegateLogger().isLoggingInfos();
  }

  @Override
  public boolean isLoggingWarnings()
  {
    return this.getDelegateLogger().isLoggingWarnings();
  }

  @Override
  public boolean isLoggingErrors()
  {
    return this.getDelegateLogger().isLoggingErrors();
  }

  @Override
  public void logDebug(String message, Object... params)
  {
    this.getDelegateLogger().logDebug(message, params);
  }

  @Override
  public void logInfo(String message, Object... params)
  {
    this.getDelegateLogger().logInfo(message, params);
  }

  @Override
  public void logWarning(String message, Object... params)
  {
    this.getDelegateLogger().logWarning(message, params);
  }

  @Override
  public void logWarning(String message, Throwable exception)
  {
    this.getDelegateLogger().logWarning(message, exception);
  }

  @Override
  public void logError(String message, Object... params)
  {
    this.getDelegateLogger().logError(message, params);
  }

  @Override
  public void logError(String message, Throwable exception)
  {
    this.getDelegateLogger().logError(message, exception);
  }

  @Override
  public void logException(Throwable ex)
  {
    this.getDelegateLogger().logException(ex);
  }

  @Override
  public boolean setLogLevel(String level)
  {
    LogLevel newLevel;

    newLevel = LogLevel.getByPFLevel(level);
    if (newLevel == null)
    {
      return false;
    }
    this.setLogLevel(newLevel);
    return true;
  }

  // -------------------------------------------------------------------------
  // Logger2 interface 
  // -------------------------------------------------------------------------
  @Override
  public String getLoggerName()
  {
    return this.getDelegateLogger().getName();
  }

  @Override
  public LogLevel getLogLevel()
  {
    return this.logLevel;
  }

  @Override
  public void setLogLevel(LogLevel level)
  {
    this.logLevel = level;
    this.getDelegateLogger().setLogLevel(level.getPFLevel());
  }

  /**
   * Returns true if this logger's log level allows logging on the given level.
   */
  @Override
  public boolean isEnabled(LogLevel level)
  {
    if (level == null)
    {
      return false;
    }
    switch (level)
    {
      case DEBUG :
        return this.isDebugEnabled();
      case INFO :
        return this.isInfoEnabled();
      case WARN :
        return this.isWarnEnabled();
      case ERROR :
        return this.isErrorEnabled();
      default :
        return false;
    }
  }

  /**
   * Returns true if this logger's log level allows debug logging.
   */
  @Override
  public boolean isDebugEnabled()
  {
    return this.getDelegateLogger().isLoggingDebugs();
  }

  /**
   * Returns true if this logger's log level allows info logging.
   */
  @Override
  public boolean isInfoEnabled()
  {
    return this.getDelegateLogger().isLoggingInfos();
  }

  /**
   * Returns true if this logger's log level allows warn logging.
   */
  @Override
  public boolean isWarnEnabled()
  {
    return this.getDelegateLogger().isLoggingWarnings();
  }

  /**
   * Returns true if this logger's log level allows error logging.
   */
  @Override
  public boolean isErrorEnabled()
  {
    return this.getDelegateLogger().isLoggingErrors();
  }

  /**
   * Writes the given message template to the log if log level DEBUG
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  @Override
  public void debugf(String msgTemplate, Object... params)
  {
    if (this.isDebugEnabled())
    {
      this.getDelegateLogger().logDebug(this.getLogMessage(msgTemplate, params));
    }
  }

  /**
   * Writes the given message template to the log if log level DEBUG
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  @Override
  public void debugf(Throwable t, String msgTemplate, Object... params)
  {
    if (this.isDebugEnabled())
    {
      this.getDelegateLogger().logDebug(this.getLogMessage(msgTemplate, params));
      this.getDelegateLogger().logException(t);
    }
  }

  /**
   * Writes the given message template to the log if log level INFO
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  @Override
  public void infof(String msgTemplate, Object... params)
  {
    if (this.isInfoEnabled())
    {
      this.getDelegateLogger().logInfo(this.getLogMessage(msgTemplate, params));
    }
  }

  /**
   * Writes the given message template to the log if log level INFO
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  @Override
  public void infof(Throwable t, String msgTemplate, Object... params)
  {
    if (this.isInfoEnabled())
    {
      this.getDelegateLogger().logInfo(this.getLogMessage(msgTemplate, params));
      this.getDelegateLogger().logException(t);
    }
  }

  /**
   * Writes the given message template to the log if log level WARNING
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  @Override
  public void warnf(String msgTemplate, Object... params)
  {
    if (this.isWarnEnabled())
    {
      this.getDelegateLogger().logWarning(this.getLogMessage(msgTemplate, params));
    }
  }

  /**
   * Writes the given message template to the log if log level WARNING
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  @Override
  public void warnf(Throwable t, String msgTemplate, Object... params)
  {
    if (this.isWarnEnabled())
    {
      this.getDelegateLogger().logWarning(this.getLogMessage(msgTemplate, params), t);
    }
  }

  /**
   * Writes the given message template to the log if log level ERROR
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  @Override
  public void errorf(String msgTemplate, Object... params)
  {
    if (this.isErrorEnabled())
    {
      this.getDelegateLogger().logError(this.getLogMessage(msgTemplate, params));
    }
  }

  /**
   * Writes the given message template to the log if log level ERROR
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  @Override
  public void errorf(Throwable t, String msgTemplate, Object... params)
  {
    if (this.isErrorEnabled())
    {
      this.getDelegateLogger().logError(this.getLogMessage(msgTemplate, params), t);
    }
  }

  /**
   * Writes the given message template to the log if the specified log level
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  @Override
  public void logf(LogLevel level, String msgTemplate, Object... params)
  {
    if (level == null)
    {
      return;
    }
    switch (level)
    {
      case DEBUG :
        this.debugf(msgTemplate, params);
        break;
      case INFO :
        this.infof(msgTemplate, params);
        break;
      case WARN :
        this.warnf(msgTemplate, params);
        break;
      case ERROR :
        this.errorf(msgTemplate, params);
        break;
      default :
        break;
    }
  }

  /**
   * Writes the given message template to the log if the specified log level
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  @Override
  public void logf(LogLevel level, Throwable t, String msgTemplate, Object... params)
  {
    if (level == null)
    {
      return;
    }
    switch (level)
    {
      case DEBUG :
        this.debugf(t, msgTemplate, params);
        break;
      case INFO :
        this.infof(t, msgTemplate, params);
        break;
      case WARN :
        this.warnf(t, msgTemplate, params);
        break;
      case ERROR :
        this.errorf(t, msgTemplate, params);
        break;
      default :
        break;
    }
  }

  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected String getLogMessage(String template, Object... params)
  {
    return String.format(template, params);
  }

  protected Logger getDelegateLogger()
  {
    return this.delegateLogger;
  }
}
