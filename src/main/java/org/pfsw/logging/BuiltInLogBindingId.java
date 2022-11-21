// ===========================================================================
// CONTENT  : CLASS BuiltInLogBindingId
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 21/11/2022
// HISTORY  :
//  21/11/2022  mdu  CREATED
//
// Copyright (c) 2022, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

public enum BuiltInLogBindingId implements LogBindingId
{
  //@formatter:off
  /**
   * The LoggerFactory with this name binds to an implementation that absorbs all log messages. 
   */
  NIL("NIL"),

  /**
   * The LoggerFactory with this name binds to an implementation that prints all log messages to stdout. 
   */
  STDOUT("STDOUT"),

  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to JDK's built-in java.util.logging (JUL). 
   */
  JUL("JUL")

  ;
  //@formatter:on

  /**
   * Returns if the given log binding name is one of the built-in names (case-insensitive).
   */
  public static boolean isBuiltInLogBinding(String logBindingName)
  {
    for (BuiltInLogBindingId builtInLogBindingId : values())
    {
      if (builtInLogBindingId.asString().equalsIgnoreCase(logBindingName))
      {
        return true;
      }
    }
    return false;
  }
  
  private final String bindingName;

  BuiltInLogBindingId(String bindingName)
  {
    this.bindingName = bindingName;
  }

  /**
   * Returns the string representation of this log binding.
   */
  @Override
  public String asString()
  {
    return getBindingName();
  }

  @Override
  public String toString()
  {
    return String.format("%s(%s, '%s')", getClass().getSimpleName(), name(), asString());
  }

  private String getBindingName()
  {
    return this.bindingName;
  }
}
