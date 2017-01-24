// ===========================================================================
// CONTENT  : INTERFACE LoggerFactory
// AUTHOR   : Manfred Duchrow
// VERSION  : 2.0 - 13/12/2015
// HISTORY  :
//  21/06/2014  mdu  CREATED
//  13/12/2015  mdu   added -> getName(), getLogger(Class)
//
// Copyright (c) 2014-2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging ;

// ===========================================================================
// IMPORTS
// ===========================================================================

/**
 * The interface a logger factory must support in order to produce logger instances
 * that are compatible to the PF logging framework.
 *
 * @author Manfred Duchrow
 * @version 2.0
 */
public interface LoggerFactory
{ 
  /**
   * Returns the name of this logger factory. This name must consist of ASCII letters,
   * digits and underscore (_) only.
   * It is a short name that can be used in configurations to refer to the logging
   * framework binding that is provided by this factory.
   */
  public String getName();
  
  /**
   * Returns a new logger instance that is not named (empty string name) and therefore
   * not be retrieved again in any way by the factory.
   */
  public Logger createLogger();

  /**
   * Returns a logger instance corresponding to the given name.
   * If it does not exist yet, it will be created.
   * Otherwise the already existing logger will be returned.
   */
  public Logger getLogger(String loggerName);
  
  /**
   * Returns a logger instance corresponding to the full qualifies name of the given class.
   * If it does not exist yet, it will be created.
   * Otherwise the already existing logger will be returned.
   */
  public Logger getLogger(Class<?> clazz);
  
} // interface LoggerFactory