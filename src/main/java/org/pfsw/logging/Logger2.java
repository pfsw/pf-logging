// ===========================================================================
// CONTENT  : INTERFACE Logger2
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 05/01/2017
// HISTORY  :
//  05/01/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging ;

/**
 * Another logger interface that is close to the JBoss logging API. 
 * It particularly specifies the formatting log methods. 
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public interface Logger2
{   
  /**
   * Returns the name of this logger.
   */
  public String getLoggerName();

  /**
   * Returns the log level of this logger.
   */
  public LogLevel getLogLevel(); 

  /**
   * Sets the log level of this logger.
   */
  public void setLogLevel(LogLevel logLevel); 
  
  /**
   * Writes the given message template to the log if log level DEBUG
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  public void debugf(String msgTemplate, Object...params);
  /**
   * Writes the given message template to the log if log level DEBUG
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  public void debugf(Throwable t, String msgTemplate, Object...params);

  /**
   * Writes the given message template to the log if log level INFO
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  public void infof(String msgTemplate, Object...params);
  /**
   * Writes the given message template to the log if log level INFO
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  public void infof(Throwable t, String msgTemplate, Object...params);
  
  /**
   * Writes the given message template to the log if log level WARNING
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  public void warnf(String msgTemplate, Object...params);
  /**
   * Writes the given message template to the log if log level WARNING
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  public void warnf(Throwable t, String msgTemplate, Object...params);
  
  /**
   * Writes the given message template to the log if log level ERROR
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  public void errorf(String msgTemplate, Object...params);
  /**
   * Writes the given message template to the log if log level ERROR
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  public void errorf(Throwable t, String msgTemplate, Object...params);
  
  /**
   * Writes the given message template to the log if the specified log level
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   */
  public void logf(LogLevel logLevel, String msgTemplate, Object...params);
  /**
   * Writes the given message template to the log if the specified log level
   * is activated for this logger. Placeholders in the message template
   * will be replaced by the given (optional) parameters.
   * <p>
   * The provided throwable will also be sent to the log after the message.
   * It probably (depending on configuration) will be written as stacktrace.
   */
  public void logf(LogLevel logLevel, Throwable t, String msgTemplate, Object...params);
  
  /**
   * Returns true if this logger's log level allows logging on the given level.
   */
  public boolean isEnabled(LogLevel logLevel);
  
  /**
   * Returns true if this logger's log level allows debug logging.
   */
  public boolean isDebugEnabled();
  
  /**
   * Returns true if this logger's log level allows info logging.
   */
  public boolean isInfoEnabled();
  
  /**
   * Returns true if this logger's log level allows warn logging.
   */
  public boolean isWarnEnabled();
  
  /**
   * Returns true if this logger's log level allows error logging.
   */
  public boolean isErrorEnabled();
}