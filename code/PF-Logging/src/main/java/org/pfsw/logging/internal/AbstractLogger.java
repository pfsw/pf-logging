// ===========================================================================
// CONTENT  : ABSTRACT CLASS AbstractLogger
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 04/02/2017
// HISTORY  :
//  04/02/2017  mdu  CREATED
//
// Copyright (c) 2017, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.internal;

// ===========================================================================
// IMPORTS
// ===========================================================================
import java.text.MessageFormat;
import org.pfsw.logging.Logger;

/**
 * Common implementation for internal re-use in various logger implementations.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public abstract class AbstractLogger implements Logger
{
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================
  private String loggerName = "";

  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  public AbstractLogger()
  {
    super();
  }

  public AbstractLogger(String loggerName)
  {
    super();
    this.setLoggerName(loggerName);
  }

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  @Override
  public String getName()
  {
    return this.loggerName;
  }

  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  protected String replacePlaceholders(String text, Object... params)
  {
    if ((params.length > 0) && (text.indexOf('{') >= 0))
    {
      return MessageFormat.format(text, params);
    }
    return text;
  }

  protected String getLoggerName()
  {
    return this.loggerName;
  }

  protected void setLoggerName(String loggerName)
  {
    this.loggerName = loggerName;
  }

  protected boolean isNullOrEmpty(String string)
  {
    return (string == null) || (string.length() == 0);
  }

  protected boolean isNullOrBlank(String string)
  {
    return (string == null) || (string.trim().length() == 0);
  }
}
