package com.github.hirotask.mc.listener

import com.github.hirotask.mc.Main
import com.github.hirotask.mc.event.ZombieDeathByPlayerEvent
import com.github.syari.spigot.api.event.events
import com.github.syari.spigot.api.sound.playSound
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Firework
import org.bukkit.inventory.ItemStack

/**
 * 本プラグインで実装したイベントに対するリスナー
 *
 * @property main
 */
class MyEventListener(private val main: Main) {

    fun register() {
        main.events {
            event<ZombieDeathByPlayerEvent> {
                val player = it.player
                val zonPlayer = main.zonPlayerService.getZONPlayer(player)

                val zombieKills = main.zonPlayerService.addZombieKills(zonPlayer, 1)

                if (zombieKills > 0 && zombieKills % 100 == 0) {
                    main.zonPlayerService.addStatusPoint(zonPlayer, 1)
                    val firework: Firework = zonPlayer.player.world.spawn(zonPlayer.player.location, Firework::class.java)
                    val data = firework.fireworkMeta.apply {
                        addEffect(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.GREEN).with(FireworkEffect.Type.BALL_LARGE).withFlicker().build())
                        power = 1
                    }
                    firework.fireworkMeta = data
                }

                when (zombieKills) {
                    50 -> {
                        player.sendMessage("50キル達成！")
                        player.inventory.addItem(ItemStack(Material.DIAMOND))
                        player.playSound(Sound.ENTITY_ENDER_DRAGON_DEATH, volume = 0.5F)
                    }
                }
            }
        }
    }
}
