// ===========================================================================
// CONTENT  : CLASS LogBindingId
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 21/11/2022
// HISTORY  :
//  21/11/2022  mdu  CREATED
//
// Copyright (c) 2022, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging;

/**
 * A generic interface for the specification of log binding identifiers.
 */
public interface LogBindingId
{
  /**
   * Returns the unique name of the log binding.
   */
  String asString();
}
