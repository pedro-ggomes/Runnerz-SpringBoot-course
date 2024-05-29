package com.example.runnerz;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class RunRepository {
    private static final Logger log = LoggerFactory.getLogger("RunRepository");
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("SELECT * FROM run").query(Run.class).list();
    }

    public Optional<Run> findById(Integer id){
        return jdbcClient.sql("SELECT * FROM RUN where id = :id")
                .param("id",id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run){
        var updated = jdbcClient.sql("INSERT INTO RUN(id,title,started_on,finished_on,kilometers,location) values ( ?,?,?,?,?,? )")
                .params(List.of(run.id(),run.title(),run.startedOn(),run.finishedOn(),run.kilometers(),run.location().toString()))
                .update();
        Assert.state(updated==1,"Failed to create run " + run.title());
    }

    public void update(Run run, Integer id){
        var updated = jdbcClient.sql("update run set title = ?, started_on = ?, finished_on = ?, kilometers = ?, location = ? where id = ?")
                .params(List.of(run.title(),run.startedOn(),run.finishedOn(),run.kilometers(),run.location().toString(), id))
                .update();
        Assert.state(updated==1,"Failed to update run" + run.title());
    }

    public void delete(Integer id){
        var updated = jdbcClient.sql("DELETE from Run where id = :id")
                .param("id",id)
                .update();
        Assert.state(updated == 1, "Failed to delete run " + id);
    }

//    private List<Run> runs = new ArrayList<>();
//
//    List<Run> findAll() {
//        return runs;
//    }
//
//    Optional<Run> findById(Integer id){
//        return runs.stream()
//                .filter(run -> run.id() == id)
//                .findFirst();
//    }
//
//    void createRun(Run run){
//        runs.add(run);
//    }
//
//    void updateRun(Run run, Integer id){
//        Optional<Run> existingRun = findById(id);
//        existingRun.ifPresent(value -> runs.set(runs.indexOf(value), run));
//    }
//
//    void deleteRun(Integer id){
//        runs.removeIf(run -> run.id().equals(id));
//    }
//
//    @PostConstruct
//    private void init(){
//        runs.add(new Run(
//                1,
//                "Monday morning run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(30),
//                10,
//                Location.INDOORS
//        ));
//        runs.add(new Run(
//                2,
//                "Wednesday evening run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(60),
//                10,
//                Location.INDOORS
//        ));
//    }
}
