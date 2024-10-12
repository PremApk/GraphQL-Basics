package com.graphql.learning.service;

import com.graphql.learning.model.Player;
import com.graphql.learning.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This service class will help the controller to provide necessary data
 *
 * @author premapk 
 */
@Service
public class PlayerService {

    private final List<Player> players = new ArrayList<>();

    AtomicInteger id = new AtomicInteger(0);

    //Get all players
    public List<Player> findAll() {
        return players;
    }

    //Get Player by Id
    public Optional<Player> findPlayerById(Integer id) {
        return players.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
    }

    //Add a new player
    public Player createPlayer(String name, Team team) {
        Player player = new Player(id.incrementAndGet(),name, team);
        players.add(player);
        return player;
    }

    //Remove the player by Id
    public Player deletePlayerById(Integer id) {
        Player player = players.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        players.remove(player);
        return player;
    }

    //Update the player
    public Player updatePlayer(Integer id, String name, Team team) {
        Player updatedPlayer = new Player(id, name, team);
        Optional<Player> existingPlayer = players.stream().filter(p -> p.id().equals(id)).findFirst();
        if (existingPlayer.isPresent()) {
            int index = players.indexOf(existingPlayer.get());
            players.set(index, updatedPlayer);
        } else {
            throw new IllegalArgumentException("Invalid Player");
        }
        return updatedPlayer;
    }

    //In-memory DB entries
    @PostConstruct
    private void init() {
        players.add(new Player(id.incrementAndGet(), "MS Dhoni", Team.CSK));
        players.add(new Player(id.incrementAndGet(), "Ravindra Jadeja", Team.CSK));
        players.add(new Player(id.incrementAndGet(), "Rohit Sharma", Team.MI));
        players.add(new Player(id.incrementAndGet(), "Jasprit Bumra", Team.MI));
        players.add(new Player(id.incrementAndGet(), "Hardik pandya", Team.GT));
        players.add(new Player(id.incrementAndGet(), "Shubman Gill", Team.GT));
        players.add(new Player(id.incrementAndGet(), "Sanju Samson", Team.RR));
        players.add(new Player(id.incrementAndGet(), "Yuzvendra Chahal", Team.RR));
    }

}
