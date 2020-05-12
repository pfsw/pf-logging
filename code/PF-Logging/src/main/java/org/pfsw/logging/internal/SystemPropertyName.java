// ===========================================================================
// CONTENT  : ENUM SystemPropertyName
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 12/05/2020
// HISTORY  :
//  12/05/2020  mdu  CREATED
//
// Copyright (c) 2020, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.internal;

/**
 * System property names that are influencing the logging component.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public enum SystemPropertyName
{
  //@formatter:off
  LOG_FILE("org.pfsw.logging.FILE"),
  LOG_LEVEL("org.pfsw.logging.LEVEL");
  //@formatter:on

  private final String propertyName;

  private SystemPropertyName(String propertyName)
  {
    this.propertyName = propertyName;
  }

  public String getPropertyName()
  {
    return this.propertyName;
  }

  public String asString()
  {
    return getPropertyName();
  }

  @Override
  public String toString()
  {
    return String.format("%s(%s, '%s')", getClass().getSimpleName(), name(), asString());
  }
}