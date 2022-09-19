// ===========================================================================
// CONTENT  : CLASS DeferredInitializationLoggerAdapter
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 04/02/2017
// HISTORY  :
//  04/02/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.deferred ;

// ===========================================================================
// IMPORTS
// ===========================================================================
import java.util.Properties;

import org.pfsw.logging.Logger ;
import org.pfsw.logging.LoggerFactory;
import org.pfsw.logging.LoggerFactoryProvider;
import org.pfsw.logging.stdout.PrintStreamLogger;

/**
 * This is a substitute logger that initially delegates all method calls to a
 * {@link PrintStreamLogger} instance.
 * <p>
 * However, with every method it checks the internal logger factory registry
 * for the actually desired logger factory type. If it finds it, it creates
 * a new logger with that factory and replaced the underlying logger to which 
 * it delegates all method calls.
 * <p>
 * This approach allows static logger initialization even before the desired logger 
 * adapter has been registered. As soon as the desired logger adapter is available,
 * logging will be re-routed to this adapter. 
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class DeferredInitializationLoggerAdapter implements Logger
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final String desiredLoggerType;
  private final String loggerName;
  private Logger targetLogger;
  private String ownLogLevel = Logger.LL_INFO;

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public DeferredInitializationLoggerAdapter(String desiredLoggerType)
  {
    this(desiredLoggerType, ANONYMOUS_LOGGER_NAME);
  }

  public DeferredInitializationLoggerAdapter(String desiredLoggerType, Class<?> clazz)
  {
    this(desiredLoggerType, clazz.getName());
  }
  
  public DeferredInitializationLoggerAdapter(String desiredLoggerType, String loggerName)
  {
    super();
    this.desiredLoggerType = desiredLoggerType;
    this.loggerName = loggerName;
    this.initTargetLogger();
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public String getName()
  {
    return this.getLoggerName();
  }

  @Override
  public void initialize(Properties properties)
  {
    this.checkTargetLogger();
    this.getTargetLogger().initialize(properties);
  }

  @Override
  public boolean isLoggingDebugs()
  {
    this.checkTargetLogger();
    return this.getTargetLogger().isLoggingDebugs();
  }

  @Override
  public boolean isLoggingInfos()
  {
    this.checkTargetLogger();
    return this.getTargetLogger().isLoggingInfos();
  }

  @Override
  public boolean isLoggingWarnings()
  {
    this.checkTargetLogger();
    return this.getTargetLogger().isLoggingWarnings();
  }

  @Override
  public boolean isLoggingErrors()
  {
    this.checkTargetLogger();
    return this.getTargetLogger().isLoggingErrors();
  }

  @Override
  public void logDebug(String message, Object... params)
  {
    this.checkTargetLogger();
    this.getTargetLogger().logDebug(message, params);
  }

  @Override
  public void logInfo(String message, Object... params)
  {
    this.checkTargetLogger();
    this.getTargetLogger().logInfo(message, params);
  }

  @Override
  public void logWarning(String message, Object... params)
  {
    this.checkTargetLogger();
    this.getTargetLogger().logWarning(message, params);
  }

  @Override
  public void logWarning(String message, Throwable exception)
  {
    this.checkTargetLogger();
    this.getTargetLogger().logWarning(message, exception);
  }

  @Override
  public void logError(String message, Object... params)
  {
    this.checkTargetLogger();
    this.getTargetLogger().logError(message, params);
  }

  @Override
  public void logError(String message, Throwable exception)
  {
    this.checkTargetLogger();
    this.getTargetLogger().logError(message, exception);
  }

  @Override
  public void logException(Throwable ex)
  {
    this.checkTargetLogger();
    this.getTargetLogger().logException(ex);
  }

  @Override
  public boolean setLogLevel(String level)
  {
    this.setOwnLogLevel(level);
    this.checkTargetLogger();
    return this.getTargetLogger().setLogLevel(level);
  }
  
  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected String getLoggerName()
  {
    return this.loggerName;
  }
  
  /**
   * Checks if the current target logger is of the desired type.
   * If not it tries to replace it by one that is of the desired type.
   */
  protected void checkTargetLogger()
  {
    LoggerFactory loggerFactory;
    Logger logger;
    
    if (this.isDesiredLoggerTypeStillNotAvailable())
    {
      loggerFactory = LoggerFactoryProvider.getLoggerFactory(this.getDesiredLoggerType());
      if (this.isDesiredLoggerType(loggerFactory.getName()))
      {
        logger = loggerFactory.getLogger(this.getLoggerName());
        logger.setLogLevel(this.getOwnLogLevel());
        this.setTargetLogger(logger);
      }
    }
  }
  
  protected void initTargetLogger()
  {
    this.setTargetLogger(new PrintStreamLogger(this.getLoggerName()));
  }

  protected boolean isDesiredLoggerTypeStillNotAvailable()
  {
    return this.getTargetLogger() instanceof PrintStreamLogger;
  }

  protected boolean isDesiredLoggerType(String name)
  {
    return this.getDesiredLoggerType().equals(name);
  }

  protected Logger getTargetLogger()
  {
    return this.targetLogger;
  }
  
  protected void setTargetLogger(Logger targetLogger)
  {
    this.targetLogger = targetLogger;
  }
  
  protected String getDesiredLoggerType()
  {
    return this.desiredLoggerType;
  }
  
  protected String getOwnLogLevel()
  {
    return this.ownLogLevel;
  }
  
  protected void setOwnLogLevel(String ownLogLevel)
  {
    this.ownLogLevel = ownLogLevel;
  }
}
