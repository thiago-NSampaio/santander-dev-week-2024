package me.dio.sdw24.application;

import me.dio.sdw24.domain.model.Champion;
import me.dio.sdw24.domain.ports.ChampionsRepository;
import me.dio.sdw24.domain.exception.ChampionNotFoundException;;


public record AskChampionUseCase(ChampionsRepository repository) {
    public String askChampion(Long championId, String question) {
        Champion champion = repository.findById(championId).orElseThrow(() -> new ChampionNotFoundException(championId));

        String championContext = champion.generateContextByQuestion(question);

        return championContext;
    }
} 