# PF-Logging - Logging Facade for Programmer's Friend (PF) Libraries

Instead of a direct dependency to any other logging framework all PF libraries are executing their log output against the facade provided by this component.
By utilizing the Java __ServiceLoader__ mechanism the component allows directing the logging to a framework that implements the __org.pfsw.logging.LoggerFactory__ interface.

If no such factory can be found on the classpath, the default binding is using the Java Util Logging (JUL) framework of the underlying JDK.
