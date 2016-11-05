package ont.paarma.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "basePackages = ont.paarma.service" })
public class SpringRootConfig {
}