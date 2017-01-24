package org.pfsw.logging;

import static org.junit.Assert.*;

import org.junit.Test;
import org.pfsw.logging.testhelper.UnitTestLogger;

public class Logger2LoggerTest
{
  @Test
  public void test_isInfoEnabled_false()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.WARN);
    Logger2 logger = new Logger2Logger(utLogger);
    
    assertFalse(logger.isInfoEnabled());
  }
  
  @Test
  public void test_isInfoEnabled_true()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.NONE);
    Logger2 logger = new Logger2Logger(utLogger);

    assertFalse(logger.isDebugEnabled());
    logger.setLogLevel(LogLevel.INFO);
    assertTrue(logger.isInfoEnabled());
    logger.setLogLevel(LogLevel.ERROR);
    assertFalse(logger.isInfoEnabled());
    logger.setLogLevel(LogLevel.DEBUG);
    assertTrue(logger.isInfoEnabled());
  }

  @Test
  public void test_debugf_disabled()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.INFO);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.debugf("[%s] param %s must not be null", "1", "alpha");
    assertNull(utLogger.getLogRecord());
    assertNull(utLogger.getThrowable());
  }

  @Test
  public void test_debugf()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.DEBUG);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.debugf("[%s] param %s must not be null", "1", "alpha");
    assertEquals("[1] param alpha must not be null", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.DEBUG, utLogger.getLogRecord().getLogLevel());
    assertNull(utLogger.getThrowable());
  }
  
  @Test
  public void test_infof()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.DEBUG);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.infof("int %s = %d", "a", 7);
    assertEquals("int a = 7", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.INFO, utLogger.getLogRecord().getLogLevel());
    assertNull(utLogger.getThrowable());
  }
  
  @Test
  public void test_warnf()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.WARN);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.warnf("param %s must not be null", "alpha");
    assertEquals("param alpha must not be null", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.WARN, utLogger.getLogRecord().getLogLevel());
    assertNull(utLogger.getThrowable());
  }
  
  @Test
  public void test_warnf_with_exception()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.INFO);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.warnf(new IllegalArgumentException(), "param %s must not be null", "alpha");
    assertEquals("param alpha must not be null", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.WARN, utLogger.getLogRecord().getLogLevel());
    assertTrue(utLogger.getThrowable() instanceof IllegalArgumentException);
  }
  
  @Test
  public void test_warnf_with_exception_disabled()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.ERROR);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.warnf(new NullPointerException(), "param %s must not be null", "alpha");
    assertNull(utLogger.getLogRecord());
    assertNull(utLogger.getThrowable());
  }
  
  @Test
  public void test_warnf_disabled()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.ERROR);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.warnf("param %s must not be null", "alpha");
    assertNull(utLogger.getLogRecord());
    assertNull(utLogger.getThrowable());
  }

  @Test
  public void test_errorf()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.WARN);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.errorf("param %s must not be null", "alpha");
    assertEquals("param alpha must not be null", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.ERROR, utLogger.getLogRecord().getLogLevel());
    assertNull(utLogger.getThrowable());
  }
  
  @Test
  public void test_errorf_with_exception()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.INFO);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.errorf(new IllegalArgumentException(), "param %s must not be null", "alpha");
    assertEquals("param alpha must not be null", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.ERROR, utLogger.getLogRecord().getLogLevel());
    assertTrue(utLogger.getThrowable() instanceof IllegalArgumentException);
  }
  
  @Test
  public void test_isEnabled()
  {
    UnitTestLogger utLogger = new UnitTestLogger();
    Logger2 logger = new Logger2Logger(utLogger);
    
    assertFalse(logger.isEnabled(null));
    assertFalse(logger.isEnabled(LogLevel.NONE));

    assertFalse(logger.isEnabled(LogLevel.DEBUG));
    assertFalse(logger.isEnabled(LogLevel.INFO));
    assertFalse(logger.isEnabled(LogLevel.WARN));
    assertFalse(logger.isEnabled(LogLevel.ERROR));

    logger.setLogLevel(LogLevel.ERROR);
    assertFalse(logger.isEnabled(LogLevel.DEBUG));
    assertFalse(logger.isEnabled(LogLevel.INFO));
    assertFalse(logger.isEnabled(LogLevel.WARN));
    assertTrue(logger.isEnabled(LogLevel.ERROR));
    
    logger.setLogLevel(LogLevel.WARN);
    assertFalse(logger.isEnabled(LogLevel.DEBUG));
    assertFalse(logger.isEnabled(LogLevel.INFO));
    assertTrue(logger.isEnabled(LogLevel.WARN));
    assertTrue(logger.isEnabled(LogLevel.ERROR));
    
    logger.setLogLevel(LogLevel.INFO);
    assertFalse(logger.isEnabled(LogLevel.DEBUG));
    assertTrue(logger.isEnabled(LogLevel.INFO));
    assertTrue(logger.isEnabled(LogLevel.WARN));
    assertTrue(logger.isEnabled(LogLevel.ERROR));
    
    logger.setLogLevel(LogLevel.DEBUG);
    assertTrue(logger.isEnabled(LogLevel.DEBUG));
    assertTrue(logger.isEnabled(LogLevel.INFO));
    assertTrue(logger.isEnabled(LogLevel.WARN));
    assertTrue(logger.isEnabled(LogLevel.ERROR));
  }

  @Test
  public void test_logf_ERROR()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.WARN);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.logf(LogLevel.ERROR, "param %s < %d", "alpha", 100);
    assertEquals("param alpha < 100", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.ERROR, utLogger.getLogRecord().getLogLevel());
    assertNull(utLogger.getThrowable());
  }
  
  @Test
  public void test_logf_WARN()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.WARN);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.logf(LogLevel.WARN, "param %s < %d", "alpha", 100);
    assertEquals("param alpha < 100", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.WARN, utLogger.getLogRecord().getLogLevel());
    assertNull(utLogger.getThrowable());
  }
  
  @Test
  public void test_logf_INFO()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.INFO);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.logf(LogLevel.INFO, "param %s < %d", "alpha", 100);
    assertEquals("param alpha < 100", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.INFO, utLogger.getLogRecord().getLogLevel());
    assertNull(utLogger.getThrowable());
  }

  @Test
  public void test_logf_DEBUG()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.DEBUG);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.logf(LogLevel.DEBUG, "param %s < %d", "alpha", 100);
    assertEquals("param alpha < 100", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.DEBUG, utLogger.getLogRecord().getLogLevel());
    assertNull(utLogger.getThrowable());
  }
  
  @Test
  public void test_logf_null()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.DEBUG);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.logf(null, "param %s < %d", "alpha", 100);
    assertNull(utLogger.getLogRecord());
    assertNull(utLogger.getThrowable());
  }

  @Test
  public void test_logf_with_exception_INFO()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.INFO);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.logf(LogLevel.INFO, new IllegalStateException(), "param %s < %d", "alpha", 100);
    assertEquals("param alpha < 100", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.INFO, utLogger.getLogRecord().getLogLevel());
    assertTrue(utLogger.getThrowable() instanceof IllegalStateException);
  }
  
  @Test
  public void test_logf_with_exception_DEBUG()
  {
    UnitTestLogger utLogger = new UnitTestLogger(LogLevel.DEBUG);
    Logger2 logger = new Logger2Logger(utLogger);
    
    logger.logf(LogLevel.DEBUG, new IllegalStateException(), "param %s < %d", "alpha", 100);
    assertEquals("param alpha < 100", utLogger.getLogRecord().getMessage());
    assertEquals(LogLevel.DEBUG, utLogger.getLogRecord().getLogLevel());
    assertTrue(utLogger.getThrowable() instanceof IllegalStateException);
  }

}
