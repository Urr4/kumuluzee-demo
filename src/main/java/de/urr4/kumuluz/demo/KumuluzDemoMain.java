package de.urr4.kumuluz.demo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Appart from the pom, this is all thart is necessary to start a complet JavaEE application.
 * Just run "mvn clean package" and then "java -jar target/kumuluzDemo[...].jar
 */
@ApplicationPath("/")
public class KumuluzDemoMain extends Application {
}
