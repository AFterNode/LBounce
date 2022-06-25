package cn.afternode.lbounce.modules.fun;

import net.ccbluex.liquidbounce.chat.Client;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.command.commands.HelpCommand;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "MurderMystery", description = "自动检查杀手", category = ModuleCategory.FUN)
public class MurderMystery extends Module {
    public static BoolValue callOut = new BoolValue("Call Out", false);
    public static IntegerValue delay = new IntegerValue("Detect Delay", 10, 1, 60);

    private final MSTimer timer = new MSTimer();

    @EventTarget
    public void onTick(TickEvent event) {
        if (!timer.hasTimePassed(delay.get() * 1000)) return;
        MMThread thread = new MMThread();
        thread.start();
        timer.reset();
    }
}

class MMThread extends Thread{
    public void run() {
        detect();
    }

    public void detect() {
        WorldClient worldClient =  MurderMystery.mc.theWorld;
        List<String> murder = new ArrayList<>();
        List<String> hunter = new ArrayList<>();
        for (EntityPlayer e: worldClient.playerEntities.toArray(new EntityPlayer[]{})) {
            if (e == MurderMystery.mc.thePlayer) continue;
            if (AntiBot.isBot(e)) continue;
            if (e.isInvisible()) continue;
            for (ItemStack is: e.getInventory()) {
                if (is.getItem() instanceof ItemSword) {
                    murder.add(e.getName());
                    break;
                }
            }
        }

        StringBuilder murders = new StringBuilder();
        murders.append("Find murders: ");
        murders.append(murder.get(0));
        for(String m: murder) {
            murders.append(", ").append(m);
        }
        ClientUtils.displayChatMessage(murders.toString());

        if (MurderMystery.callOut.get()) {
            StringBuilder sb = new StringBuilder();
            for(String n: murder.toArray(new String[]{})) {
                sb.append(n).append(", ");
            }
            sb.append("is the murder");
            MurderMystery.mc.thePlayer.sendChatMessage(sb.toString());
        }
    }
}
