// ===========================================================================
// CONTENT  : INTERFACE LoggerBindingNames
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 13/12/2015
// HISTORY  :
//  13/12/2015  mdu  CREATED
//
// Copyright (c) 2015, by MDCS. All rights reserved.
// ===========================================================================
package org.pfsw.logging ;

/**
 * Provides names of LoggerFactory implementations that correspond to
 * known logging frameworks or some internal implementation by this component. 
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public interface LoggerBindingNames
{ 
  /**
   * This is the name of a system property that might be used to set the
   * logging factory binding name.
   */
  public static final String PROP_BINDING_NAME = "org.pfsw.logging.binding";
  
  /**
   * The LoggerFactory with this name binds to an implementation
   * that absorbs all log messages. 
   */
  public static final String NIL = "NIL";

  /**
   * The LoggerFactory with this name binds to an implementation
   * that prints all log messages to stdout. 
   */
  public static final String STDOUT = "STDOUT";
  
  /**
   * The LoggerFactory with this name binds to an implementation
   * that forwards all log output to JDK's built-in java.util.logging (JUL). 
   */
  public static final String JUL = "JUL";

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
}