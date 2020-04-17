// ===========================================================================
// CONTENT  : CLASS LoggerFactoryProvider
// AUTHOR   : Manfred Duchrow
// VERSION  : 2.2 - 04/03/2017
// HISTORY  :
//  21/06/2014  mdu  CREATED
//  13/12/2015  mdu   changed -> using registry
//  03/02/2017  mdu   added   -> initialization mechanism via LogBindingInitializer
//  04/03/2017  mdu   added   -> getLogger(), getLogger2()
//
// Copyright (c) 2014-2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

import java.util.ServiceLoader;

import org.pfsw.logging.deferred.DeferredInitializationLoggerFactory;
import org.pfsw.logging.jul.JavaUtilLoggerFactory;
import org.pfsw.logging.nil.NilLoggerFactory;
import org.pfsw.logging.stdout.PrintStreamLoggerFactory;

/**
 * This class provides static access to the configured LoggerFactory which
 * then can be used to create logger instances.
 * <p>
 * The name of the default factory binding can be set via the system property "org.pfsw.logging.binding".
 * If this property is not specified, the default factory will be "STDOUT".
 *
 * @author Manfred Duchrow
 * @version 2.2
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
   * Returns a logger instance corresponding to the full qualifies name of the given class.
   * If it does not exist yet, it will be created.
   * Otherwise the already existing logger will be returned.
   * <p>
   * This will use the LoggerFactory provided by {@link #getLoggerFactory()}.
   */
  public static Logger getLogger(Class<?> clazz)
  {
    return getLoggerFactory().getLogger(clazz);
  }

  /**
   * Returns a logger instance corresponding to the given name.
   * If it does not exist yet, it will be created.
   * Otherwise the already existing logger will be returned.
   * <p>
   * This will use the LoggerFactory provided by {@link #getLoggerFactory()}.
   */
  public static Logger getLogger(String loggerName)
  {
    return getLoggerFactory().getLogger(loggerName);
  }
  
  /**
   * Returns a logger instance corresponding to the full qualifies name of the given class.
   * If it does not exist yet, it will be created.
   * Otherwise the already existing logger will be returned.
   * <p>
   * This will use the LoggerFactory provided by {@link #getLoggerFactory()}.
   */
  public static Logger2 getLogger2(Class<?> clazz)
  {
    return new Logger2Logger(getLogger(clazz));
  }
  
  /**
   * Returns a logger instance corresponding to the given name.
   * If it does not exist yet, it will be created.
   * Otherwise the already existing logger will be returned.
   * <p>
   * This will use the LoggerFactory provided by {@link #getLoggerFactory()}.
   */
  public static Logger2 getLogger2(String loggerName)
  {
    return new Logger2Logger(getLogger(loggerName));
  }
  
  /**
   * Returns the default logger factory (i.e. the logger factory corresponding 
   * to the default name). 
   */
  public static LoggerFactory getLoggerFactory()
  {
    return getLoggerFactory(getDefaultFactoryName());
  }

  /**
   * Returns the logger factory with the given name or, if it cannot be found,
   * a special factory that produces logger that do all logging to 
   * stdout and try to load the desired factory later, as soon as
   * it is available.
   * <br>
   * So this method never returns null.
   * 
   * @param name The unique name of the logger factory type (must not be null).
   * @throws IllegalArgumentException If the given name is null.
   */
  public static LoggerFactory getLoggerFactory(String name)
  {
    LoggerFactory factory;
    
    if (name == null)
    {
      throw new IllegalArgumentException("The name for a logger factory must not be null!");
    }
    factory = getRegistry().getLoggerFactory(name);
    if (factory == null)
    {
      factory = new DeferredInitializationLoggerFactory(name);
    }
    return factory;
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
      foundInstances = ServiceLoader.load(LoggerFactory.class, classLoader);
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
