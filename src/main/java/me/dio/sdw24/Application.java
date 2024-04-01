package me.dio.sdw24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import me.dio.sdw24.domain.ports.ChampionsRepository;
import me.dio.sdw24.domain.ports.GenerativeAiApi;
import me.dio.sdw24.application.AskChampionUseCase;
import me.dio.sdw24.application.ListChampionsUseCase;

@EnableFeignClients
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository) {
		return new ListChampionsUseCase(repository);
	}

	@Bean
	public AskChampionUseCase provideAskChampionUseCase(ChampionsRepository repository, GenerativeAiApi genAi) {
		return new AskChampionUseCase(repository, genAi);
	}

}
