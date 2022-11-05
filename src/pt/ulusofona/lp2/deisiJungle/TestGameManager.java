package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameManager {
    @Test
    public void test_01_CreateInitialJungle() {
        // Testing 2 players with same ID
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "3", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        assertFalse(game.createInitialJungle(10, 20, playersInfo));
    }

    @Test
    public void test_02_CreateInitialJungle() {
        // Testing with empty/null player name
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "", "L"},
                { "3", "Player 2", "T"}
        };
        assertFalse(game.createInitialJungle(10, 20, playersInfo));

        playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", null, "T"}
        };
        assertFalse(game.createInitialJungle(10, 20, playersInfo));
    }

    @Test
    public void test_03_CreateInitialJungle() {
        // Testing with a non-existent species
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "Y"},
                { "3", "Player 2", "T"}
        };
        assertFalse(game.createInitialJungle(10, 20, playersInfo));
    }

    @Test
    public void test_04_CreateInitialJungle() {
        // Testing with invalid number of players
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "3", "Player 1", "T"}
        };
        assertFalse(game.createInitialJungle(10, 20, playersInfo));

        playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        assertTrue(game.createInitialJungle(10, 20, playersInfo));


        playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"},
                { "4", "Player 3", "T"}
        };
        assertTrue(game.createInitialJungle(10, 20, playersInfo));
    }

    @Test
    public void test_05_CreateInitialJungle() {
        // Testing with short number of positions
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        assertFalse(game.createInitialJungle(2, 20, playersInfo));
        assertFalse(game.createInitialJungle(3, 20, playersInfo));
        assertTrue(game.createInitialJungle(4, 20, playersInfo));
    }

    @Test
    public void test_01_GetPlayerIds() {
        // Testing with non-existent cell
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, 20, playersInfo);
        int[] expectedResult = new int[0];
        assertEquals(expectedResult.length, game.getPlayerIds(30).length);
    }

    @Test
    public void test_02_GetPlayerIds() {
        // Testing with different player IDs in the same cell
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"},
                { "4", "Player 3", "T"}
        };
        game.createInitialJungle(10, 20, playersInfo);

        // Adds player with ID 3 to cell with index 20
        MapCell cell = game.map.getMapCell(10);
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
    public void test_01_GetSquareInfo() {
        // Testing with non-existent cell
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, 20, playersInfo);
        int[] expectedResult = null;
        assertEquals(expectedResult, game.getSquareInfo(30));
        assertEquals(expectedResult, game.getSquareInfo(11));
    }

    @Test
    public void test_02_GetSquareInfo() {
        // Testing with empty cell with no players
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, 20, playersInfo);

        String[] expectedResult = new String[] { "blank.png", "Vazio", "" };
        String[] realResult = game.getSquareInfo(5);
        assertTrue(
                expectedResult[0].equals(realResult[0])
                && expectedResult[1].equals(realResult[1])
                && expectedResult[2].equals(realResult[2])
        );
    }

    @Test
    public void test_03_GetSquaresInfo() {
        // Testing with empty cell with players
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, 20, playersInfo);

        // Add players with ID 3 to cell 5
        MapCell cell = game.map.getMapCell(5);
        cell.addPlayer(3);
        String[] expectedResult = new String[] { "blank.png", "Vazio", "3" };
        String[] realResult = game.getSquareInfo(5);
        assertTrue(
                expectedResult[0].equals(realResult[0])
                        && expectedResult[1].equals(realResult[1])
                        && expectedResult[2].equals(realResult[2])
        );
    }

    @Test
    public void test_04_GetSquaresInfo() {
        // Testing with finish cell
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, 20, playersInfo);

        // Add players with ID 3 to cell 5
        MapCell cell = game.map.getMapCell(10);
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
    public void test_01_GetPlayerInfo() {
        // Testing with invalid ID
        GameManager game = new GameManager();

        String[][] playersInfo = new String[][] {
                { "1", "Player 1", "L"},
                { "3", "Player 2", "T"}
        };
        game.createInitialJungle(10, 20, playersInfo);

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
        game.createInitialJungle(10, 20, playersInfo);

        String[] expectedResult = new String[] { "3", "Player 2", "T", "20" };
        String[] realResult = game.getPlayerInfo(3);
        assertTrue(
                expectedResult[0].equals(realResult[0]) &&
                        expectedResult[1].equals(realResult[1]) &&
                        expectedResult[2].equals(realResult[2]) &&
                        expectedResult[3].equals(realResult[3])
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
        game.createInitialJungle(10, 20, playersInfo);

        String[][] realResult = game.getPlayersInfo();

        boolean hasPlayer1 = false;
        boolean hasPlayer2 = false;
        for (String[] player : realResult) {
            // Checks for player1
            if (player[0].equals("1") && player[1].equals("Player 1") && player[2].equals("L") && player[3].equals("20")) {
                hasPlayer1 = true;
            }

            if (player[0].equals("3") && player[1].equals("Player 2") && player[2].equals("T") && player[3].equals("20")) {
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
                { "22", "Player 4", "B"}
        };
        game.createInitialJungle(10, 20, playersInfo);

        String[][] realResult = game.getPlayersInfo();

        boolean hasPlayer1 = false;
        boolean hasPlayer2 = false;
        boolean hasPlayer3 = false;
        boolean hasPlayer4 = false;
        for (String[] player : realResult) {
            // Checks for player1
            if (player[0].equals("1") && player[1].equals("Player 1") && player[2].equals("L") && player[3].equals("20")) {
                hasPlayer1 = true;
            }

            if (player[0].equals("3") && player[1].equals("Player 2") && player[2].equals("T") && player[3].equals("20")) {
                hasPlayer2 = true;
            }

            if (player[0].equals("4") && player[1].equals("Player 3") && player[2].equals("T") && player[3].equals("20")) {
                hasPlayer3 = true;
            }

            if (player[0].equals("22") && player[1].equals("Player 4") && player[2].equals("B") && player[3].equals("20")) {
                hasPlayer4 = true;
            }
        }

        assertTrue(hasPlayer1 && hasPlayer2 && hasPlayer3 && hasPlayer4);
        // TODO: Improve this test
    }
}
