package me.dio.sdw24.adapters.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;

import feign.FeignException;
import feign.RequestInterceptor;
import me.dio.sdw24.domain.ports.GenerativeAiApi;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI", matchIfMissing = true)
@FeignClient(name = "openAiChatApi", url = "${gemini.base-url}", configuration = OpenAiChatApi.Config.class)
public interface GeminiService extends GenerativeAiApi {
    @PostMapping("v1beta/models/gemini-pro:generateContent")
    GeminiResp textOnlyInput(GeminiReq req);
    
    @Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s
                """.formatted(objective, context);

        GeminiReq req = new GeminiReq(
            List.of(new Content(List.of(new Part(prompt))))
        );


        try{
            GeminiResp resp = textOnlyInput(req);
            return resp.candidates().getFirst().content().parts().getFirst().text();
    
        }catch(FeignException httpErrors){
            return "Deu ruim! Erro de comunicação com API.";

        }catch (Exception unexpectedError){
            return "Foi mai! o retorno da API não contem os dados esperados.";
        }

    }

    record GeminiReq(List<Content> contents) {}

    record GeminiResp(List<Candidate> candidates) {}

    record Content(List<Part> parts) {}

    record Part(String text) {
    }
    
    record Candidate(Content content) {}


    /**
     * Config
     */
    public class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini-api-key}") String apikey){
            return requestTemplate -> requestTemplate.query("key", apikey);
        }
        
    }

}