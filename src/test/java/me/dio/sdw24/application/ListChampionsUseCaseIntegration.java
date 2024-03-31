package me.dio.sdw24.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import me.dio.sdw24.domain.model.Champion;

import java.util.List;

@SpringBootTest
public class ListChampionsUseCaseIntegration {
    @Autowired
    private ListChampionsUseCase listChampionsUseCase;

    @Test
    public void testListChampions() {
        List<Champion> Champions = listChampionsUseCase.findAll();

        Assertions.assertEquals(12,Champions.size());
    }
}
