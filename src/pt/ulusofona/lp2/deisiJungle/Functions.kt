package pt.ulusofona.lp2.deisiJungle

enum class CommandType {
    GET,
    POST
}

fun router():Function1<CommandType,Function2<GameManager,List<String>,String?>> {
    return ::command
}

fun command(typeCommand: CommandType):Function2<GameManager,List<String>,String?>{
    if (typeCommand == CommandType.POST){
        return ::postMove
    }
    return ::getCommand
}

fun getCommand(manager: GameManager,args: List<String>):String?{
    when(args[1]){
        "PLAYER_INFO" -> {
            return getPlayerInfo(manager,args)
        }
        "PLAYERS_BY_SPECIE" -> {
            return getPlayerBySpecie(manager,args)
        }
        "MOST_TRAVELED" -> {
            return getMostTraveled(manager,args)
        }
        "TOP_ENERGETIC_OMNIVORES" -> {
            return getTopEnergeticOmnivores(manager,args)
        }
        "CONSUMED_FOODS" -> {
            return getConsumedFoods(manager,args)

        }
        else ->{
            return null
        }
    }
}

fun getPlayerInfo(manager: GameManager,args: List<String>):String? {
    return null
}

fun getPlayerBySpecie(manager: GameManager,args: List<String>):String? {
    return null
}

fun getMostTraveled(manager: GameManager,args: List<String>):String? {
    return null
}

fun getTopEnergeticOmnivores(manager: GameManager,args: List<String>):String? {
    return null
}

fun getConsumedFoods(manager: GameManager,args: List<String>):String? {
    return null
}

fun postMove(manager: GameManager,args: List<String>):String? {
    return null
}
