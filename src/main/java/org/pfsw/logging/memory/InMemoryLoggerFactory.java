// ===========================================================================
// CONTENT  : CLASS InMemoryLoggerFactory
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 04/02/2017
// HISTORY  :
//  04/02/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.memory;

import org.pfsw.logging.Logger;
import org.pfsw.logging.LoggerFactory;

/**
 * This factory produces logger instances that keep all log output in memory. 
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class InMemoryLoggerFactory implements LoggerFactory
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================
  public static final String LOGGER_TYPE = "IN-MEM";

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public InMemoryLoggerFactory()
  {
    super();
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public String getName()
  {
    return LOGGER_TYPE;
  }

  @Override
  public Logger createLogger()
  {
    return getLogger(Logger.ANONYMOUS_LOGGER_NAME);
  }

  @Override
  public Logger getLogger(Class<?> clazz)
  {
    return getLogger(clazz.getName());
  }

  @Override
  public Logger getLogger(String loggerName)
  {
    return new InMemoryLogger(loggerName);
  }
}
