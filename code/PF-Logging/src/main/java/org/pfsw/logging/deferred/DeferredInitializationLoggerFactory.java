// ===========================================================================
// CONTENT  : CLASS DeferredInitializationLoggerFactory
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 04/02/2017
// HISTORY  :
//  04/02/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.deferred;

import org.pfsw.logging.Logger;
// ===========================================================================
// IMPORTS
// ===========================================================================
import org.pfsw.logging.LoggerFactory;

/**
 * This factory is for internal use only. It is not in the logger factory 
 * registry.
 * <p>
 * Its purpose is to create surrogate logger instances in cases where the 
 * desired logger adapter type cannot be found yet in the logger factory 
 * registry.
 * <br>
 * The loggers created by this factory are able to switch to the desired logger
 * types ass soon as the corresponding logger factory gets registered.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class DeferredInitializationLoggerFactory implements LoggerFactory
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final String desiredLoggerType;

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public DeferredInitializationLoggerFactory(String desiredLoggerType)
  {
    super();
    this.desiredLoggerType = desiredLoggerType;
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public String getName()
  {
    return "INTERNAL-DO-NOT-REFER-TO";
  }

  @Override
  public Logger createLogger()
  {
    return new DeferredInitializationLoggerAdapter(this.getDesiredLoggerType());
  }

  @Override
  public Logger getLogger(Class<?> clazz)
  {
    return new DeferredInitializationLoggerAdapter(this.getDesiredLoggerType(), clazz);
  }

  @Override
  public Logger getLogger(String loggerName)
  {
    return new DeferredInitializationLoggerAdapter(this.getDesiredLoggerType(), loggerName);
  }

  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected String getDesiredLoggerType()
  {
    return this.desiredLoggerType;
  }
}
