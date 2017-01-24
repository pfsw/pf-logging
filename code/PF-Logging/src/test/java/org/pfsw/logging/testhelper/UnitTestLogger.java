/*
 * Created on 05.01.2017
 */
package org.pfsw.logging.testhelper;

import static org.pfsw.logging.LogLevel.*;

import java.text.MessageFormat;
import java.util.Properties;

import org.pfsw.logging.LogLevel;
import org.pfsw.logging.Logger;

public class UnitTestLogger implements Logger
{
  private String name;
  private LogLevel currentLogLevel = null;
  private LogRecord logRecord;
  private Throwable throwable;

  public UnitTestLogger()
  {
    super();
  }

  public UnitTestLogger(String name)
  {
    super();
    this.setName(name);
  }
  
  public UnitTestLogger(LogLevel level)
  {
    super();
    this.setCurrentLogLevel(level);
  }

  @Override
  public String getName()
  {
    return this.name;
  }

  @Override
  public void initialize(Properties properties)
  {
    // Auto-generated method stub
  }

  @Override
  public void logException(Throwable ex)
  {
    this.setThrowable(ex);
  }

  @Override
  public void logDebug(String message, Object... params)
  {
    if (this.isLoggingDebugs())
    {      
      this.addLogRecord(DEBUG, message, params);
    }
  }

  @Override
  public void logInfo(String message, Object... params)
  {
    if (this.isLoggingInfos())
    {      
      this.addLogRecord(INFO, message, params);
    }
  }

  @Override
  public void logWarning(String message, Object... params)
  {
    if (this.isLoggingWarnings())
    {      
      this.addLogRecord(WARN, message, params);
    }
  }

  @Override
  public void logError(String message, Object... params)
  {
    if (this.isLoggingErrors())
    {      
      this.addLogRecord(ERROR, message, params);
    }
  }

  @Override
  public void logWarning(String message, Throwable exception)
  {
    if (this.isLoggingWarnings())
    {      
      this.addLogRecord(WARN, message);
      this.setThrowable(exception);
    }
  }

  @Override
  public void logError(String message, Throwable exception)
  {
    if (this.isLoggingErrors())
    {      
      this.addLogRecord(ERROR, message);
      this.setThrowable(exception);
    }
  }

  @Override
  public boolean isLoggingDebugs()
  {
    return DEBUG == this.getCurrentLogLevel();
  }

  @Override
  public boolean isLoggingInfos()
  {
    return (INFO == this.getCurrentLogLevel()) || this.isLoggingDebugs();
  }

  @Override
  public boolean isLoggingWarnings()
  {
    return (WARN == this.getCurrentLogLevel()) || this.isLoggingInfos();
  }

  @Override
  public boolean isLoggingErrors()
  {
    return (ERROR == this.getCurrentLogLevel()) || this.isLoggingWarnings();
  }

  @Override
  public boolean setLogLevel(String newLevel)
  {
    LogLevel logLevel;
    
    logLevel = LogLevel.getByPFLevel(newLevel);
    if (logLevel == null)
    {      
      return false;
    }
    this.setCurrentLogLevel(logLevel);
    return true;
  }

  // --- Not interface Logger --------
  public LogLevel getCurrentLogLevel()
  {
    return this.currentLogLevel;
  }
  
  public Throwable getThrowable()
  {
    return this.throwable;
  }
  
  public LogRecord getLogRecord()
  {
    return this.logRecord;
  }
  
  protected void setThrowable(Throwable throwable)
  {
    this.throwable = throwable;
  }
  
  protected void setLogRecord(LogRecord logRecord)
  {
    this.logRecord = logRecord;
  }
  
  protected void setCurrentLogLevel(LogLevel currentLogLevel)
  {
    this.currentLogLevel = currentLogLevel;
  }
  
  protected void setName(String name)
  {
    this.name = name;
  }
  
  protected void addLogRecord(LogLevel logLevel, String message, Object...params) 
  {
    String text;
    
    text = MessageFormat.format(message, params);
    this.setLogRecord(new LogRecord(logLevel, text));
  }
}
