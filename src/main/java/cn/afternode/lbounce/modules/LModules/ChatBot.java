package cn.afternode.lbounce.modules.LModules;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

@ModuleInfo(name = "ChatBot", description = "自动在当前世界中寻找玩家发送私聊消息", category = ModuleCategory.LBounce)
public class ChatBot extends Module {
    private final TextValue content = new TextValue("Content", "LBounce >>https://afternode.cn/<<");
    private final IntegerValue delay = new IntegerValue("Delay", 60, 10, 3600);

    private final MSTimer timer = new MSTimer();

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (!timer.hasTimePassed(delay.get() * 1000)) return;

        EntityPlayer[] players = mc.theWorld.playerEntities.toArray(new EntityPlayer[]{});
        Random random = new Random();

        String playerName = players[random.nextInt(players.length)].getName();
        mc.thePlayer.sendChatMessage("/tell " + playerName + " " + content.get());
    }
}
