package com.example.runnerz.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(RunRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RunRepositoryTest {

    @Autowired
    RunRepository runRepository;

    @BeforeEach
    void setup() {
        runRepository.create(new Run(1,
                "Monday morning run",
                LocalDateTime.now(),
                LocalDateTime.now().minusMinutes(30),
                3,
                Location.INDOORS));

        runRepository.create(new Run(2,
                "Wednesday evening run",
                LocalDateTime.now(),
                LocalDateTime.now().minusMinutes(60),
                6,
                Location.INDOORS));
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = runRepository.findAll();
        assertEquals(2, runs.size());
    }

    @Test
    void shouldFindRunWithValidId() {
        Run run = runRepository.findById(1).get();
        assertEquals("Monday morning run", run.title());
        assertEquals(3, run.kilometers());
    }

    @Test
    void shouldFindRunWithInvalidId() {
        var run = runRepository.findById(3);
        assertTrue(run.isEmpty());
    }

    @Test
    void shouldCreateNewRun() {
        runRepository.create(new Run(3,
                "Friday morning run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                3,
                Location.INDOORS));
        List<Run> runs = runRepository.findAll();
        assertEquals(3, runs.size());
    }

    @Test
    void shouldUpdateRun() {
        runRepository.update(new Run(1,
                "Monday morning run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                5,
                Location.OUTDOORS), 1);
        var run = runRepository.findById(1).get();
        assertEquals("Monday morning run", run.title());
        assertEquals(5, run.kilometers());
        assertEquals(Location.OUTDOORS, run.location());
    }

    @Test
    void shouldDeleteRun() {
        runRepository.delete(1);
        List<Run> runs = runRepository.findAll();
        assertEquals(1, runs.size());
    }
}
