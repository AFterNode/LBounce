package cn.afternode.lbounce.modules.LModules;

import cn.afternode.lbounce.utils.network;
import com.google.gson.Gson;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraftforge.event.ServerChatEvent;

import java.util.HashMap;

@ModuleInfo(name = "AutoGG", description = "AutoGG", category = ModuleCategory.LBounce)
public class AutoGG extends Module {
    private final BoolValue hitokoto = new BoolValue("UseHitokoto", false);
    private final TextValue content = new TextValue("Content", "gg");
    private final String[] victory = {"胜利",
    "victory"};
    public static String hitokoto_auotgg;

    @EventTarget
    public void onGameOver(ServerChatEvent event) {
        if (!getState()) return;
        String msg = event.getComponent().toString().toLowerCase();
        for (String v: victory) {
            if (msg.contains(v)) {
                String message = content.get();
                if (hitokoto.get()) {
                    GGThread thread = new GGThread(Hitokoto.Category.get());
                    thread.start();
                    message = message.replace("{hitokoto}", hitokoto_auotgg);
                }

                mc.thePlayer.sendChatMessage(message);
            }
        }
    }
}

class GGThread extends Thread {
    private String category;

    GGThread(String c) {
        switch (c) {
            case "Animation":
                category = "a";
                break;
            case "Manga":
                category = "b";
                break;
            case "Game":
                category = "c";
                break;
        }
    }

    public void run() {
        String h = network.GetHitokoto(category);

        Gson gson = new Gson();
        HashMap map = new HashMap();
        map = gson.fromJson(h, map.getClass());
        AutoGG.hitokoto_auotgg = (String) map.get("hitokoto");
    }
}
