package com.example.runnerz;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {
    private List<Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    Optional<Run> findById(Integer id){
        return runs.stream()
                .filter(run -> run.id() == id)
                .findFirst();
    }

    void createRun(Run run){
        runs.add(run);
    }

    void updateRun(Run run, Integer id){
        Optional<Run> existingRun = findById(id);
        existingRun.ifPresent(value -> runs.set(runs.indexOf(value), run));
    }

    void deleteRun(Integer id){
        runs.removeIf(run -> run.id().equals(id));
    }

    @PostConstruct
    private void init(){
        runs.add(new Run(
                1,
                "Monday morning run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                10,
                Location.INDOORS
        ));
        runs.add(new Run(
                2,
                "Wednesday evening run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(60),
                10,
                Location.INDOORS
        ));
    }
}
