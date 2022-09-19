// ===========================================================================
// CONTENT  : CLASS LogMessageOutputTargetRegistryTest
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 05/04/2021
// HISTORY  :
//  05/04/2021  mdu  CREATED
//
// Copyright (c) 2021, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LogMessageOutputTargetRegistryTest
{
  @Before
  public void setup()
  {
    LogMessageOutputTargetRegistry.clear();
  }

  @Test
  public void test_getOutputTarget__register_null_output()
  {
    LogMessageOutputTargetRegistry.registerOutputTarget("logger1", null);
    assertTrue(LogMessageOutputTargetRegistry.DEFAULT_OUTPUT_TARGET == LogMessageOutputTargetRegistry.getOutputTarget("just.any.logger.name"));
  }

  @Test
  public void test_getOutputTarget__register_null_logger_name()
  {
    LogMessageOutputTargetRegistry.registerOutputTarget(null, LogMessageOutputPrintStream.create());
    assertTrue(LogMessageOutputTargetRegistry.DEFAULT_OUTPUT_TARGET == LogMessageOutputTargetRegistry.getOutputTarget("just.any.logger.name"));
  }

  @Test
  public void test_getOutputTarget__null_logger_name()
  {
    assertTrue(LogMessageOutputTargetRegistry.DEFAULT_OUTPUT_TARGET == LogMessageOutputTargetRegistry.getOutputTarget(null));
  }

  @Test
  public void test_getOutputTarget__nothing_registered()
  {
    assertTrue(LogMessageOutputTargetRegistry.DEFAULT_OUTPUT_TARGET == LogMessageOutputTargetRegistry.getOutputTarget("just.any.logger.name"));
  }

  @Test
  public void test_getOutputTarget__root_logger_output()
  {
    LogMessageOutputTarget outputTarget = LogMessageOutputPrintStream.create();

    LogMessageOutputTargetRegistry.registerOutputTarget(LogMessageOutputTargetRegistry.ROOT_LOGGER_NAME, outputTarget);
    assertTrue(outputTarget == LogMessageOutputTargetRegistry.getOutputTarget("just.any.logger.name"));
  }

  @Test
  public void test_getOutputTarget__intermediate_parent()
  {
    LogMessageOutputTarget outputTarget = LogMessageOutputPrintStream.create();

    LogMessageOutputTargetRegistry.registerOutputTarget("org.pfsw.logging", outputTarget);
    assertTrue(outputTarget == LogMessageOutputTargetRegistry.getOutputTarget(LogMessageOutputTargetRegistryTest.class.getName()));
  }

  @Test
  public void test_getOutputTarget__explicit_target()
  {
    LogMessageOutputTarget outputTarget1 = LogMessageOutputPrintStream.create();
    LogMessageOutputTarget outputTarget2 = LogMessageOutputPrintStream.create();

    LogMessageOutputTargetRegistry.registerOutputTarget("org.pfsw.logging", outputTarget1);
    LogMessageOutputTargetRegistry.registerOutputTarget(LogMessageOutputTargetRegistryTest.class.getName(), outputTarget2);
    assertFalse(outputTarget1 == LogMessageOutputTargetRegistry.getOutputTarget(LogMessageOutputTargetRegistryTest.class.getName()));
    assertTrue(outputTarget2 == LogMessageOutputTargetRegistry.getOutputTarget(LogMessageOutputTargetRegistryTest.class.getName()));
  }
}
