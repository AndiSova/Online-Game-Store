package com.ssn.aso;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.ssn.aso.archieve.GameRepository;
import com.ssn.aso.common.Game;
import com.ssn.aso.property.ApiUrlProperties;
import com.ssn.aso.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class, ApiUrlProperties.class })
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner runner(GameRepository productRepository) {
		return args -> {

			Game game1 = new Game("Fifa 23");
			game1.setPrice(69.99);
			game1.setImage("/images/fifa23.jpg");
			productRepository.save(game1);

			Game game2 = new Game("Elden Ring");
			game2.setPrice(269.00);
			game2.setImage("/images/elden_ring.jpg");
			productRepository.save(game2);

			Game game3 = new Game("The Callisto Protocol");
			game3.setPrice(19.99);
			game3.setImage("/images/callisto.jpg");
			productRepository.save(game3);

			Game game4 = new Game("God of War Ragnarok");
			game4.setPrice(19.99);
			game4.setImage("/images/gowR.jpg");
			productRepository.save(game4);
		};
	}
}
