// ===========================================================================
// CONTENT  : CLASS LoggerFactoryRegistry
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 13/12/2015
// HISTORY  :
//  13/12/2015  mdu  CREATED
//
// Copyright (c) 2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging ;

// ===========================================================================
// IMPORTS
// ===========================================================================
import java.util.HashMap;
import java.util.Map;

/**
 * A registry for named logger factories. Allows to find factory instances by 
 * symbolic name rather than class name.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
class LoggerFactoryRegistry
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================

  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private final Map<String, LoggerFactory> factoriesMap = new HashMap<String, LoggerFactory>();

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public LoggerFactoryRegistry()
  {
    super() ;
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  public void register(LoggerFactory loggerFactory) 
  {
    if (loggerFactory != null)
    {
      this.getFactoriesMap().put(loggerFactory.getName(), loggerFactory);
    }
  }
  
  public LoggerFactory getLoggerFactory(String name) 
  {
    if (name == null)
    {
      return null;
    }
    return this.getFactoriesMap().get(name);
  }
  
  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected Map<String, LoggerFactory> getFactoriesMap()
  {
    return this.factoriesMap;
  }
}
