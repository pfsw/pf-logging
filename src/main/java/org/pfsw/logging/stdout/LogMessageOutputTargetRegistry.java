// ===========================================================================
// CONTENT  : CLASS LogMessageOutputTargetRegistry
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 05/04/2021
// HISTORY  :
//  05/04/2021  mdu  CREATED
//
// Copyright (c) 2021, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

import java.util.HashMap;
import java.util.Map;

import org.pfsw.logging.Logger;

public class LogMessageOutputTargetRegistry
{
  // key = logger name, value = output target
  private static final Map<String, LogMessageOutputTarget> REGISTRY = new HashMap<String, LogMessageOutputTarget>();
  private static final String SEPARATOR = ".";

  /**
   * The default out put target that will be used whenever no specific output target is registered for a given logger name.
   */
  public static LogMessageOutputTarget DEFAULT_OUTPUT_TARGET = LogMessageOutputPrintStream.create();
  
  /**
   * The name of the root logger (parent of all loggers).
   */
  public static final String ROOT_LOGGER_NAME = Logger.ANONYMOUS_LOGGER_NAME;

  /**
   * Removes all registered output targets. 
   */
  public static void clear()
  {
    REGISTRY.clear();
  }
  
  /**
   * Register a specific output target fore the given logger name.
   */
  public static void registerOutputTarget(String loggerName, LogMessageOutputTarget outputTarget)
  {
    if ((loggerName != null) && (outputTarget != null))
    {
      REGISTRY.put(loggerName, outputTarget);
    }
  }

  /**
   * Returns the output target registered for the given logger name or its parent logger name.
   * If no registered output target can be found the default output target will be returned.
   */
  public static LogMessageOutputTarget getOutputTarget(String loggerName)
  {
    LogMessageOutputTarget outputTarget;
    int index;
    String parentLoggerName;

    if (loggerName == null)
    {
      return DEFAULT_OUTPUT_TARGET;
    }
    outputTarget = REGISTRY.get(loggerName);
    if (outputTarget != null)
    {
      return outputTarget;
    }
    index = loggerName.lastIndexOf(SEPARATOR);
    if (index > 0)
    {
      parentLoggerName = loggerName.substring(0, index);
      return getOutputTarget(parentLoggerName);
    }
    if (!ROOT_LOGGER_NAME.equals(loggerName))
    {
      return getOutputTarget(ROOT_LOGGER_NAME);      
    }
    return DEFAULT_OUTPUT_TARGET;
  }
}
