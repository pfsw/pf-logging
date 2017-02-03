// ===========================================================================
// CONTENT  : CLASS LoggerFactoryProvider
// AUTHOR   : Manfred Duchrow
// VERSION  : 2.1 - 03/02/2017
// HISTORY  :
//  21/06/2014  mdu  CREATED
//  13/12/2015  mdu   changed -> using registry
//  03/02/2017  mdu   added   -> initialization mechanism via LogBindingInitializer
//
// Copyright (c) 2014-2017, by MDCS. All rights reserved.
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
 * @version 2.1
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
    LogBindingInitializer initializer;

    name = System.getProperty(LoggerBindingNames.PROP_BINDING_NAME);
    if (name == null)
    {
      initializer = lookupInitializer();
      if (initializer != null)
      {
        name = initializer.getLoggerFactoryName().trim();
      }
    }
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
    ServiceLoader<LoggerFactory> foundInstances;
    ClassLoader classLoader;
    
    classLoader = getClassLoader();
    if (classLoader != null)
    {      
      foundInstances = ServiceLoader.load(LoggerFactory.class);
      for (LoggerFactory loggerFactory : foundInstances)
      {
        register(loggerFactory);
      }
    }
  }

  private static LogBindingInitializer lookupInitializer()
  {
    LogBindingInitializer initializer = null;
    ServiceLoader<LogBindingInitializer> foundInstances;
    int prio = Integer.MIN_VALUE;
    ClassLoader classLoader;
    
    classLoader = getClassLoader();
    if (classLoader != null)
    {
      foundInstances = ServiceLoader.load(LogBindingInitializer.class, classLoader);
      for (LogBindingInitializer bindingInitializer : foundInstances)
      {
        // Pick the one with the highest priority
        if ((bindingInitializer.getPriority() > prio) && notBlank(bindingInitializer.getLoggerFactoryName()))
        {
          initializer = bindingInitializer;
          prio = bindingInitializer.getPriority();
        }
      }
    }
    return initializer;
  }
  
  /**
   * Returns the first non-null classloader from the following order:
   * <ol>
   *   <li>The current thread's context class loader</li>
   *   <li>the class loader of this class</li>
   *   <li>the system class loader</li>
   * </ol>
   * or null if no classloader can be found.
   */
  private static ClassLoader getClassLoader()
  {
    try
    {
      return getFirstNonNull(Thread.currentThread().getContextClassLoader(), LoggerFactoryProvider.class.getClassLoader(), ClassLoader.getSystemClassLoader());
    }
    catch (RuntimeException ex)
    {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * Returns the first element of the given array that is not null or 
   * throws an ObjectNotFoundException if no such element can be found.
   * 
   * @param array The array from which to detect the non-null element.
   * @return The first non null element.
   * @throws NullPointerException If no non-null object has been found in the array or the array was null.
   */
  private static <T> T getFirstNonNull(T... array)
  {
    if (array != null)
    {
      for (T element : array)
      {
        if (element != null)
        {
          return element;
        }
      }
    }
    throw new NullPointerException("No non-null object found in the given array.");
  } 

  private static boolean notBlank(String string) 
  {
    return (string != null) && (string.trim().length() > 1);
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
  } 

} 
