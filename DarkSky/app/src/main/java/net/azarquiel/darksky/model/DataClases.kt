package net.azarquiel.darksky.model

data class Result(var currently: Currently, var daily: Daily)
data class Currently(var summary: String, var icon: String, var precipProbability:Float, var temperature:Float)
data class Daily(var data:List<Dia>)
data class Dia(var time:Long, var summary: String, var icon: String, var precipProbability:Float, var temperatureMax:Float, var temperatureMin:Float)
