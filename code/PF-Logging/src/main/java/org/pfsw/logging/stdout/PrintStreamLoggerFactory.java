// ===========================================================================
// CONTENT  : CLASS PrintStreamLoggerFactory
// AUTHOR   : Manfred Duchrow
// VERSION  : 2.0 - 13/12/2015
// HISTORY  :
//  21/06/2014  mdu  CREATED
//  13/12/2015  mdu   added ->  name with getter, getLogger(Class)
//
// Copyright (c) 2014-2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

// ===========================================================================
// IMPORTS
// ===========================================================================
import org.pfsw.logging.Logger;
import org.pfsw.logging.LoggerFactory;

/**
 * This factory returns logger instances of class {@link PrintStreamLogger}.
 *
 * @author Manfred Duchrow
 * @version 2.0
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
    return new PrintStreamLogger();
  }

  @Override
  public Logger getLogger(String loggerName)
  {
    return new PrintStreamLogger(loggerName);
  }

  @Override
  public Logger getLogger(Class<?> clazz)
  {
    return this.getLogger(clazz.getName());
  }

}
