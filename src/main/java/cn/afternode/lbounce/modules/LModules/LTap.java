package cn.afternode.lbounce.modules.LModules;

import cn.afternode.lbounce.variables.Bypass;
import cn.afternode.lbounce.variables.LMessages;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import org.lwjgl.input.Keyboard;

import java.util.Random;

@ModuleInfo(name = "LTap", description = "LTap", category = ModuleCategory.LBounce)
public class LTap extends Module {
    private final String[] bypassList = {"Off", "HuaYuTing"};
    private final ListValue bypass = new ListValue("ChatBypass", bypassList, bypassList[0]);

    @EventTarget
    public void onKey(KeyEvent event) {
        if (event.getKey() != Keyboard.KEY_L) return;

        Random random = new Random();
        String content = LMessages.LMessages[random.nextInt(LMessages.LMessages.length)];

        if (bypass.get().equals("HuaYuTing")){
            for (String tmp: Bypass.HuaYuTing.keySet().toArray(new String[]{})) {
                content = content.replace(tmp, Bypass.HuaYuTing.get(tmp));
            }
        }

        mc.thePlayer.sendChatMessage(content);
    }
}
