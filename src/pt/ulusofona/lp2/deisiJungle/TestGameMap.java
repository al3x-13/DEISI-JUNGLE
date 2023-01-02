package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestGameMap {
    @Test
    public void test_GetMapCell_With_Invalid_Cell() {
        GameMap map = new GameMap(10, new ArrayList<>());
        assertNull(map.getMapCell(11));
    }

    @Test
    public void test_MovePlayerAndUpdateEnergy_With_Missing_Player() {
        Player player1 = new Player(
                3,
                "Player 1",
                new Species('L', "Leão", "lion.png", DietType.CARNIVORE, 80, 2, 10, 4, 6)
        );
        player1.updateMapPosition(3);
        GameMap map = new GameMap(10, new ArrayList<>());
        MapCell cell = map.getMapCell(5);

        assertFalse(map.movePlayerAndUpdateEnergy(player1, 8, 2));
    }

    @Test
    public void test_MovePlayerAndUpdateEnergy_With_Invalid_Dest_Cell() {
        Player player1 = new Player(
                3,
                "Player 1",
                new Species('L', "Leão", "lion.png", DietType.CARNIVORE, 80, 2, 10, 4, 6)
        );
        GameMap map = new GameMap(10, new ArrayList<>());

        assertFalse(map.movePlayerAndUpdateEnergy(player1, 12, 2));
    }

    @Test
    public void test_GetGameResultsByIDs_By_Player_Getting_To_Finish() {
        Player player1 = new Player(
                3,
                "Player 1",
                new Species('L', "Leão", "lion.png", DietType.CARNIVORE, 80, 2, 10, 4, 6)
        );
        Player player2 = new Player(
                5,
                "Player 2",
                new Species('T', "Tartaruga", "lion.png", DietType.CARNIVORE, 80, 2, 10, 4, 6)
        );
        GameMap map = new GameMap(10, new ArrayList<>());
        map.getMapCell(10).addPlayer(player2.getID());
        map.getMapCell(8).addPlayer(player1.getID());
        ArrayList<Integer> results = map.getGameResultsByIDs();
        assertTrue(
                results.get(0) == player2.getID()
                && results.get(1) == player1.getID()
        );
    }

    @Test
    public void test_GetGameResultsByIDs_By_Gap_Between_First_And_Second() {
        Player player1 = new Player(
                3,
                "Player 1",
                new Species('L', "Leão", "lion.png", DietType.CARNIVORE, 80, 2, 10, 4, 6)
        );
        Player player2 = new Player(
                5,
                "Player 2",
                new Species('T', "Tartaruga", "turtle.png", DietType.OMNIVORE, 150, 1, 5, 1, 3)
        );
        Player player3 = new Player(
                6,
                "Player 3",
                new Species('L', "Leão", "lion.png", DietType.CARNIVORE, 80, 2, 10, 4, 6)
        );
        GameMap map = new GameMap(10, new ArrayList<>());
        map.getMapCell(9).addPlayer(player1.getID());
        map.getMapCell(3).addPlayer(player2.getID());
        map.getMapCell(3).addPlayer(player3.getID());
        ArrayList<Integer> results = map.getGameResultsByIDs();
        assertTrue(
                results.get(0) == player2.getID()
                        && results.get(1) == player1.getID()
                        && results.get(2) == player3.getID()
        );
    }
}
