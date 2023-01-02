package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TestGameManager {
    @Test
    public void test_Main() {
        Main main = new Main();
        assertEquals("lulz", main.getLulz());
    }

    @Test
    public void test_01_CreateInitialJungle() {
        // Testing 2 players with same ID
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "3", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };

        assertNotNull(game.createInitialJungle(10, playersInfo));
    }

    @Test
    public void test_02_CreateInitialJungle() {
        // Testing with empty/null player name
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "", "L"},
                { "3", "Player 2", "T"}
        };
        assertNotNull(game.createInitialJungle(10, playersInfo));

        playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", null, "T"}
        };
        assertNotNull(game.createInitialJungle(10, playersInfo));
    }

    @Test
    public void test_03_CreateInitialJungle() {
        // Testing with a non-existent species
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "Y"},
                { "3", "Player 2", "T"}
        };
        assertNotNull(game.createInitialJungle(10, playersInfo));
    }

    @Test
    public void test_04_CreateInitialJungle() {
        // Testing with invalid number of players
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "3", "Player 1", "T"}
        };
        assertNotNull(game.createInitialJungle(10, playersInfo));

        playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        assertNull(game.createInitialJungle(10, playersInfo));


        playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"},
                { "4", "Player 3", "T"}
        };
        assertNull(game.createInitialJungle(10, playersInfo));
    }

    @Test
    public void test_05_CreateInitialJungle() {
        // Testing with short number of positions
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        assertNotNull(game.createInitialJungle(2, playersInfo));
        assertNotNull(game.createInitialJungle(3, playersInfo));
        assertNull(game.createInitialJungle(4, playersInfo));
    }

    @Test
    public void test_06_CreateInitialJungle() {
        // Testing with empty/null food ID
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };

        String[][] foodsInfo = new String[][] {
                { "", "3" }
        };
        assertNotNull(game.createInitialJungle(10, playersInfo, foodsInfo));

        foodsInfo = new String[][] {
                { null, "3" }
        };
        assertNotNull(game.createInitialJungle(10, playersInfo, foodsInfo));
    }

    @Test
    public void test_07_CreateInitialJungle() {
        // Testing with non-existent food type
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };

        String[][] foodsInfo = new String[][] {
                { "z", "3" }
        };
        assertNotNull(game.createInitialJungle(10, playersInfo, foodsInfo));
    }

    @Test
    public void test_08_CreateInitialJungle() {
        // Testing with valid values
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        String[][] foodsInfo = new String[][] {
                { "a", "3" }
        };
        assertNull(game.createInitialJungle(10, playersInfo, foodsInfo));
    }

    @Test
    public void test_09_CreateInitialJungle() {
        // Testing with invalid player ID
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "lulz", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        InitializationError error = game.createInitialJungle(10, playersInfo);
        assertEquals("Invalid player ID! The ID must be a number.", error.getMessage());
    }

    @Test
    public void test_10_CreateInitialJungle() {
        // Testing with invalid Species ID
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", ""},
                { "3", "Player 2", "T"}
        };
        InitializationError error = game.createInitialJungle(10, playersInfo);
        assertEquals("Invalid Species ID! The Species ID must be a character.", error.getMessage());
    }

    @Test
    public void test_11_CreateInitialJungle() {
        // Testing with multiple Tarzans
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "Z"},
                { "3", "Player 2", "Z"}
        };
        InitializationError error = game.createInitialJungle(10, playersInfo);
        assertEquals("Tarzan already exists!", error.getMessage());
    }

    @Test
    public void test_12_CreateInitialJungle() {
        // Testing with invalid food position
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "E"},
                { "3", "Player 2", "Z"}
        };
        String[][] foodsInfo = new String[][] {
                { "a", "0" }
        };
        InitializationError error = game.createInitialJungle(10, playersInfo, foodsInfo);
        assertEquals(
                "Invalid Food Position! Food position must be in " +
                        "the map range except start and finish positions.",
                error.getMessage());
    }

    @Test
    public void test_13_CreateInitialJungle() {
        // Testing with invalid food value
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "E"},
                { "3", "Player 2", "Z"}
        };
        String[][] foodsInfo = new String[][] {
                { "a", "lulz" }
        };
        InitializationError error = game.createInitialJungle(10, playersInfo, foodsInfo);
        assertEquals(
                "Invalid Food Position! Food position must be an integer value.",
                error.getMessage());
    }

    @Test
    public void test_01_GetPlayerIds() {
        // Testing with non-existent cell
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, playersInfo);
        int[] expectedResult = new int[0];
        assertEquals(expectedResult.length, game.getPlayerIds(30).length);
    }

    @Test
    public void test_02_GetPlayerIds() {
        // Testing with multiple player IDs in the same cell
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"},
                { "4", "Player 3", "T"}
        };
        game.createInitialJungle(10, playersInfo);

        // Adds player with ID 3 to cell with index 20
        MapCell cell = game.getMap().getMapCell(10);
        cell.addPlayer(3);
        int[] expectedResult = new int[] { 3 };
        assertEquals(expectedResult.length, game.getPlayerIds(10).length);
        assertEquals(expectedResult[0], game.getPlayerIds(10)[0]);

        // Adds player with ID 5 to cell with index 20
        cell.addPlayer(5);
        expectedResult = new int[] { 3,5 };
        assertEquals(expectedResult.length, game.getPlayerIds(10).length);
        assertEquals(expectedResult[0], game.getPlayerIds(10)[0]);
        assertEquals(expectedResult[1], game.getPlayerIds(10)[1]);

        // Remove player with ID 3 from the cell with index 20
        cell.rmPlayer(3);
        expectedResult = new int[] { 5 };
        assertEquals(expectedResult.length, game.getPlayerIds(10).length);
        assertEquals(expectedResult[0], game.getPlayerIds(10)[0]);
    }

    @Test
    public void test_03_GetPlayerIds() {
        // Testing with invalid cell
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][]{
                {"1", "Player 1", "L"},
                {"3", "Player 2", "T"},
                {"4", "Player 3", "T"}
        };
        game.createInitialJungle(10, playersInfo);
        game.getPlayerIds(0);
    }

    @Test
    public void test_01_GetSquareInfo() {
        // Testing with non-existent cell
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, playersInfo);
        assertNull(game.getSquareInfo(30));
        assertNull(game.getSquareInfo(11));
        assertNull(game.getSquareInfo(0));
    }

    @Test
    public void test_02_GetSquareInfo() {
        // Testing with empty cell with no players
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, playersInfo);

        String[] expectedResult = new String[] { "middle.png", "Vazio", "" };
        String[] realResult = game.getSquareInfo(5);
        assertTrue(
                expectedResult[0].equals(realResult[0])
                && expectedResult[1].equals(realResult[1])
                && expectedResult[2].equals(realResult[2])
        );
    }

    @Test
    public void test_03_GetSquareInfo() {
        // Testing with empty cell with players
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, playersInfo);

        // Add players with ID 3 to cell 5
        MapCell cell = game.getMap().getMapCell(5);
        cell.addPlayer(3);
        String[] expectedResult = new String[] { "middle.png", "Vazio", "3" };
        String[] realResult = game.getSquareInfo(5);
        assertTrue(
                expectedResult[0].equals(realResult[0])
                        && expectedResult[1].equals(realResult[1])
                        && expectedResult[2].equals(realResult[2])
        );
    }

    @Test
    public void test_04_GetSquareInfo() {
        // Testing with finish cell
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, playersInfo);

        // Add players with ID 3 to cell 5
        MapCell cell = game.getMap().getMapCell(10);
        cell.addPlayer(3);

        String[] expectedResult = new String[] { "finish.png", "Meta", "3" };
        String[] realResult = game.getSquareInfo(10);
        assertTrue(
                expectedResult[0].equals(realResult[0])
                        && expectedResult[1].equals(realResult[1])
                        && expectedResult[2].equals(realResult[2])
        );
    }

    @Test
    public void test_05_GetSquareInfo() {
        // Testing with food in cell
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        String[][] foodsInfo = new String[][] {
                { "a", "3" },
                { "b", "6"}
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);

        String[] expectedResult = new String[] { "water.png", "Agua : + 15U|20% energia", "" };
        String[] realResult = game.getSquareInfo(3);
        assertTrue(
                expectedResult[0].equals(realResult[0])
                        && expectedResult[1].equals(realResult[1])
                        && expectedResult[2].equals(realResult[2])
        );

        game.moveCurrentPlayer(5, true);
        expectedResult = new String[] { "bananas.png", "Bananas : 2 : + 40 energia", "1" };
        realResult = game.getSquareInfo(6);
        assertTrue(
                expectedResult[0].equals(realResult[0])
                        && expectedResult[1].equals(realResult[1])
                        && expectedResult[2].equals(realResult[2])
        );
    }

    @Test
    public void test_01_GetPlayerInfo() {
        // Testing with invalid ID
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, playersInfo);

        assertNull(game.getPlayerInfo(5));
    }

    @Test
    public void test_02_GetPlayerInfo() {
        // Testing with valid ID
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, playersInfo);

        String[] expectedResult = new String[] { "3", "Player 2", "T", "150", "1..3" };
        String[] realResult = game.getPlayerInfo(3);
        assertTrue(
                expectedResult[0].equals(realResult[0]) &&
                        expectedResult[1].equals(realResult[1]) &&
                        expectedResult[2].equals(realResult[2]) &&
                        expectedResult[3].equals(realResult[3]) &&
                        expectedResult[4].equals(realResult[4])
        );
    }

    @Test
    public void test_01_GetCurrentPlayerEnergyInfo() {
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, playersInfo);

        String[] expectedResult = new String[] { "6", "10" };
        String[] realResult = game.getCurrentPlayerEnergyInfo(3);
        assertTrue(
                expectedResult[0].equals(realResult[0]) &&
                        expectedResult[1].equals(realResult[1])
        );;
        game.moveCurrentPlayer(3, true);

        expectedResult = new String[] { "5", "5" };
        realResult = game.getCurrentPlayerEnergyInfo(5);
        assertTrue(
                expectedResult[0].equals(realResult[0]) &&
                        expectedResult[1].equals(realResult[1])
        );
    }

    @Test
    public void test_01_GetPlayersInfo() {
        // Testing with 2 players
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);

        String[][] realResult = game.getPlayersInfo();

        boolean hasPlayer1 = false;
        boolean hasPlayer2 = false;
        for (String[] player : realResult) {
            // Checks for player1
            if (
                player[0].equals("1")
                && player[1].equals("Player 1")
                && player[2].equals("L")
                && player[3].equals("80")
                && player[4].equals("4..6")
            ) {
                hasPlayer1 = true;
            }

            if (
                player[0].equals("3")
                && player[1].equals("Player 2")
                && player[2].equals("T")
                && player[3].equals("150")
                && player[4].equals("1..3")
            ) {
                hasPlayer2 = true;
            }
        }

        assertTrue(hasPlayer1 && hasPlayer2);
        // TODO: Improve this test
    }

    @Test
    public void test_02_GetPlayersInfo() {
        // Testing with 4 players
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" },
                { "4", "Player 3", "T"},
                { "22", "Player 4", "P"}
        };
        game.createInitialJungle(10, playersInfo);

        String[][] realResult = game.getPlayersInfo();

        boolean hasPlayer1 = false;
        boolean hasPlayer2 = false;
        boolean hasPlayer3 = false;
        boolean hasPlayer4 = false;
        for (String[] player : realResult) {
            // Checks for player1
            if (player[0].equals("1") && player[1].equals("Player 1") && player[2].equals("L")
                    && player[3].equals("80") && player[4].equals("4..6")
            ) {
                hasPlayer1 = true;
            }

            if (player[0].equals("3") && player[1].equals("Player 2") && player[2].equals("T")
                    && player[3].equals("150") && player[4].equals("1..3")
            ) {
                hasPlayer2 = true;
            }

            if (player[0].equals("4") && player[1].equals("Player 3") && player[2].equals("T")
                    && player[3].equals("150") && player[4].equals("1..3")
            ) {
                hasPlayer3 = true;
            }

            if (player[0].equals("22") && player[1].equals("Player 4") && player[2].equals("P")
                    && player[3].equals("70") && player[4].equals("5..6")
            ) {
                hasPlayer4 = true;
            }
        }

        assertTrue(hasPlayer1 && hasPlayer2 && hasPlayer3 && hasPlayer4);
        // TODO: Improve this test
    }

    @Test
    public void test_01_SortPlayersByID() {
        // Testing with already sorted ArrayList
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" },
                { "4", "Player 3", "T"},
                { "22", "Player 4", "P"}
        };
        game.createInitialJungle(10, playersInfo);
        game.sortPlayersByID();
        ArrayList<Player> players = game.getPlayers();

        assertTrue(
                players.get(0).getID() == 1
                        && players.get(1).getID() == 3
                        && players.get(2).getID() == 4
                        && players.get(3).getID() == 22
        );
    }

    @Test
    public void test_02_SortPlayersByID() {
        // Testing with unsorted ArrayList
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "22", "Player 4", "P"},
                { "1", "Player 1", "L" },
                { "4", "Player 3", "T"},
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        game.sortPlayersByID();
        ArrayList<Player> players = game.getPlayers();

        assertTrue(
                players.get(0).getID() == 1
                        && players.get(1).getID() == 3
                        && players.get(2).getID() == 4
                        && players.get(3).getID() == 22
        );
    }

    @Test
    public void test_01_MoveCurrentPlayer() {
        // Testing with invalid number of squares
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        ArrayList<Player> players = game.getPlayers();

        assertEquals(1, players.get(0).getCurrentMapPosition());
        assertEquals(MovementResultCode.INVALID_MOVEMENT, game.moveCurrentPlayer(7, false).code());
        assertEquals(1, players.get(0).getCurrentMapPosition());
    }

    @Test
    public void test_02_MoveCurrentPlayer() {
        // Testing bypass validation
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        ArrayList<Player> players = game.getPlayers();

        assertEquals(1, players.get(0).getCurrentMapPosition());
        assertEquals(MovementResultCode.VALID_MOVEMENT, game.moveCurrentPlayer(7, true).code());
        assertEquals(8, players.get(0).getCurrentMapPosition());
    }

    @Test
    public void test_03_MoveCurrentPlayer() {
        // Testing valid moves
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        ArrayList<Player> players = game.getPlayers();

        // Moving player with ID 1
        assertEquals(1, players.get(0).getCurrentMapPosition());
        assertEquals(MovementResultCode.VALID_MOVEMENT, game.moveCurrentPlayer(5, false).code());
        // Getting current player position from player object
        assertEquals(6, players.get(0).getCurrentMapPosition());
        // Checking if player was removed from the previous cell
        assertFalse(game.getMap().getMapCell(1).hasPlayerID(1));
        // Checking if player was added to the correct cell
        assertTrue(game.getMap().getMapCell(6).hasPlayerID(1));
    }

    @Test
    public void test_04_MoveCurrentPlayer() {
        // Testing with a number of squares that exceed the map limits (with bypassValidation)
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        ArrayList<Player> players = game.getPlayers();

        // Moving player with ID 1
        assertEquals(1, players.get(0).getCurrentMapPosition());
        assertEquals(MovementResultCode.VALID_MOVEMENT, game.moveCurrentPlayer(11, true).code());
        // Getting current player position from player object
        assertEquals(10, players.get(0).getCurrentMapPosition());
        // Checking if player was removed from the previous cell
        assertFalse(game.getMap().getMapCell(1).hasPlayerID(1));
        // Checking if player was added to the correct cell (last one in this case)
        assertTrue(game.getMap().getMapCell(10).hasPlayerID(1));
    }

    @Test
    public void test_05_MoveCurrentPlayer() {
        // Testing with not enough energy
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        ArrayList<Player> players = game.getPlayers();

        // Moving player with ID 1
        assertEquals(1, players.get(0).getCurrentMapPosition());
        assertEquals(MovementResultCode.VALID_MOVEMENT, game.moveCurrentPlayer(4, false).code());
        assertEquals(5, players.get(0).getCurrentMapPosition());
        assertEquals(MovementResultCode.VALID_MOVEMENT, game.moveCurrentPlayer(2, false).code());
        players.get(0).setEnergy(1);
        assertEquals(MovementResultCode.NO_ENERGY, game.moveCurrentPlayer(4, false).code());
    }

    @Test
    public void test_06_MoveCurrentPlayer() {
        // Testing with backwards movements
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        assertEquals(1, player1.getCurrentMapPosition());
        game.moveCurrentPlayer(-5, false);
        assertEquals(1, player1.getCurrentMapPosition());

        assertEquals(1, player2.getCurrentMapPosition());
        game.moveCurrentPlayer(-2, false);
        assertEquals(1, player2.getCurrentMapPosition());
    }

    @Test
    public void test_07_MoveCurrentPlayer() {
        // Testing movement by a player in the same cell twice
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);
        MapCell cell = game.getMap().getMapCell(5);
        cell.addPlayer(1);

        assertNotNull(game.moveCurrentPlayer(4, true));
    }

    @Test
    public void test_01_Energy() {
        // Testing energy gain by consuming food
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        String[][] foodsInfo = new String[][] {
                { "a", "3" },
                { "b", "6" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        assertEquals(80, player1.getEnergy());
        game.moveCurrentPlayer(2, true);
        // 80 (energy) - 2 (move) - 2 (move) + 15 (water) = 91
        assertEquals(91, player1.getEnergy());

        assertEquals(150, player2.getEnergy());
        game.moveCurrentPlayer(5, true);
        // 150 (energy) - 5 * 1 (move) + 40 (bananas) = 185
        assertEquals(185, player2.getEnergy());
    }

    @Test
    public void test_02_Energy() {
        // Testing energy gain by consuming water and grass
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "E" },
        };
        String[][] foodsInfo = new String[][] {
                { "a", "3" },
                { "e", "4" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        assertEquals(80, player1.getEnergy());
        game.moveCurrentPlayer(2, true);
        // 80 (energy) - 2 (move) - 2 (move) + 15 (water) = 91
        assertEquals(91, player1.getEnergy());

        assertEquals(180, player2.getEnergy());
        game.moveCurrentPlayer(3, true);
        // 180 (energy) - 12 (move) + 20 (grass) = 192
        assertEquals(188, player2.getEnergy());
    }

    @Test
    public void test_03_Energy() {
        // Testing energy gain by consuming more than 3 bananas
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" },
        };
        String[][] foodsInfo = new String[][] {
                { "b", "3" },
                { "b", "5" },
                { "b", "7" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        game.moveCurrentPlayer(3, true);
        assertEquals(150, player2.getEnergy());
        game.moveCurrentPlayer(2, false);
        // 150 (energy) - 1 (move) - 1 (move) + 40 (banana) = 188
        assertEquals(188, player2.getEnergy());

        game.moveCurrentPlayer(2, true);
        game.moveCurrentPlayer(2, false);
        // 188 (energy) - 1 (move) - 1 (move) - 40 (banana) = 146
        assertEquals(146, player2.getEnergy());

        game.moveCurrentPlayer(2, true);
        game.moveCurrentPlayer(2, false);
        // 146 (energy) - 1 (move) - 1 (move) - 40 (banana) = 104
        assertEquals(104, player2.getEnergy());
    }

    @Test
    public void test_04_Energy() {
        // Testing energy gain by moving backwards
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" },
        };
        String[][] foodsInfo = new String[][] {
                { "b", "3" },
                { "b", "5" },
                { "b", "7" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        assertEquals(80, player1.getEnergy());
        game.moveCurrentPlayer(-5, false);
        // 80 (energy) - 10 (move) = 70
        assertEquals(70, player1.getEnergy());
    }

    @Test
    public void test_05_Energy() {
        // Testing energy gain by staying in place
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" },
        };
        String[][] foodsInfo = new String[][] {
                { "b", "3" },
                { "b", "5" },
                { "b", "7" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        assertEquals(80, player1.getEnergy());
        game.moveCurrentPlayer(0, false);
        assertEquals(90, player1.getEnergy());

        assertEquals(150, player2.getEnergy());
        game.moveCurrentPlayer(0, false);
        assertEquals(155, player2.getEnergy());
    }

    @Test
    public void test_06_Energy() {
        // Testing if energy exceeds 200 by staying in food cell
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "E" },
                { "3", "Player 2", "T" },
        };
        String[][] foodsInfo = new String[][] {
                { "b", "3" },
                { "b", "5" },
                { "b", "7" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);

        assertEquals(180, player1.getEnergy());
        game.moveCurrentPlayer(0, false);
        assertEquals(190, player1.getEnergy());

        game.moveCurrentPlayer(0, false);

        game.moveCurrentPlayer(0, false);
        assertEquals(200, player1.getEnergy());

        game.moveCurrentPlayer(0, false);

        game.moveCurrentPlayer(0, false);
        assertEquals(200, player1.getEnergy());

        game.moveCurrentPlayer(0, false);
        assertEquals(200, player1.getEnergy());
    }

    @Test
    public void test_07_Energy() {
        // Testing if energy exceeds 200 by moving
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "E" },
                { "3", "Player 2", "T" },
        };
        String[][] foodsInfo = new String[][] {
                { "b", "2" },
                { "a", "3" },
                { "a", "4" },
                { "a", "5" },
                { "a", "6" },
                { "a", "7" },
                { "a", "8" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);

        assertEquals(180, player1.getEnergy());
        game.moveCurrentPlayer(1, false);
        assertEquals(200, player1.getEnergy());

        game.moveCurrentPlayer(1, false);

        game.moveCurrentPlayer(1, false);
        assertEquals(200, player1.getEnergy());

        game.moveCurrentPlayer(1, false);

        game.moveCurrentPlayer(1, false);
        assertEquals(200, player1.getEnergy());

        game.moveCurrentPlayer(1, false);
        assertEquals(200, player1.getEnergy());
    }

    @Test
    public void test_08_Energy() {
        // Testing energy gain after consuming spoiled meat
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "Z" },
        };
        String[][] foodsInfo = new String[][] {
                { "c", "8" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        // Makes 12 plays
        for (int i = 0; i < 11; i++) {
             game.moveCurrentPlayer(1, true);
        }

        assertEquals(60, player2.getEnergy());
        game.moveCurrentPlayer(1, true);
        assertEquals(58, player2.getEnergy());

        assertEquals(68, player1.getEnergy());
        game.moveCurrentPlayer(1, true);
        assertEquals(33, player1.getEnergy());

        assertEquals(58, player2.getEnergy());
        game.moveCurrentPlayer(1, true);
        assertEquals(28, player2.getEnergy());
    }

    @Test
    public void test_09_Energy() {
        // Testing energy to check if it goes below 0
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "L" },
        };
        String[][] foodsInfo = new String[][] {
                { "e", "2" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        game.moveCurrentPlayer(1, true);
        game.moveCurrentPlayer(1, true);

        for (int i = 0; i < 20; i++) {
            game.moveCurrentPlayer(0, false);
        }

        assertEquals(0, player1.getEnergy());
        assertEquals(0, player2.getEnergy());
    }

    @Test
    public void test_10_Energy() {
        // Testing energy gain by consuming Magic Mushroom
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "Z" },
        };
        String[][] foodsInfo = new String[][] {
                { "m", "4" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        MagicMushrooms magicMushroom = (MagicMushrooms)game.getMap().getMapCell(4).getFoodItem();
        int magicNumber = magicMushroom.getMagicNumber();
        assertTrue(magicMushroom.canBeConsumedBySpecies(player1.getSpecies()));
        assertTrue(magicMushroom.canBeConsumedBySpecies(player2.getSpecies()));

        assertEquals(1, game.getCurrentPlay());
        game.moveCurrentPlayer(3, true);
        assertEquals(2, game.getCurrentPlay());
        game.moveCurrentPlayer(3, true);

        // 80 - 6 (move) - (74 * (magicNumber / 100))
        assertEquals((74 - (int)(74 * (magicNumber / 100.0f))), player1.getEnergy());
        // 70 - 6 (move) - (64 * (magicNumber / 100))
        assertEquals((64 + (int)(64 * (magicNumber / 100.0f))), player2.getEnergy());
    }

    @Test
    public void test_11_Energy() {
        // Testing energy gain by consuming multiple foods
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "Z" },
                { "8", "Player 3", "E" }
        };
        String[][] foodsInfo = new String[][] {
                { "c", "4" },
                { "a", "6" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);

        Meat meat = (Meat) game.getMap().getMapCell(4).getFoodItem();
        assertTrue(meat.canBeConsumedBySpecies(player1.getSpecies()));
        assertFalse(meat.canBeConsumedBySpecies(player3.getSpecies()));

        assertEquals(1, game.getCurrentPlay());
        game.moveCurrentPlayer(3, true);
        // 80 - 6 (move) - 50 (meat) = 124
        assertEquals(124, player1.getEnergy());

        assertEquals(2, game.getCurrentPlay());
        game.moveCurrentPlayer(5, true);
        // 70 - 10 (move) + 12 (water) = 72
        assertEquals(72, player2.getEnergy());
    }

    @Test
    public void test_12_Energy() {
        // Testing if energy does not drop below 0 when constantly moving
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "Z" },
        };
        game.createInitialJungle(50, playersInfo);
        ArrayList<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        for (int i = 0; i < 84; i++) {
            game.moveCurrentPlayer(1, true);
        }

        assertEquals(0, player1.getEnergy());
        assertEquals(0, player2.getEnergy());
    }

    @Test
    public void test_01_loadSavedData() {
        // Testing Bananas' 'loadSavedData'
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        String[][] foodsInfo = new String[][] {
                { "b", "3" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        MapCell cell = game.getMap().getMapCell(3);
        Bananas bananas = (Bananas)cell.getFoodItem();
        assertEquals(3, bananas.getConsumableUnits());
        bananas.loadSavedData(2);
        assertEquals(2, bananas.getConsumableUnits());
        assertEquals("Bananas : 2 : + 40 energia", bananas.getTooltip());
    }

    @Test
    public void test_02_loadSavedData() {
        // Testing MagicMushrooms' 'loadSavedData'
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        String[][] foodsInfo = new String[][] {
                { "m", "3" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);
        MapCell cell = game.getMap().getMapCell(3);
        MagicMushrooms mushrooms = (MagicMushrooms)cell.getFoodItem();
        mushrooms.loadSavedData(13);
        assertEquals("Cogumelo Magico : +- 13% energia", mushrooms.getTooltip());
    }

    @Test
    public void test_01_UpdateSpoilStatusAndTooltip() {
        // Testing Meat's 'updateSpoilStatusAndTooltip'
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        String[][] foodsInfo = new String[][] {
                { "c", "3" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);

        Meat meat = (Meat) game.getMap().getMapCell(3).getFoodItem();
        game.moveCurrentPlayer(1, true);
        game.getSquareInfo(3);
        assertEquals("Carne : + 50 energia : 1 jogadas", meat.getTooltip());
        meat.updateSpoilStatusAndTooltip(14);
        assertEquals("Carne toxica", meat.getTooltip());
    }

    @Test
    public void test_01_FullGame() {
        // Testing player with the lowest ID gets to the finish line
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);

        game.moveCurrentPlayer(4, true);
        assertFalse(game.isGameOver());
        assertNull(game.getWinnerInfo());
        game.moveCurrentPlayer(3, true);
        assertFalse(game.isGameOver());
        assertNull(game.getWinnerInfo());
        game.moveCurrentPlayer(6, true);
        assertTrue(game.isGameOver());
        assertEquals(1, Integer.parseInt(game.getWinnerInfo()[0]));
    }

    @Test
    public void test_02_FullGame() {
        // Testing player with the highest ID getting to the finish line
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        game.createInitialJungle(10, playersInfo);
        String[] realResult = game.getCurrentPlayerInfo();
        String[] expectedResult = new String[] { "1", "Player 1", "L", "80", "4..6" };

        assertTrue(
                expectedResult[0].equals(realResult[0])
                        && expectedResult[1].equals(realResult[1])
                        && expectedResult[2].equals(realResult[2])
                        && expectedResult[3].equals(realResult[3])
        );
        game.moveCurrentPlayer(2, true);
        assertFalse(game.isGameOver());
        assertNull(game.getWinnerInfo());
        game.moveCurrentPlayer(5, true);
        assertFalse(game.isGameOver());
        assertNull(game.getWinnerInfo());
        game.moveCurrentPlayer(6, true);
        assertFalse(game.isGameOver());
        assertNull(game.getWinnerInfo());
        game.moveCurrentPlayer(5, true);
        assertTrue(game.isGameOver());
        assertEquals(3, Integer.parseInt(game.getWinnerInfo()[0]));
    }

    @Test
    public void test_03_FullGame() {
        // Testing win by gap between 2 first player bigger than half the map
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" },
                { "8", "Player 3", "Z" }
        };
        game.createInitialJungle(12, playersInfo);

        assertEquals(1, game.getCurrentPlay());
        game.moveCurrentPlayer(2, true);
        game.moveCurrentPlayer(1, true);
        game.moveCurrentPlayer(2, true);
        game.moveCurrentPlayer(3, true);
        game.moveCurrentPlayer(1, true);
        game.moveCurrentPlayer(1, true);
        game.moveCurrentPlayer(5, true);
        assertEquals(8, game.getCurrentPlay());

        assertTrue(game.isGameOver());
        assertEquals(8, Integer.parseInt(game.getWinnerInfo()[0]));
        ArrayList<String> results = game.getGameResults();

        // Checks game results
        assertTrue(
                results.get(0).equals("#1 Player 3, Tarzan, 4, 3, 0")
                        && results.get(1).equals("#2 Player 1, Leao, 11, 10, 0")
                        && results.get(2).equals("#3 Player 2, Tartaruga, 3, 2, 0")
        );
    }

    @Test
    public void test_01_SaveGame() {
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        String[][] foodsInfo = new String[][] {
                { "a", "3" },
                { "b", "7" },
                { "m", "9" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);

        File saveFile = new File("test-files/test_save_game");
        assertTrue(game.saveGame(saveFile));
    }

    @Test
    public void test_02_SaveGame() {
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L" },
                { "3", "Player 2", "T" }
        };
        String[][] foodsInfo = new String[][] {
                { "a", "3" },
                { "b", "7" },
                { "m", "9" }
        };
        game.createInitialJungle(10, playersInfo, foodsInfo);

        File saveFile = new File("");
        assertFalse(game.saveGame(saveFile));
    }

    @Test
    public void test_01_LoadGame() {
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "5", "Player 1", "E" },
                { "9", "Player 2", "L" }
        };
        game.createInitialJungle(33, playersInfo);

        File saveFile = new File("test-files/load_game");
        assertTrue(game.loadGame(saveFile));
    }

    @Test
    public void test_02_LoadGame() {
        GameManager game = new GameManager();
        String[][] playersInfo = new String[][] {
                { "5", "Player 1", "E" },
                { "9", "Player 2", "L" }
        };
        game.createInitialJungle(33, playersInfo);

        File saveFile = new File("");
        assertFalse(game.loadGame(saveFile));
    }

    @Test
    public void test_01_getSpecies() {
        GameManager game = new GameManager();
        String[][] species = game.getSpecies();
        boolean hasElephant = false;
        boolean hasLion = false;
        boolean hasTurtle = false;
        boolean hasBird = false;
        boolean hasTarzan = false;

        for (String[] item : species) {
            if (item[0].equals("E") && item[1].equals("Elefante")) {
                hasElephant = true;
            }
            if (item[0].equals("L") && item[1].equals("Leão")) {
                hasLion = true;
            }
            if (item[0].equals("T") && item[1].equals("Tartaruga")) {
                hasTurtle = true;
            }
            if (item[0].equals("P") && item[1].equals("Pássaro")) {
                hasBird = true;
            }
            if (item[0].equals("Z") && item[1].equals("Tarzan")) {
                hasTarzan = true;
            }
        }

        assertTrue(
                hasElephant
                && hasLion
                && hasTurtle
                && hasBird
                && hasTarzan
        );
    }

    @Test
    public void test_01_getFoodTypes() {
        GameManager game = new GameManager();
        String[][] foods = game.getFoodTypes();
        boolean hasGrass = false;
        boolean hasWater = false;
        boolean hasBananas = false;
        boolean hasMeat = false;
        boolean hasMagicMushrooms = false;

        for (String[] item : foods) {
            if (item[0].equals("a") && item[1].equals("Agua")) {
                hasWater = true;
            }
            if (item[0].equals("e") && item[1].equals("Erva")) {
                hasGrass = true;
            }
            if (item[0].equals("b") && item[1].equals("Bananas")) {
                hasBananas = true;
            }
            if (item[0].equals("c") && item[1].equals("Carne")) {
                hasMeat = true;
            }
            if (item[0].equals("m") && item[1].equals("Cogumelo Magico")) {
                hasMagicMushrooms = true;
            }
        }

        assertTrue(
                hasWater
                        && hasGrass
                        && hasBananas
                        && hasMeat
                        && hasMagicMushrooms
        );
    }

    @Test
    public void test_01_InitializationError() {
        InitializationError error = new InitializationError("Test error");
        assertEquals("Test error", error.getMessage());
    }

    @Test
    public void test_Credits() {
        GameManager game = new GameManager();
        game.getAuthorsPanel();
        game.updateCreditsImagePath("");
        game.getAuthorsPanel();
    }

    @Test
    public void test_WhoIsTaborda() {
        GameManager game = new GameManager();
        assertEquals("professional wrestling", game.whoIsTaborda());
    }
}
