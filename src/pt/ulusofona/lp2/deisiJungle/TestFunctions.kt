package pt.ulusofona.lp2.deisiJungle

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.NumberFormatException
import kotlin.math.exp

class TestFunctions {
    @Test
    fun test_01_GET_COMMAND() {
        // Testing with empty arg list
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        assertNull(router.invoke(CommandType.GET).invoke(game, listOf()))
    }

    @Test
    fun test_02_GET_COMMAND() {
        // Testing with a non-existent query
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        assertNull(router.invoke(CommandType.GET).invoke(game, listOf("LULZ")))
    }

    @Test
    fun test_01_POST_COMMAND() {
        // Testing with empty arg list
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        assertNull(router.invoke(CommandType.POST).invoke(game, listOf()))
    }

    @Test
    fun test_02_POST_COMMAND() {
        // Testing with empty non-existent query
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        assertNull(router.invoke(CommandType.POST).invoke(game, listOf("LULZ")))
    }

    @Test
    fun test_01_GET_PLAYER_INFO() {
        // Testing with valid player
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        val expectedResult = "3 | Player 1 | Leao | 80 | 1"
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("PLAYER_INFO", "Player 1"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_02_GET_PLAYER_INFO() {
        // Testing with nonexistent player
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        val expectedResult = "Inexistent player"
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("PLAYER_INFO", "Player 3"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_03_GET_PLAYER_INFO() {
        // Testing without passing player name
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("PLAYER_INFO"))
        assertNull(realResult)
    }

    @Test
    fun test_01_GET_PLAYERS_BY_SPECIE() {
        // Testing without passing player name
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
            arrayOf("8", "Player 3", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        val expectedResult = "Player 3,Player 2"
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("PLAYERS_BY_SPECIE", "T"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_02_GET_PLAYERS_BY_SPECIE() {
        // Testing with nonexistent species
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        val expectedResult = ""
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("PLAYERS_BY_SPECIE", "E"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_03_GET_PLAYERS_BY_SPECIE() {
        // Testing with invalid species ID
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        val expectedResult = ""
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("PLAYERS_BY_SPECIE", "H"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_04_GET_PLAYERS_BY_SPECIE() {
        // Testing with invalid number of args
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        assertNull(router.invoke(CommandType.GET).invoke(game, listOf("PLAYERS_BY_SPECIE")))
    }

    @Test
    fun test_01_GET_MOST_TRAVELED() {
        // Testing with valid data
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
            arrayOf("8", "Player 3", "Z")
        )
        game.createInitialJungle(10, playersInfo)
        game.players[0].increaseDistanceCovered(10)
        game.players[1].increaseDistanceCovered(23)
        game.players[2].increaseDistanceCovered(8)

        val router = router()
        val expectedResult = "Player 2:T:23" +
                "\nPlayer 1:L:10" +
                "\nPlayer 3:Z:8" +
                "\nTotal:41"
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("MOST_TRAVELED"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_02_GET_MOST_TRAVELED() {
        // Testing different player with same distance traveled
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
            arrayOf("8", "Player 3", "Z")
        )
        game.createInitialJungle(10, playersInfo)
        game.players[0].increaseDistanceCovered(5)
        game.players[1].increaseDistanceCovered(11)
        game.players[2].increaseDistanceCovered(11)

        val router = router()
        val expectedResult = "Player 2:T:11" +
                "\nPlayer 3:Z:11" +
                "\nPlayer 1:L:5" +
                "\nTotal:27"
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("MOST_TRAVELED"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_01_GET_TOP_ENERGETIC_OMNIVORES() {
        // Testing with valid data
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
            arrayOf("8", "Player 3", "Z")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        val expectedResult = "Player 2:150\nPlayer 3:70"
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("TOP_ENERGETIC_OMNIVORES", "3"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_02_GET_TOP_ENERGETIC_OMNIVORES() {
        // Testing with invalid number of args
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
            arrayOf("8", "Player 3", "Z")
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        assertNull(router.invoke(CommandType.GET).invoke(game, listOf("TOP_ENERGETIC_OMNIVORES")))
    }

    @Test
    fun test_01_GET_CONSUMED_FOODS() {
        // Testing with valid data
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
            arrayOf("8", "Player 3", "Z")
        )
        val foodsInfo = arrayOf(
            arrayOf("a", "3"),
            arrayOf("b", "6"),
            arrayOf("m", "8")
        )
        game.createInitialJungle(10, playersInfo, foodsInfo)
        game.map.getMapCell(3).foodItem.consumedCount = 4;
        game.map.getMapCell(6).foodItem.consumedCount = 2;
        game.map.getMapCell(8).foodItem.consumedCount = 1;
        val router = router()
        val expectedResult = "Agua\nBananas\nCogumelo Magico"
        val realResult = router.invoke(CommandType.GET).invoke(game, listOf("CONSUMED_FOODS"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_01_POST_MOVE() {
        // Testing with valid move
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
        )
        game.createInitialJungle(10, playersInfo)
        val router = router()
        val expectedResult = "OK"
        val realResult = router.invoke(CommandType.POST).invoke(game, listOf("MOVE", "7"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_02_POST_MOVE() {
        // Testing without energy
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
        )
        game.createInitialJungle(10, playersInfo)
        game.players[0].decreaseEnergy(70)
        val router = router()
        val expectedResult = "Sem energia"
        val realResult = router.invoke(CommandType.POST).invoke(game, listOf("MOVE", "7"))
        assertEquals(expectedResult, realResult)
    }

    @Test
    fun test_03_POST_MOVE() {
        // Testing with food
        val game = GameManager()
        val playersInfo = arrayOf(
            arrayOf("3", "Player 1", "L"),
            arrayOf("5", "Player 2", "T"),
        )
        val foodsInfo = arrayOf(
            arrayOf("a", "4"),
        )
        game.createInitialJungle(10, playersInfo, foodsInfo)
        val router = router()
        val expectedResult = "Apanhou comida"
        val realResult = router.invoke(CommandType.POST).invoke(game, listOf("MOVE", "3"))
        assertEquals(expectedResult, realResult)
    }
}