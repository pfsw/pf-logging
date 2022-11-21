// ===========================================================================
// CONTENT  : CLASS LoggerFactoryRegistry
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 13/12/2015
// HISTORY  :
//  13/12/2015  mdu  CREATED
//
// Copyright (c) 2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
  // INSTANCE VARIABLES
  // =========================================================================
  private final Map<String, LoggerFactory> factoriesMap = new HashMap<String, LoggerFactory>();

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public LoggerFactoryRegistry()
  {
    super();
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  public void clear() 
  {
    getFactoriesMap().clear();
  }
  
  public void register(LoggerFactory loggerFactory)
  {
    if (loggerFactory != null)
    {
      getFactoriesMap().put(loggerFactory.getName(), loggerFactory);
    }
  }

  public LoggerFactory getLoggerFactory(String name)
  {
    if (name == null)
    {
      return null;
    }
    return getFactoriesMap().get(name);
  }

  /**
   * Returns all registered factories that are not built-in (i.e. part of this library).
   */
  public List<LoggerFactory> getNotBuiltInFactories()
  {
    List<LoggerFactory> factories = new ArrayList<LoggerFactory>();

    for (LoggerFactory factory : getFactoriesMap().values())
    {
      if (!BuiltInLogBindingId.isBuiltInLogBinding(factory.getName()))
      {
        factories.add(factory);
      }
    }
    return factories;
  }

  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected Map<String, LoggerFactory> getFactoriesMap()
  {
    return this.factoriesMap;
  }
}
