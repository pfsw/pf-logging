package org.pfsw.logging;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Before;
import org.junit.Test;
import org.pfsw.logging.deferred.DeferredInitializationLoggerFactory;
import org.pfsw.logging.internal.SystemPropertyName;
import org.pfsw.logging.jul.JavaUtilLoggerFactory;
import org.pfsw.logging.nil.NilLoggerFactory;
import org.pfsw.logging.stdout.PrintStreamLoggerFactory;
import org.pfsw.logging.testhelper.Dummy1LoggerFactory;
import org.pfsw.logging.testhelper.Dummy2LoggerFactory;
import org.pfsw.logging.testhelper.Dummy3LoggerFactory;

public class LoggerFactoryProviderTest
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================
  private static final String DEFAULT_LOGGER_FACTORY_CLASS = System.getProperty("org.pf.logging.Expected.Default.Class",
      PrintStreamLoggerFactory.class.getName());

  // =========================================================================
  // TEST METHODS
  // =========================================================================
  @Test
  public void test_getLoggerFactory_default()
  {
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory();
    assertThat(factory.getClass().getName(), is(DEFAULT_LOGGER_FACTORY_CLASS));
  }

  @Test
  public void test_getLoggerFactory_changed_default()
  {
    LoggerFactoryProvider.setDefaultFactoryId(BuiltInLogBindingId.NIL);
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory();
    assertThat(factory.getClass().getName(), is(NilLoggerFactory.class.getName()));
  }

  @Test
  public void test_getLoggerFactory_JUL()
  {
    LoggerFactoryProvider.setDefaultFactoryId(BuiltInLogBindingId.JUL);
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory();
    assertThat(factory, is(instanceOf(JavaUtilLoggerFactory.class)));
  }

  @Test
  public void test_getLoggerFactory_default_property_binding()
  {
    LoggerFactory factory;

    factory = LoggerFactoryProvider.getLoggerFactory();
    assertThat(factory.getName(), is(BuiltInLogBindingId.STDOUT.asString()));

    System.setProperty(SystemPropertyName.LOG_BINDING_NAME.asString(), "DUMMY2");
    LoggerFactoryProvider.reset();

    factory = LoggerFactoryProvider.getLoggerFactory();
    assertThat(factory.getName(), is("DUMMY2"));

    System.clearProperty(SystemPropertyName.LOG_BINDING_NAME.asString());
    LoggerFactoryProvider.reset();
    factory = LoggerFactoryProvider.getLoggerFactory();
    assertThat(factory.getName(), is(BuiltInLogBindingId.STDOUT.asString()));
  }

  @Test
  public void test_getLoggerFactory_dynamically_registered()
  {
    LoggerFactory factory;

    factory = LoggerFactoryProvider.getLoggerFactory("DUMMY1");
    assertThat(factory.getClass().getName(), is(Dummy1LoggerFactory.class.getName()));
    factory = LoggerFactoryProvider.getLoggerFactory("DUMMY2");
    assertThat(factory.getClass().getName(), is(Dummy2LoggerFactory.class.getName()));
    factory = LoggerFactoryProvider.getLoggerFactory("DUMMY99");
    assertThat(factory.getClass().getName(), is(DeferredInitializationLoggerFactory.class.getName()));
  }

  @Test
  public void test_getLoggerFactory_additionally_registered()
  {
    LoggerFactory factory;

    LoggerFactoryProvider.register(new Dummy3LoggerFactory());
    factory = LoggerFactoryProvider.getLoggerFactory("DUMMY3");
    assertThat(factory.getClass().getName(), is(Dummy3LoggerFactory.class.getName()));
  }

  @Test
  public void test_getLoggerFactory()
  {
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory(BuiltInLogBindingId.JUL);
    assertThat(factory, is(instanceOf(JavaUtilLoggerFactory.class)));
  }

  @Test
  public void test_getLogger_by_class()
  {
    assertThat(LoggerFactoryProvider.getLogger(LoggerFactoryProviderTest.class), is(not(nullValue())));
  }

  @Test
  public void test_getLogger_by_name()
  {
    assertThat(LoggerFactoryProvider.getLogger("unittest"), is(not(nullValue())));
  }

  @Test
  public void test_getLogger2_by_class()
  {
    assertThat(LoggerFactoryProvider.getLogger2(LoggerFactoryProviderTest.class), is(not(nullValue())));
  }

  @Test
  public void test_getLogger2_by_name()
  {
    assertThat(LoggerFactoryProvider.getLogger2("unittest"), is(not(nullValue())));
  }

  @Test
  public void test_getLoggerFactory_soleNotBuiltIn()
  {
    // Remove 1 of the 2 automatically registered factories 
    LoggerFactoryProvider.deregister(Dummy1LoggerFactory.FACTORY_ID);
    LoggerFactoryProvider.reset();
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory();
    assertThat(factory, is(instanceOf(Dummy2LoggerFactory.class)));
  }

  @Test
  public void test_getLoggerFactory_soleNotBuiltIn_but_multiple_found()
  {
    LoggerFactory factory = LoggerFactoryProvider.getLoggerFactory();
    assertThat(factory.getName(), is(LoggerFactoryProvider.DEFAULT_FACTORY_NAME));
  }

  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================
  @Before
  public void setUp() throws Exception
  {
    LoggerFactoryProvider.setDefaultFactoryId(null);
    LoggerFactoryProvider.initialize();
  }
}
