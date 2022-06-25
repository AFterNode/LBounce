package cn.afternode.lbounce.modules.player;

import cn.afternode.lbounce.variables.LMessages;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;

import java.util.Random;

@ModuleInfo(name = "AutoXNM", description = "自动骂人", category = ModuleCategory.LBounce)
public class AutoXNM extends Module {
    private final IntegerValue delay = new IntegerValue("Delay", 3, 1, 20);
    private final MSTimer timer = new MSTimer();
    private final String[] modes = {
            "Normal"
    };
    private final ListValue mode = new ListValue("Mode", modes, modes[0]);


    @EventTarget
    public void onUpdate(UpdateEvent event) {
       while (timer.hasTimePassed(delay.get()*1000)) {
           Random random = new Random();
           mc.thePlayer.sendChatMessage(LMessages.LMessages[random.nextInt(LMessages.LMessages.length)]);
           timer.reset();
       }
    }
}
