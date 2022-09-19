// ===========================================================================
// CONTENT  : CLASS LogMessageOutputTarget
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 05/04/2021
// HISTORY  :
//  05/04/2021  mdu  CREATED
//
// Copyright (c) 2021, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

/**
 * Definition of all methods needed to output log data.
 * 
 * @author M.Duchrow
 */
public interface LogMessageOutputTarget
{
  void print(String string);
  void printException(Throwable ex);
  void close();
}
