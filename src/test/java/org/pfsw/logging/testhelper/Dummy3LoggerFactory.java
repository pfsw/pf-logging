// ===========================================================================
// CONTENT  : CLASS Dummy1LoggerFactory
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 13/12/2015
// HISTORY  :
//  13/12/2015  mdu  CREATED
//
// Copyright (c) 2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.testhelper;

import org.pfsw.logging.Logger;
import org.pfsw.logging.LoggerFactory;

public class Dummy3LoggerFactory implements LoggerFactory
{
  public static final String FACTORY_ID = "DUMMY3";

  public Dummy3LoggerFactory()
  {
    super();
  }

  @Override
  public String getName()
  {
    return FACTORY_ID;
  }

  @Override
  public Logger createLogger()
  {
    return null;
  }

  @Override
  public Logger getLogger(String loggerName)
  {
    return null;
  }

  @Override
  public Logger getLogger(Class<?> clazz)
  {
    return null;
  }
}
