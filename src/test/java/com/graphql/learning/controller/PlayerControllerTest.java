package com.graphql.learning.controller;

import com.graphql.learning.model.Player;
import com.graphql.learning.model.Team;
import com.graphql.learning.service.PlayerService;
import graphql.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

@Import(PlayerService.class)
@GraphQlTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    /**
     * This test will validate the fetch all players functionality
     */
    @Test
    void testFetchAllPlayers() {
        GraphQlTester.EntityList<Player> playerList = graphQlTester.documentName("players")
                .operationName("FetchPlayers")
                .execute()
                .path("fetchPlayers")
                .entityList(Player.class);
        Assert.assertNotEmpty(playerList.get());
        playerList.hasSize(8);
    }


    /**
     * This method will test the fetch player by id functionality for valid Id
     */
    @Test
    void testFetchPlayerById() {
        Player player = graphQlTester.documentName("players")
                .operationName("FetchPlayerById")
                .variable("id",1)
                .execute()
                .path("fetchPlayerById")
                .entity(Player.class).get();
        Assertions.assertEquals(1, player.id());
        Assertions.assertEquals("MS Dhoni", player.name());
        Assertions.assertEquals(Team.CSK, player.team());
    }

    /**
     * This method will test the fetch player by id functionality for InvalidId
     */
    @Test
    void testFetchPlayerByIdInvalidId() {
        graphQlTester.documentName("players")
                .operationName("FetchPlayerById")
                .variable("id",11)
                .execute()
                .path("fetchPlayerById")
                .valueIsNull();
    }

    /**
     * This method will test the functionality of Create
     */
    @Test
    void testCreatePlayer() {
        Player createdPlayer = graphQlTester.documentName("players")
                .operationName("CreatePlayer")
                .variable("name", "Suresh Raina")
                .variable("team", Team.CSK)
                .execute()
                .path("createPlayer")
                .entity(Player.class)
                .get();
        Assertions.assertEquals("Suresh Raina", createdPlayer.name());
        Assertions.assertEquals(9, createdPlayer.id());
        Assertions.assertEquals(Team.CSK, createdPlayer.team());
    }

    /**
     * This method will test the functionality of Update
     */
    @Test
    void testUpdatePlayer() {
        Player createdPlayer = graphQlTester.documentName("players")
                .operationName("UpdatePlayer")
                .variable("id",8)
                .variable("name", "Chahal")
                .variable("team", Team.CSK)
                .execute()
                .path("updatePlayer")
                .entity(Player.class)
                .get();
        Assertions.assertEquals("Chahal", createdPlayer.name());
        Assertions.assertEquals(8, createdPlayer.id());
        Assertions.assertEquals(Team.CSK, createdPlayer.team());
    }

    /**
     * This method will test the functionality of Delete
     */
    @Test
    void testDeletePlayer() {
        Player createdPlayer = graphQlTester.documentName("players")
                .operationName("DeletePlayer")
                .variable("id",8)
                .execute()
                .path("deletePlayer")
                .entity(Player.class)
                .get();
        Assertions.assertEquals(8, createdPlayer.id());
    }

}