package pt.ulusofona.lp2.deisiJungle

import java.lang.NumberFormatException

enum class CommandType {
    GET,
    POST
}

// Routes the input to the correct function
fun router(): Function1<CommandType, Function2<GameManager, List<String>, String?>> {
    return ::command
}

// Separates 'GET' and 'POST' queries
fun command(typeCommand: CommandType): Function2<GameManager, List<String>, String?>{
    return if (typeCommand == CommandType.GET) ::getCommand else ::postCommand
}

// Executes 'GET' commands
fun getCommand(manager: GameManager, args: List<String>): String? {
    if (args.isNotEmpty()) {
        return when(args[1]){
            "PLAYER_INFO" -> getPlayerInfo(manager,args)
            "PLAYERS_BY_SPECIE" -> getPlayersBySpecie(manager,args)
            "MOST_TRAVELED" -> getMostTraveled(manager,args)
            "TOP_ENERGETIC_OMNIVORES" -> getTopEnergeticOmnivores(manager,args)
            "CONSUMED_FOODS" -> getConsumedFoods(manager,args)
            else -> null
        }
    }
    return null
}

// Executes 'POST' commands
fun postCommand(manager: GameManager, args: List<String>): String? {
    if (args.isNotEmpty()) {
        return when (args[1]) {
            "MOVE" -> postMove(manager, args)
            else -> null
        }
    }
    return null
}

fun getPlayerInfo(manager: GameManager, args: List<String>): String? {
    // Checks if the number of inputs is correct
    if (args.size < 3) {
        return null
    }
    val name = args[2]
    val result = manager.players
        .filter { it.name == name }
        .map { "${it.id} | ${it.name} | ${it.species.name} | ${it.energy} | ${it.currentMapPosition}" }

    return if (result.isEmpty()) "Inexistent player" else result[0]
}

fun getPlayersBySpecie(manager: GameManager, args: List<String>): String? {
    // Checks if the number of inputs is correct
    if (args.size < 3) {
        return null
    }
    val speciesID = args[2][0]

    return manager.players
        .filter { it.species.id == speciesID }.joinToString(",") { it.name }
}

fun getMostTraveled(manager: GameManager, args: List<String>): String? {
    var result = manager.players
        .sortedByDescending { it.distanceCovered }
        .joinToString("\n") { "${it.name}:${it.species.id}:${it.distanceCovered}" }

    result += "\nTotal:"
    result += manager.players.sumOf { it.distanceCovered }
    return result
}

fun getTopEnergeticOmnivores(manager: GameManager, args: List<String>): String? {
    // Checks if the number of inputs is correct
    if (args.size < 3) {
        return null
    }
    val maxResults: Number
    try {
        maxResults = args[2].toInt()
    } catch (e: NumberFormatException) {
        return null
    }

    return manager.players
        .filter { it.species.diet == DietType.OMNIVORE }
        .sortedByDescending { it.energy }
        .take(maxResults)
        .joinToString("\n") { "${it.name}:${it.energy}" }
}

fun getConsumedFoods(manager: GameManager, args: List<String>): String? {
    return manager.map.mapCells
        .filter { it.foodItem != null }
        .map { it.foodItem }
        .filter { it.getConsumedCount() > 0 }
        .sortedBy { it.getName() }.joinToString("\n") { "${it.name}" }
}

fun postMove(manager: GameManager, args: List<String>): String? {
    // Checks if the number of inputs is correct
    if (args.size < 3) {
        return null
    }
    val numberOfPositionToMove: Number
    try {
        numberOfPositionToMove = args[2].toInt()
    } catch (e: NumberFormatException) {
        return null
    }
    val movementResult = manager.moveCurrentPlayer(numberOfPositionToMove, true)

    return when (movementResult.code) {
        MovementResultCode.INVALID_MOVEMENT -> "Movimento invalido"
        MovementResultCode.NO_ENERGY -> "Sem energia"
        MovementResultCode.CAUGHT_FOOD -> "Apanhou comida"
        else -> "OK"
    }
}
