package de.urr4.kumuluz.demo.resources;

import de.urr4.kumuluz.demo.entities.Greeting;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/configs")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfigResource {

    /**
     * Loads values from config.yaml
     * It is also possible to provide own ConfigSource Implementations but I didn't care
     */
    @Inject
    private Config config;

    /**
     * Values can be injected directly
     */
    @Inject
    @ConfigProperty(name = "demo.word2")
    private String injectedProperty;

    /**
     * Values can be defaulted if optional
     */
    @Inject
    @ConfigProperty(name = "doesntExist", defaultValue = "Property does not exist!")
    private String nonExistend;

    /**
     * Complexer Objects can be injected if there is a Converter registered
     * See {@link de.urr4.kumuluz.demo.microprofiles.config.GreetingConverter} and
     * META-INF/services/org.eclipse.microprofile.config.spi.converter
     */
    @Inject
    @ConfigProperty(name = "demo.greeting")
    private Greeting greeting;

    /**
     * Simple counter
     * Read metrics by sending a GET to localhost:8080/metrics with the Accept:application/json Header
     */
    @Inject
    @Metric(name = "call_counter")
    private Counter counter;

    /**
     * Statistic distibution
     */
    @Inject
    @Metric(name = "call_histogram")
    private Histogram histogram;

    /**
     * Count over time
     */
    @Inject
    @Metric(name = "call_meter")
    private Meter meter;

    @GET
    public String getConfigs() {
        counter.inc();
        histogram.update(counter.getCount());
        meter.mark();
        StringBuilder sb = new StringBuilder();
        String word1 = config.getValue("demo.word1", String.class);
        sb.append(word1 + "\n");
        String word2 = injectedProperty;
        sb.append(word2 + "\n");
        String word3 = nonExistend;
        sb.append(word3 + "\n");
        sb.append(greeting.greet() + "\n");

        return sb.toString();
    }

    @GET
    @Path(value = "/fail")
    @Timeout(value = 5000)
    @Asynchronous //Needed for the Timeout to grip
    @CircuitBreaker
    @Fallback(fallbackMethod = "fallback")
    public String provokeTimeout() throws InterruptedException {
        Thread.sleep(10000l);
        return "nope";
    }

    /**
     * Failsafe Method.
     * The provokeTimeout Method will fail after 5 Sek and this method will be called as fallback
     *
     * @return
     */
    public String fallback() {
        return "Fallback";
    }
}
