// ===========================================================================
// CONTENT  : CLASS InMemoryPrintStream
// AUTHOR   : Manfred Duchrow
// VERSION  : 2.0 - 05/04/2021
// HISTORY  :
//  20/06/2014  mdu  CREATED
//  05/04/2021  mdu   changed -> implements LogMessageOutputTarget
//
// Copyright (c) 2014-2021, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.testhelper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.pfsw.logging.stdout.LogMessageOutputTarget;

/**
 * Simple holder of a PrintStream and the underlying byte array with convenience
 * methods to get the written result as String.
 *
 * @author Manfred Duchrow
 * @version 2.0
 */
public class InMemoryPrintStream implements LogMessageOutputTarget
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
  private PrintStream printStream;

  // =========================================================================
  // CLASS METHODS
  // =========================================================================
  public static InMemoryPrintStream create()
  {
    return new InMemoryPrintStream();
  } 

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public InMemoryPrintStream()
  {
    super();
    this.printStream = new PrintStream(this.byteStream);
  } 
  
  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
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
  
  public PrintStream getPrintStream()
  {
    return printStream;
  }
  
  public String getContent()
  {
    try
    {
      return this.byteStream.toString("UTF-8");
    }
    catch (UnsupportedEncodingException ex)
    {
      // never happens
      ex.printStackTrace();
      return "";
    }
  }
  
  @Override
  public String toString()
  {
    return String.format("%s()", getClass().getSimpleName());
  }
}
