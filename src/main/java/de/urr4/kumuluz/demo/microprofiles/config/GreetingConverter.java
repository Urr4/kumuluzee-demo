package de.urr4.kumuluz.demo.microprofiles.config;

import de.urr4.kumuluz.demo.entities.Greeting;
import org.eclipse.microprofile.config.spi.Converter;

import javax.annotation.Priority;

@Priority(500)
public class GreetingConverter implements Converter<Greeting> {

    @Override
    public Greeting convert(String s) {
        Greeting greeting = new Greeting();
        greeting.setName(s);
        return greeting;
    }
}
