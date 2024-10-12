package com.graphql.learning.controller;

import com.graphql.learning.model.Player;
import com.graphql.learning.model.Team;
import com.graphql.learning.service.PlayerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class PlayerController {

    //Player Service will be autowired (Dependency Injection)
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    //Query mapping used to fetch the data
    @QueryMapping
    public List<Player> fetchPlayers() {
        return playerService.findAll();
    }

    @QueryMapping
    public Optional<Player> fetchPlayerById(@Argument Integer id) {
        return playerService.findPlayerById(id);
    }

    //Mutation Mapping is used to perform Create,Update and Delete data
    @MutationMapping
    public Player createPlayer(@Argument String name, @Argument Team team) {
        return playerService.createPlayer(name, team);
    }

    @MutationMapping
    public Player updatePlayer(@Argument Integer id, @Argument String name, @Argument Team team) {
        return playerService.updatePlayer(id, name, team);
    }

    @MutationMapping
    public Player deletePlayer(@Argument Integer id) {
        return playerService.deletePlayerById(id);
    }

}
