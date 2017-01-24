// ===========================================================================
// CONTENT  : TEST CLASS PrintStreamLoggerTest
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 20/12/2003
// HISTORY  :
//  20/12/2003  duma  CREATED
//
// Copyright (c) 2003, by Manfred Duchrow. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

// ===========================================================================
// IMPORTS
// ===========================================================================
import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;
import org.pfsw.logging.Logger;
import org.pfsw.logging.PrintStreamLogger;
import org.pfsw.logging.testhelper.InMemoryPrintStream;

/**
 * Test class for corresponding business class.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class PrintStreamLoggerTest
{
  @Test
  public void test_logLevel_1() throws Exception
  {
    Logger logger;

    logger = new PrintStreamLogger();
    assertTrue(!logger.isLoggingDebugs());
    assertTrue(!logger.isLoggingInfos());
    assertTrue(!logger.isLoggingWarnings());
    assertTrue(logger.isLoggingErrors());
  } 

  @Test
  public void test_logLevel_2() throws Exception
  {
    Logger logger;
    Properties props = new Properties();

    props.setProperty(PrintStreamLogger.PROP_LOG_LEVEL, "WARNING");
    logger = new PrintStreamLogger();
    logger.initialize(props);
    assertTrue(!logger.isLoggingDebugs());
    assertTrue(!logger.isLoggingInfos());
    assertTrue(logger.isLoggingWarnings());
    assertTrue(logger.isLoggingErrors());
  } 

  @Test
  public void test_logLevel_3() throws Exception
  {
    Logger logger;
    Properties props = new Properties();

    props.setProperty(PrintStreamLogger.PROP_LOG_LEVEL, "INFO");
    logger = new PrintStreamLogger();
    logger.initialize(props);
    assertTrue(!logger.isLoggingDebugs());
    assertTrue(logger.isLoggingInfos());
    assertTrue(logger.isLoggingWarnings());
    assertTrue(logger.isLoggingErrors());
  } 

  @Test
  public void test_logLevel_4() throws Exception
  {
    Logger logger;
    Properties props = new Properties();

    props.setProperty(PrintStreamLogger.PROP_LOG_LEVEL, "DEBUG");
    logger = new PrintStreamLogger();
    logger.initialize(props);
    assertTrue(logger.isLoggingDebugs());
    assertTrue(logger.isLoggingInfos());
    assertTrue(logger.isLoggingWarnings());
    assertTrue(logger.isLoggingErrors());
  } 

  @Test
  public void test_logLevel_5() throws Exception
  {
    Logger logger;
    Properties props = new Properties();

    props.setProperty(PrintStreamLogger.PROP_LOG_LEVEL, "NONE");
    logger = new PrintStreamLogger();
    logger.initialize(props);
    assertTrue(!logger.isLoggingDebugs());
    assertTrue(!logger.isLoggingInfos());
    assertTrue(!logger.isLoggingWarnings());
    assertTrue(!logger.isLoggingErrors());
  } 

  @Test
  public void test_setLogLevel_1() throws Exception
  {
    Logger logger;
    Properties props = new Properties();

    props.setProperty(PrintStreamLogger.PROP_LOG_LEVEL, "NONE");
    logger = new PrintStreamLogger();
    logger.initialize(props);
    assertTrue(!logger.isLoggingInfos());
    assertTrue(logger.setLogLevel("INFO"));
    assertTrue(logger.isLoggingInfos());
    assertTrue(!logger.setLogLevel("unknown"));
  } 

  @Test
  public void test_getName_default() throws Exception
  {
    Logger logger;
    
    logger = new PrintStreamLogger();
    assertEquals("", logger.getName());
  } 
  
  @Test
  public void test_getName_set_by_property() throws Exception
  {
    Logger logger;
    Properties props = new Properties();
    
    props.setProperty(PrintStreamLogger.PROP_LOGGER_NAME, "myLogger");
    logger = new PrintStreamLogger();
    logger.initialize(props);
    assertEquals("myLogger", logger.getName());
  } 
  
  @Test
  public void test_getName_set_by_constructor() throws Exception
  {
    Logger logger;

    logger = new PrintStreamLogger("best.logger.ever");
    assertEquals("best.logger.ever", logger.getName());
  } 
  
  @Test
  public void test_getName_override_by_property() throws Exception
  {
    Logger logger;
    Properties props = new Properties();
    
    logger = new PrintStreamLogger("special.logger");
    props.setProperty(PrintStreamLogger.PROP_LOGGER_NAME, "what.a.logger");
    logger.initialize(props);
    assertEquals("what.a.logger", logger.getName());
  } 
  
  @Test
  public void test_that_logger_name_gets_written() throws Exception
  {
    PrintStreamLogger logger;
    InMemoryPrintStream memoryPrintStream;
    
    memoryPrintStream = new InMemoryPrintStream();
    logger = new PrintStreamLogger("special.logger");
    logger.setPrintStream(memoryPrintStream.getPrintStream());
    logger.logError("What a mess!");
    assertEquals("E special.logger What a mess!\n", memoryPrintStream.getContent());
  } 
  
  @Test
  public void test_logError_with_placeholders() throws Exception
  {
    PrintStreamLogger logger;
    InMemoryPrintStream memoryPrintStream;
    
    memoryPrintStream = new InMemoryPrintStream();
    logger = new PrintStreamLogger();
    logger.setPrintStream(memoryPrintStream.getPrintStream());
    logger.logError("The value of {0} is {1}, which is greater than {2}!", "maxSize", 6, 5L);
    assertEquals("E The value of maxSize is 6, which is greater than 5!\n", memoryPrintStream.getContent());
  }  
} 
