package org.pfsw.logging.testhelper;

import org.pfsw.logging.LogLevel;

public class LogRecord
{
  private LogLevel logLevel;
  private String message;
  
  public LogRecord()
  {
    super();
  }
  
  public LogRecord(LogLevel level, String message)
  {
    super();
    this.setLogLevel(level);
    this.setMessage(message);
  }
  
  public LogLevel getLogLevel()
  {
    return this.logLevel;
  }
  public void setLogLevel(LogLevel logLevel)
  {
    this.logLevel = logLevel;
  }
  public String getMessage()
  {
    return this.message;
  }
  public void setMessage(String message)
  {
    this.message = message;
  }
}
