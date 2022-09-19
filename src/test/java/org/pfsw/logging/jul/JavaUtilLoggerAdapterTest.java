// ===========================================================================
// CONTENT  : TEST CLASS JavaUtilLoggerAdapterTest
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 13/12/2015
// HISTORY  :
//  13/12/2015  mdu  CREATED
//
// Copyright (c) 2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.jul;

// ===========================================================================
// IMPORTS
// ===========================================================================
import static org.junit.Assert.*;

import java.util.logging.Level;

import org.junit.Test;
import org.pfsw.logging.LogLevel;
import org.pfsw.logging.Logger;
import org.pfsw.logging.LoggerBindingNames;
import org.pfsw.logging.LoggerFactory;
import org.pfsw.logging.LoggerFactoryProvider;

public class JavaUtilLoggerAdapterTest
{
  @Test
  public void test_getLogger()
  {
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory(LoggerBindingNames.JUL);
    Logger logger = factory.getLogger("dummy");
    assertTrue("Unexpected class: " + logger.getClass().getName(), logger instanceof JavaUtilLoggerAdapter);    
    assertEquals("dummy", logger.getName());    
  } 
  
  @Test
  public void test_setLogLevel_NONE()
  {
    JavaUtilLoggerAdapter logger = new JavaUtilLoggerAdapter("jul-unittest-none");
    assertTrue(logger.setLogLevel(Logger.LL_NONE));
    assertEquals(Level.OFF, logger.getJulLogger().getLevel());    
  } 

  @Test
  public void test_setLogLevel_DEBUG()
  {
    JavaUtilLoggerAdapter logger = new JavaUtilLoggerAdapter("jul-unittest-debug");
    assertTrue(logger.setLogLevel(Logger.LL_DEBUG));
    assertEquals(LogLevel.DEBUG.getJULLevel(), logger.getJulLogger().getLevel());    
  } 
  
  @Test
  public void test_setLogLevel_INFO()
  {
    JavaUtilLoggerAdapter logger = new JavaUtilLoggerAdapter("jul-unittest-info");
    assertTrue(logger.setLogLevel(Logger.LL_INFO));
    assertEquals(Level.INFO, logger.getJulLogger().getLevel());    
  } 
  
  @Test
  public void test_setLogLevel_WARNING()
  {
    JavaUtilLoggerAdapter logger = new JavaUtilLoggerAdapter("jul-unittest-warning");
    assertTrue(logger.setLogLevel(Logger.LL_WARNING));
    assertEquals(Level.WARNING, logger.getJulLogger().getLevel());    
  } 
  
  @Test
  public void test_setLogLevel_ERROR()
  {
    JavaUtilLoggerAdapter logger = new JavaUtilLoggerAdapter("jul-unittest-error");
    assertTrue(logger.setLogLevel(Logger.LL_ERROR));
    assertEquals(Level.SEVERE, logger.getJulLogger().getLevel());    
  } 
  
  @Test
  public void test_setLogLevel_UNKNOWN()
  {
    JavaUtilLoggerAdapter logger = new JavaUtilLoggerAdapter("jul-unittest-unknown");
    assertFalse(logger.setLogLevel("unknown"));
    assertNull(logger.getJulLogger().getLevel());    
  } 
  
  @Test
  public void test_setLogLevel_null()
  {
    JavaUtilLoggerAdapter logger = new JavaUtilLoggerAdapter("jul-unittest-null");
    assertFalse(logger.setLogLevel(null));
    assertNull(logger.getJulLogger().getLevel());    
  } 
}