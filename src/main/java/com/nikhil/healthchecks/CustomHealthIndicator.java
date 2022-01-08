package com.nikhil.healthchecks;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class CustomHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		boolean error = true;
		if (error) {
			return Health.down().withDetail("Error Key", 123).build();
		}
		return Health.up().build();
	}

}
