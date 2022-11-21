// ===========================================================================
// CONTENT  : INTERFACE LoggerBindingNames
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.1 - 21/11/2022
// HISTORY  :
//  13/12/2015  mdu  CREATED
//  21/11/2022  mdu  deprecated 
//
// Copyright (c) 2015-2022, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging ;

/**
 * Provides names of LoggerFactory implementations that correspond to
 * known logging frameworks or some internal implementation by this component. 
 *
 * @author Manfred Duchrow
 * @version 1.1
 * @deprecated Use enum {@link BuiltInLogBindingId} instead.
 */
@Deprecated
public interface LoggerBindingNames
{ 
  /**
   * The LoggerFactory with this name binds to an implementation
   * that absorbs all log messages. 
   */
  public static final String NIL = BuiltInLogBindingId.NIL.asString();

  /**
   * The LoggerFactory with this name binds to an implementation
   * that prints all log messages to stdout. 
   */
  public static final String STDOUT = BuiltInLogBindingId.STDOUT.asString();
  
  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to JDK's built-in java.util.logging (JUL). 
   */
  public static final String JUL = BuiltInLogBindingId.JUL.asString();

  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to Apache Jakarta Commons Logging. 
   */
  public static final String JCL = "JCL";
  
  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to Simple Logging Facade for Java. 
   */
  public static final String SLF4J = "SLF4J";
  
  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to Apache Logging for Java. 
   */
  public static final String LOG4J = "LOG4J";
  
  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to Apache Logging for Java. 
   */
  public static final String LOG4J2 = "LOG4J2";
  
  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to JBoss logging. 
   */
  public static final String JBOSS_LOGGING = "JBL";
  
  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to JBoss logging. 
   */
  public static final String LOGBACK = "LOGBACK";
}