// ===========================================================================
// CONTENT  : CLASS LogMessageOutputPrintStream
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 05/04/2021
// HISTORY  :
//  05/04/2021  mdu  CREATED
//
// Copyright (c) 2021, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class LogMessageOutputPrintStream implements LogMessageOutputTarget
{
  private final PrintStream printStream;

  public static LogMessageOutputPrintStream create()
  {
    return new LogMessageOutputPrintStream();
  }

  public static LogMessageOutputPrintStream create(PrintStream printStream)
  {
    return new LogMessageOutputPrintStream(printStream);
  }

  @SuppressWarnings("resource")
  public static LogMessageOutputPrintStream create(String filename)
  {
    File file = null;
    FileOutputStream os = null;
    PrintStream ps = null;

    try
    {
      file = new File(filename);
      os = new FileOutputStream(file);
      ps = new PrintStream(os);
      return create(ps);
    }
    catch (IOException ex)
    {
      throw new RuntimeException("Failed to create file '" + filename + "' for log output", ex);
    }
  }

  public LogMessageOutputPrintStream()
  {
    this(System.out);
  }

  public LogMessageOutputPrintStream(PrintStream printStream)
  {
    super();
    this.printStream = printStream;
  }

  @SuppressWarnings("resource")
  @Override
  public void print(String string)
  {
    getPrintStream().print(string);
  }

  @SuppressWarnings("resource")
  @Override
  public void printException(Throwable ex)
  {
    ex.printStackTrace(getPrintStream());
  }

  @Override
  public void close()
  {
    getPrintStream().close();
  }

  protected PrintStream getPrintStream()
  {
    return this.printStream;
  }
}
