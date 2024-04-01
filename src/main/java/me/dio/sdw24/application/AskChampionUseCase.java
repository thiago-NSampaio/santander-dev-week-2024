package me.dio.sdw24.application;

import me.dio.sdw24.domain.model.Champion;
import me.dio.sdw24.domain.ports.ChampionsRepository;
import me.dio.sdw24.domain.ports.GenerativeAiApi;
import me.dio.sdw24.domain.exception.ChampionNotFoundException;


public record AskChampionUseCase(ChampionsRepository repository,GenerativeAiApi genAiapi) {
    public String askChampion(Long championId, String question) {
        Champion champion = repository.findById(championId).orElseThrow(() -> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                
                """;

        genAiapi.generateContent(objective, context);

        return context;
    }
} 