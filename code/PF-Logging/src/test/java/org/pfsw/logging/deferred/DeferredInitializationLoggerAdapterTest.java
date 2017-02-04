package org.pfsw.logging.deferred;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.pfsw.logging.LogLevel;
import org.pfsw.logging.Logger;
import org.pfsw.logging.LoggerFactory;
import org.pfsw.logging.LoggerFactoryProvider;
import org.pfsw.logging.memory.InMemoryLogger;
import org.pfsw.logging.memory.InMemoryLoggerFactory;
import org.pfsw.logging.memory.LogRecord;
import org.pfsw.logging.stdout.PrintStreamLogger;

public class DeferredInitializationLoggerAdapterTest
{
  @Test
  public void test_dynamic_replacement()
  {
    Logger logger;
    Logger targetLogger;
    DeferredInitializationLoggerAdapter loggerAdapter;
    InMemoryLogger memLogger;
    LoggerFactory loggerFactory;
    
    logger = LoggerFactoryProvider.getLoggerFactory(InMemoryLoggerFactory.LOGGER_TYPE).getLogger("dummy1");
    logger.setLogLevel(Logger.LL_DEBUG);
    assertTrue(logger instanceof DeferredInitializationLoggerAdapter);
    logger.logDebug("Sample1");
    logger.logInfo("Sample2");
    logger.logWarning("Sample3");
    logger.logError("Sample4");
    
    loggerAdapter = (DeferredInitializationLoggerAdapter)logger;
    targetLogger = loggerAdapter.getTargetLogger();
    assertTrue(targetLogger instanceof PrintStreamLogger);
    
    loggerFactory = new InMemoryLoggerFactory();
    LoggerFactoryProvider.register(loggerFactory);
    
    logger.logDebug("Sample10");
    logger.logInfo("Sample11");
    logger.logWarning("Sample12");
    logger.logError("Sample13");
    targetLogger = loggerAdapter.getTargetLogger();
    assertTrue(targetLogger instanceof InMemoryLogger);
    memLogger = (InMemoryLogger)targetLogger;
    assertEquals(4, memLogger.size());
    assertContains(memLogger, LogLevel.DEBUG, "Sample10");
    assertContains(memLogger, LogLevel.INFO, "Sample11");
    assertContains(memLogger, LogLevel.WARN, "Sample12");
    assertContains(memLogger, LogLevel.ERROR, "Sample13");
  }
  
  // -----  helper methods ----
  protected void assertContains(InMemoryLogger logger, LogLevel expectedLevel, String containedText) 
  {
    List<LogRecord> records;
    
    records = logger.findEntriesContaining(containedText);
    assertEquals("Expected exactly one log record!", 1, records.size());
    assertEquals(expectedLevel, records.get(0).getLogLevel());
  }
}