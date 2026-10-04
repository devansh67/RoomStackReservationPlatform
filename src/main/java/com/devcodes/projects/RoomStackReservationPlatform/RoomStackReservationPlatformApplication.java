package com.devcodes.projects.RoomStackReservationPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RoomStackReservationPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomStackReservationPlatformApplication.class, args);
	}

}
