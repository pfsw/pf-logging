// ===========================================================================
// CONTENT  : CLASS InMemoryPrintStream
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 20/06/2014
// HISTORY  :
//  20/06/2014  mdu  CREATED
//
// Copyright (c) 2014, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.testhelper;

// ===========================================================================
// IMPORTS
// ===========================================================================
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Simple holder of a PrintStream and the underlying byte array with convenience
 * methods to get the written result as String.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class InMemoryPrintStream
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
  private PrintStream printStream;

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
}
