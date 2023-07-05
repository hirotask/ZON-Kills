package com.github.hirotask.usecase.impl

import com.github.hirotask.domain.ZONPlayerService
import com.github.hirotask.usecase.AddZombieKillsUseCase
import com.github.hirotask.usecase.ZONPlayerActionUseCase
import org.bukkit.entity.Player
import javax.inject.Inject

class AddZombieKillsUseCaseImpl @Inject constructor(
        private val zonPlayerService: ZONPlayerService,
        private val zonPlayerActionUseCase: ZONPlayerActionUseCase
) : AddZombieKillsUseCase{
    override fun invoke(player: Player, value : Int): Int {
        var result = 0
        zonPlayerActionUseCase.invoke(player) {
            result = zonPlayerService.addZombieKills(zonPlayer = it, value)
        }
        return result
    }
}