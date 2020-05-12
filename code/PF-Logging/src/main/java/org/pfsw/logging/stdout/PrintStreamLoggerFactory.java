// ===========================================================================
// CONTENT  : CLASS PrintStreamLoggerFactory
// AUTHOR   : Manfred Duchrow
// VERSION  : 3.0 - 12/05/2020
// HISTORY  :
//  21/06/2014  mdu  CREATED
//  13/12/2015  mdu   added ->  name with getter, getLogger(Class)
//  12/05/2020  mdu   added ->  initLogger(), getLoggerSettings()
//
// Copyright (c) 2014-2020, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

import static org.pfsw.logging.internal.SystemPropertyName.*;
import java.util.Properties;

import org.pfsw.logging.Logger;
import org.pfsw.logging.LoggerFactory;

/**
 * This factory returns logger instances of class {@link PrintStreamLogger}.
 *
 * @author Manfred Duchrow
 * @version 3.0
 */
public class PrintStreamLoggerFactory implements LoggerFactory
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final String name;

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public PrintStreamLoggerFactory(String name)
  {
    super();
    this.name = name;
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public String getName()
  {
    return this.name;
  }

  @Override
  public Logger createLogger()
  {
    return initLogger(new PrintStreamLogger());
  }

  @Override
  public Logger getLogger(String loggerName)
  {
    return initLogger(new PrintStreamLogger(loggerName));
  }

  @Override
  public Logger getLogger(Class<?> clazz)
  {
    return getLogger(clazz.getName());
  }

  protected Logger initLogger(Logger logger)
  {
    logger.initialize(getLoggerSettings());
    return logger;
  }

  protected Properties getLoggerSettings()
  {
    Properties properties = new Properties();
    String value;
    
    value = System.getProperty(LOG_LEVEL.asString());
    if (value != null)
    {
      properties.setProperty(PrintStreamLogger.PROP_LOG_LEVEL, value.toUpperCase());
    }
    value = System.getProperty(LOG_FILE.asString());
    if (value != null)
    {
      properties.setProperty(PrintStreamLogger.PROP_OUTPUT_FILE, value.trim());
    }
    return properties;
  }
}
