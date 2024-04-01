package me.dio.sdw24.adapters.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import feign.RequestInterceptor;
import me.dio.sdw24.domain.ports.GenerativeAiApi;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "OPENAI", matchIfMissing = true)
@FeignClient(name = "openAiChatApi", url = "${openai.base-url}", configuration = OpenAiChatApi.Config.class)
public interface OpenAiChatApi extends GenerativeAiApi {
    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);
    
    @Override
    default String generateContent(String objective, String context) {
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)
        );
        OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);

        OpenAiChatCompletionResp resp = chatCompletion(req);

        return resp.choices().getFirst().message().content();
    }

    record OpenAiChatCompletionReq(String model, List<Message> messages) {
    }

    record OpenAiChatCompletionResp(String role, List<Choice> choices) {
    }

    record Message(String role, String content) {
    }

    record Choice(Message message) {
    }

    /**
     * Config
     */
    public class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai-api-key}") String apikey){
            return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apikey));
        }
        
    }

}