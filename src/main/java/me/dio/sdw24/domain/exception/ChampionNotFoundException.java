package me.dio.sdw24.domain.exception;

public class ChampionNotFoundException extends RuntimeException {
    public ChampionNotFoundException(Long championId) {
        super("Champion %s not found".formatted(championId));
    }
    
}
