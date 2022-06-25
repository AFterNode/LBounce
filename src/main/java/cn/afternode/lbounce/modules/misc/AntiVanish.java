package cn.afternode.lbounce.modules.misc;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

@ModuleInfo(name = "AntiVanish", description = "探测隐身玩家", category = ModuleCategory.MISC)
public class AntiVanish extends Module {
    private final IntegerValue delay = new IntegerValue("Detect Delay", 5, 1, 120);
    private final IntegerValue vl = new IntegerValue("Level", 3, 1, 10);

    private final MSTimer timer = new MSTimer();

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (!timer.hasTimePassed(delay.get() * 1000)) return;
        detect();
        timer.reset();
    }

    private void detect() {
        EntityPlayer[] players = mc.theWorld.playerEntities.toArray(new EntityPlayer[0]);
        int count = 0;
        for (EntityPlayer p: players) {
            if (!p.isInvisible()) continue;
            if (AntiBot.isBot(p)) continue;
            count ++;
        }

        if (count >= vl.get()) {
            ClientUtils.displayChatMessage("Invisible players: " + count);
        }
    }
}
