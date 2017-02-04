// ===========================================================================
// CONTENT  : TEST CLASS LoggerFactoryProviderTest
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 22/06/2014
// HISTORY  :
//  22/06/2014  mdu  CREATED
//
// Copyright (c) 2014, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

// ===========================================================================
// IMPORTS
// ===========================================================================
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pfsw.logging.deferred.DeferredInitializationLoggerFactory;
import org.pfsw.logging.jul.JavaUtilLoggerFactory;
import org.pfsw.logging.nil.NilLoggerFactory;
import org.pfsw.logging.stdout.PrintStreamLoggerFactory;
import org.pfsw.logging.testhelper.Dummy1LoggerFactory;
import org.pfsw.logging.testhelper.Dummy2LoggerFactory;
import org.pfsw.logging.testhelper.Dummy3LoggerFactory;

public class LoggerFactoryProviderTest
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================
  private static final String DEFAULT_LOGGER_FACTORY_CLASS = System.getProperty("org.pf.logging.Expected.Default.Class", PrintStreamLoggerFactory.class.getName());

  // =========================================================================
  // TEST METHODS
  // =========================================================================
  @Test
  public void test_getLoggerFactory_default()
  {
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory();
    assertEquals(DEFAULT_LOGGER_FACTORY_CLASS, factory.getClass().getName());
  }  

  @Test
  public void test_getLoggerFactory_changed_default()
  {
    LoggerFactoryProvider.setDefaultFactoryName(LoggerBindingNames.NIL);
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory();
    assertEquals(NilLoggerFactory.class.getName(), factory.getClass().getName());
  }  
  
  @Test
  public void test_getLoggerFactory_JUL()
  {
    LoggerFactoryProvider.setDefaultFactoryName(LoggerBindingNames.JUL);
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory();
    assertTrue(factory instanceof JavaUtilLoggerFactory);
  }  

  @Test
  public void test_getLoggerFactory_default_property_binding()
  {
    LoggerFactory factory;
    
    factory = LoggerFactoryProvider.getLoggerFactory();
    assertEquals(LoggerBindingNames.STDOUT, factory.getName());

    System.setProperty(LoggerBindingNames.PROP_BINDING_NAME, "DUMMY2");
    LoggerFactoryProvider.reset();
    
    factory = LoggerFactoryProvider.getLoggerFactory();
    assertEquals("DUMMY2", factory.getName());
    
    System.clearProperty(LoggerBindingNames.PROP_BINDING_NAME);
    LoggerFactoryProvider.reset();
    factory = LoggerFactoryProvider.getLoggerFactory();
    assertEquals(LoggerBindingNames.STDOUT, factory.getName());
  }
  
  @Test
  public void test_getLoggerFactory_dynamically_registered()
  {
    LoggerFactory factory;
    
    factory = LoggerFactoryProvider.getLoggerFactory("DUMMY1");
    assertEquals(Dummy1LoggerFactory.class.getName(), factory.getClass().getName());
    factory = LoggerFactoryProvider.getLoggerFactory("DUMMY2");
    assertEquals(Dummy2LoggerFactory.class.getName(), factory.getClass().getName());
    factory = LoggerFactoryProvider.getLoggerFactory("DUMMY99");
    assertEquals(DeferredInitializationLoggerFactory.class.getName(), factory.getClass().getName());
  }  

  @Test
  public void test_getLoggerFactory_additionally_registered()
  {
    LoggerFactory factory;
    
    LoggerFactoryProvider.register(new Dummy3LoggerFactory());
    factory = LoggerFactoryProvider.getLoggerFactory("DUMMY3");
    assertEquals(Dummy3LoggerFactory.class.getName(), factory.getClass().getName());
  }  

  @Test
  public void test_getLoggerFactory()
  {
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory(LoggerBindingNames.JUL);
    assertTrue("Unexpected class: " + factory.getClass().getName(), factory instanceof JavaUtilLoggerFactory);    
  } 
  
  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  @Before
  public void setUp() throws Exception
  {
    LoggerFactoryProvider.setDefaultFactoryName(null);
    LoggerFactoryProvider.reset();
  } 
} 
