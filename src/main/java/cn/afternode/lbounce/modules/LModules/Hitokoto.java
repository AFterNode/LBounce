package cn.afternode.lbounce.modules.LModules;

import cn.afternode.lbounce.utils.network;
import com.google.gson.Gson;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import scala.reflect.internal.Types;

import java.util.HashMap;

@ModuleInfo(name = "Hitokoto", description = "从HitokotoAPI获取词句", category = ModuleCategory.LBounce)
public class Hitokoto extends Module {
    private final String[] categories = {"Animation", "Manga"};
    private final ListValue Category = new ListValue("Category", categories, categories[0]);

    @Override
    public void onEnable() {
        HitokotoThread thread = new HitokotoThread(Category.get());
        thread.start();
        this.toggle();
    }
}

class HitokotoThread extends Thread {
    String category;

    HitokotoThread(String c) {
        if (c.equals("Animation")) category = "a";
        else if (c.equals("Manga")) category = "b";
    }

    public void run() {
        String result = network.GetHitokoto(category);

        Gson gson = new Gson();
        HashMap map = new HashMap();
        map = gson.fromJson(result, map.getClass());
        Hitokoto.mc.thePlayer.sendChatMessage(map.get("hitokoto") + " | " + map.get("from") +
                " >>LBounce Hitokoto<<");
    }
}
