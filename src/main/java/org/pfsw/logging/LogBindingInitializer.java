// ===========================================================================
// CONTENT  : INTERFACE LogBindingInitializer
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 03/02/2017
// HISTORY  :
//  03/02/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

/**
 * This interface is used to dynamically load the initial
 * log factory to be used for all PF libraries logging.
 * <br>
 * It will be detected via the Java ServiceLoader mechanism.
 * That is, implementation classes must be specified in<br>
 * <em>META-INF/services/org.pfsw.logging.LogBindingInitializer</em>
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public interface LogBindingInitializer
{
  /**
   * Returns the priority of this initializer.
   * If more than one initializer are found, the one with the highest 
   * priority will be used.  
   */
  public int getPriority();
  
  /**
   * Returns the name of the factory that should be used to adapt
   * PF logging to an external logging system.
   */
  public String getLoggerFactoryName();
}