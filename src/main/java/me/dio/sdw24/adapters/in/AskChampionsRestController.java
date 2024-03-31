package me.dio.sdw24.adapters.in;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.sdw24.application.AskChampionUseCase;

@Tag(name = "Campeões", description = "Endpoint do domínio de Campeões de LOL")
@RestController
@RequestMapping("/champions")
public record AskChampionsRestController(AskChampionUseCase useCase) {

    @PostMapping("/{championId}/ask")
    public AskChampionResponse askChampion(
            @PathVariable Long championId,
            @RequestBody AskChampionRequest request) {
        String answer = useCase.askChampion(championId, request.question());

        return new AskChampionResponse(answer);
    }

    public record AskChampionRequest(String question) {
    }

    public record AskChampionResponse(String answer) {
    }
}
