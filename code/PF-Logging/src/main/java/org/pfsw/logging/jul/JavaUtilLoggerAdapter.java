// ===========================================================================
// CONTENT  : CLASS JavaUtilLoggerAdapter
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.1 - 05/01/2017
// HISTORY  :
//  22/06/2014  mdu  CREATED
//  05/01/2017  mdu   changed --> Refer to LogLevel enum
//
// Copyright (c) 2014-2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.jul ;

import java.util.Properties;
import java.util.logging.Level;

import org.pfsw.logging.Logger;
// ===========================================================================
// IMPORTS
// ===========================================================================
import org.pfsw.logging.LogLevel;

/**
 * This logger adapter maps to logger instances of the java.util.logging (JUL)
 * framework.<br>
 * The log level of PF framework are mapped to the JUL log levels in the following way:
 * <ul>
 * <li>ERRROR --> SEVERE</li>
 * <li>WARNUNG --> WARNING</li>
 * <li>INFO --> INFO</li>
 * <li>DEBUG --> FINE</li>
 * <li>NONE --> OFF</li>
 * </ul>
 *
 * @author Manfred Duchrow
 * @version 1.1
 */
class JavaUtilLoggerAdapter implements Logger
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================
  private static final Level DEBUG_LEVEL = LogLevel.DEBUG.getJULLevel();

  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final java.util.logging.Logger julLogger;

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public JavaUtilLoggerAdapter()
  {
    super() ;
    this.julLogger = java.util.logging.Logger.getAnonymousLogger();
  } 

  public JavaUtilLoggerAdapter(String loggerName)
  {
    super() ;
    this.julLogger = java.util.logging.Logger.getLogger(loggerName);
  } 

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public String getName()
  {
    return getJulLogger().getName();
  }

  @Override
  public void initialize(Properties properties)
  {
    // So far nothing to initialize
    // Configuration will be done via properties file specified by
    // -Djava.util.logging.config.file
  }

  @Override
  public void logException(Throwable ex)
  {
    getJulLogger().log(Level.ALL, "Exception: ", ex);
  }

  @Override
  public void logDebug(String message, Object... params)
  {
    getJulLogger().log(DEBUG_LEVEL, message, params);
  }

  @Override
  public void logInfo(String message, Object... params)
  {
    getJulLogger().log(Level.INFO, message, params);
  }

  @Override
  public void logWarning(String message, Object... params)
  {
    getJulLogger().log(Level.WARNING, message, params);
  }
  
  @Override
  public void logError(String message, Object... params)
  {
    getJulLogger().log(Level.SEVERE, message, params);    
  }

  @Override
  public void logWarning(String message, Throwable exception)
  {
    getJulLogger().log(Level.WARNING, message, exception);    
  }

  @Override
  public void logError(String message, Throwable exception)
  {
    getJulLogger().log(Level.SEVERE, message, exception);    
  }

  @Override
  public boolean isLoggingDebugs()
  {
    return getJulLogger().isLoggable(DEBUG_LEVEL);
  }

  @Override
  public boolean isLoggingInfos()
  {
    return getJulLogger().isLoggable(Level.INFO);
  }

  @Override
  public boolean isLoggingWarnings()
  {
    return getJulLogger().isLoggable(Level.WARNING);
  }

  @Override
  public boolean isLoggingErrors()
  {
    return getJulLogger().isLoggable(Level.SEVERE);
  }

  @Override
  public boolean setLogLevel(String level)
  {
    LogLevel logLevel;
    
    logLevel = LogLevel.getByPFLevel(level);
    if (logLevel == null)
    {
      return false;
    }
    getJulLogger().setLevel(logLevel.getJULLevel());
    return true;
  }
  
  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected java.util.logging.Logger getJulLogger()
  {
    return julLogger;
  }
  
} 
