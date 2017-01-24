// ===========================================================================
// CONTENT  : CLASS JavaUtilLoggerFactory
// AUTHOR   : Manfred Duchrow
// VERSION  : 2.0 - 13/12/2015
// HISTORY  :
//  22/06/2014  mdu  CREATED
//  13/12/2015  mdu   added -> getName(), getLogger(Class)
//
// Copyright (c) 2014-2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.jul;

import org.pfsw.logging.Logger;
import org.pfsw.logging.LoggerBindingNames;
// ===========================================================================
// IMPORTS
// ===========================================================================
import org.pfsw.logging.LoggerFactory;

/**
 * The logger factory that creates logger instances that adapt logging
 * to the JDK's java.util.logging (JUL) framework.
 *
 * @author Manfred Duchrow
 * @version 2.0
 */
public class JavaUtilLoggerFactory implements LoggerFactory
{
  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public JavaUtilLoggerFactory()
  {
    super();
  } // JavaUtilLoggerFactory()
  
  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================  
  @Override
  public String getName()
  {
    return LoggerBindingNames.JUL;
  }
  
  @Override
  public Logger createLogger()
  {
    return new JavaUtilLoggerAdapter();
  }
  
  @Override
  public Logger getLogger(String loggerName)
  {
    return new JavaUtilLoggerAdapter(loggerName);
  }

  @Override
  public Logger getLogger(Class<?> clazz)
  {
    return this.getLogger(clazz.getName());
  }
}
