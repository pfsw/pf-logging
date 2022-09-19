// ===========================================================================
// CONTENT  : CLASS PrintStreamOptions
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 12/04/2021
// HISTORY  :
//  12/04/2021  mdu  CREATED
//
// Copyright (c) 2021, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging.stdout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class with static fields and methods to influence PrintStreamLogger output.
 */
public class PrintStreamOptions
{
  private static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

  private static String timestampFormat = null;
  private static PrintLogLevel printLogLevel = PrintLogLevel.SHORT;

  public static PrintLogLevel getPrintLogLevel()
  {
    return printLogLevel;
  }

  public static void setPrintLogLevel(PrintLogLevel printLevel)
  {
    if (printLevel != null)
    {
      printLogLevel = printLevel;
    }
  }

  public static boolean isTimestampEnabled()
  {
    return getTimestampFormat() != null;
  }

  public static String getCurrentTimestampString()
  {
    if (isTimestampEnabled())
    {
      return formatTimestamp(new Date(), getTimestampFormat());
    }
    return "";
  }

  public static String formatTimestamp(Date timestamp, String pattern)
  {
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    return formatter.format(timestamp);
  }

  public static void enableDefaultTimestampFormat()
  {
    setTimestampFormat(DEFAULT_TIMESTAMP_FORMAT);
  }

  public static void setTimestampFormat(String pattern)
  {
    timestampFormat = pattern;
  }

  private static String getTimestampFormat()
  {
    return timestampFormat;
  }
}
