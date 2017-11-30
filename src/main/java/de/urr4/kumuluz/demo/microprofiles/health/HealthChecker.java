package de.urr4.kumuluz.demo.microprofiles.health;

import de.urr4.kumuluz.demo.entities.Greeting;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

/**
 * Health Checker, registered like in config.yaml defined
 */
@Health
@ApplicationScoped
public class HealthChecker implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .named(Greeting.class.getSimpleName())
                .up()
                .build();
    }
}
