// ===========================================================================
// CONTENT  : CLASS LogMessageOutputPrintStreamTest
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 05/04/2021
// HISTORY  :
//  05/04/2021  mdu  CREATED
//
// Copyright (c) 2021, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.pfsw.logging.testhelper.UnitTestHelper;

public class LogMessageOutputPrintStreamTest
{
  private static final String FILENAME = UnitTestHelper.TEMP_FOLDER + "/out1.log";
  private static final File LOG_FILE = new File(FILENAME);

  @Before
  public void setup()
  {
    LOG_FILE.delete();
  }

  @Test
  public void test_create__filename()
  {
    LogMessageOutputPrintStream output;

    assertFalse(LOG_FILE.exists());
    output = LogMessageOutputPrintStream.create(FILENAME);
    output.print("test");
    assertTrue(LOG_FILE.exists());
  }
}
