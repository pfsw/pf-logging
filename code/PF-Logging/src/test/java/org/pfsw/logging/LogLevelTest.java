package org.pfsw.logging;

import static org.junit.Assert.*;

import java.util.logging.Level;

import org.junit.Test;

public class LogLevelTest
{
  @Test
  public void test_getPFLevel_null()
  {
    assertNull(LogLevel.getByPFLevel(null));
  }

  @Test
  public void test_getPFLevel_unknown()
  {
    assertNull(LogLevel.getByPFLevel("unknown"));
  }
  
  @Test
  public void test_getPFLevel_case_insensitive_match()
  {
    assertSame(LogLevel.ERROR, LogLevel.getByPFLevel("error"));
  }
  
  @Test
  public void test_getPFLevel_matches()
  {
    assertSame(LogLevel.NONE, LogLevel.getByPFLevel(Logger.LL_NONE));
    assertSame(LogLevel.DEBUG, LogLevel.getByPFLevel(Logger.LL_DEBUG));
    assertSame(LogLevel.INFO, LogLevel.getByPFLevel(Logger.LL_INFO));
    assertSame(LogLevel.WARN, LogLevel.getByPFLevel(Logger.LL_WARNING));
    assertSame(LogLevel.ERROR, LogLevel.getByPFLevel(Logger.LL_ERROR));
  }

  @Test
  public void test_getJULLevel_null()
  {
    assertNull(LogLevel.getByJULLevel(null));
  }
  
  @Test
  public void test_getJULLevel_unknown()
  {
    assertNull(LogLevel.getByJULLevel(Level.FINEST));
  }
  
  @Test
  public void test_getJULLevel_matches()
  {
    assertSame(LogLevel.NONE, LogLevel.getByJULLevel(Level.OFF));
    assertSame(LogLevel.DEBUG, LogLevel.getByJULLevel(Level.FINE));
    assertSame(LogLevel.INFO, LogLevel.getByJULLevel(Level.INFO));
    assertSame(LogLevel.WARN, LogLevel.getByJULLevel(Level.WARNING));
    assertSame(LogLevel.ERROR, LogLevel.getByJULLevel(Level.SEVERE));
  }
}