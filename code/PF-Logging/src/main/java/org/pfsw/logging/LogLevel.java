// ===========================================================================
// CONTENT  : ENUM LogLevel
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 05/01/2017
// HISTORY  :
//  05/01/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

import java.util.logging.Level;

/**
 * A log level definition that maps PFSW log levels to Java Util log levels. 
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public enum LogLevel
{ 
  ERROR(Logger.LL_ERROR, Level.SEVERE),
  WARN(Logger.LL_WARNING, Level.WARNING),
  INFO(Logger.LL_INFO, Level.INFO),
  DEBUG(Logger.LL_DEBUG, Level.FINE),
  NONE(Logger.LL_NONE, Level.OFF);
  
  private final String pfLevel;
  private final Level julLevel;
  
  /**
   * Returns the LogLevel that matches the given level string
   * definition (see {@link Logger} LL_ constants) 
   * or null if no match found.
   */
  public static LogLevel getByPFLevel(String level)
  {
    for (LogLevel logLevel : values())
    {
      if (logLevel.getPFLevel().equalsIgnoreCase(level))
      {
        return logLevel;
      }
    }
    return null;
  }
  
  /**
   * Returns the LogLevel that matches the given Java level
   * or null if no match found.
   */
  public static LogLevel getByJULLevel(Level level)
  {
    for (LogLevel logLevel : values())
    {
      if (logLevel.getJULLevel().equals(level))
      {
        return logLevel;
      }
    }
    return null;
  }
  
  private LogLevel(String pfLevel, Level julLevel)
  {
    this.pfLevel = pfLevel;
    this.julLevel = julLevel;
  }
  
  public String getPFLevel()
  {
    return this.pfLevel;
  }
  
  public Level getJULLevel()
  {
    return this.julLevel;
  }
}