// ===========================================================================
// CONTENT  : CLASS NilLoggerFactory
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 13/12/2015
// HISTORY  :
//  13/12/2015  mdu  CREATED
//
// Copyright (c) 2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.nil;

import org.pfsw.logging.Logger;
import org.pfsw.logging.LoggerBindingNames;
import org.pfsw.logging.LoggerFactory;

/**
 * This factory creates a single NilLogger instance.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class NilLoggerFactory implements LoggerFactory
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final NilLogger logger = new NilLogger();

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public NilLoggerFactory()
  {
    super();
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public String getName()
  {
    return LoggerBindingNames.NIL;
  }

  @Override
  public Logger createLogger()
  {
    return this.logger;
  }

  @Override
  public Logger getLogger(String loggerName)
  {
    return this.logger;
  }

  @Override
  public Logger getLogger(Class<?> clazz)
  {
    return this.logger;
  }
}
