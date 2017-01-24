// ===========================================================================
// CONTENT  : CLASS LoggerFactoryProvider
// AUTHOR   : Manfred Duchrow
// VERSION  : 2.0 - 21/06/2014
// HISTORY  :
//  21/06/2014  mdu  CREATED
//  13/12/2015  mdu   changed -> using registry
//
// Copyright (c) 2014-2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

// ===========================================================================
// IMPORTS
// ===========================================================================
import java.util.ServiceLoader;

import org.pfsw.logging.jul.JavaUtilLoggerFactory;

/**
 * This class provides static access to the configured LoggerFactory which
 * then can be used to create logger instances.
 * <p>
 * The name of the default factory binding can be set via the system property "org.pfsw.logging.binding".
 * If this property is not specified, the default factory will be "STDOUT".
 *
 * @author Manfred Duchrow
 * @version 2.0
 */
public class LoggerFactoryProvider
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================
  private static final LoggerFactoryRegistry REGISTRY = new LoggerFactoryRegistry();
  private static final String DEFAULT_FACTORY_NAME = LoggerBindingNames.STDOUT;

  // =========================================================================
  // CLASS VARIABLES
  // =========================================================================
  private static String defaultFactoryName = DEFAULT_FACTORY_NAME;

  // =========================================================================
  // CLASS METHODS
  // =========================================================================
  static
  {
    loadLoggerFactoryRegistry();
    reset();
  }

  // -------------------------------------------------------------------------

  /**
   * Returns the default logger factory (i.e. the logger factory corresponding 
   * to the default name). 
   */
  public static LoggerFactory getLoggerFactory()
  {
    return getRegistry().getLoggerFactory(getDefaultFactoryName());
  }

  /**
   * Returns the logger factory with the given name or null if it cannot be found.
   */
  public static LoggerFactory getLoggerFactory(String name)
  {
    return getRegistry().getLoggerFactory(name);
  }

  /**
   * Resets the default logger factory name to the value of system property
   * "org.pfsw.logging.binding" or to "STDOUT". 
   */
  public static void reset()
  {
    String name;

    name = System.getProperty(LoggerBindingNames.PROP_BINDING_NAME);
    setDefaultFactoryName(name);
  }

  public static String getDefaultFactoryName()
  {
    return defaultFactoryName;
  }

  public static void setDefaultFactoryName(String name)
  {
    if (name == null)
    {
      LoggerFactoryProvider.defaultFactoryName = DEFAULT_FACTORY_NAME;
    }
    else
    {
      LoggerFactoryProvider.defaultFactoryName = name;
    }
  }

  /**
   * Registers the given factory under its name (see {@link LoggerFactory#getName()}).
   */
  public static void register(LoggerFactory factory)
  {
    getRegistry().register(factory);
  }

  // -------------------------------------------------------------------------

  private static void loadLoggerFactoryRegistry()
  {
    registerDefaultLoggerFactories();
    registerDynamicLoggerFactories();
  }

  private static void registerDefaultLoggerFactories()
  {
    register(new NilLoggerFactory());
    register(new PrintStreamLoggerFactory(LoggerBindingNames.STDOUT));
    register(new JavaUtilLoggerFactory());
  }

  private static void registerDynamicLoggerFactories()
  {
    ServiceLoader<LoggerFactory> serviceLoader;

    serviceLoader = ServiceLoader.load(LoggerFactory.class);
    for (LoggerFactory loggerFactory : serviceLoader)
    {
      register(loggerFactory);
    }
  }

  private static LoggerFactoryRegistry getRegistry()
  {
    return REGISTRY;
  }

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  private LoggerFactoryProvider()
  {
    super();
  } // LoggerFactoryProvider() 

} // class LoggerFactoryProvider 
