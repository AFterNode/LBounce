package cn.afternode.lbounce.modules.LModules;

import cn.afternode.lbounce.utils.network;
import com.google.gson.Gson;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.lwjgl.opengl.Display;

import java.util.HashMap;

@ModuleInfo(name = "Title", description = "修改标题", category = ModuleCategory.LBounce)
public class Title extends Module {
    private final BoolValue hitokoto = new BoolValue("Hitokoto", false);
    private final TextValue title = new TextValue("Title", "LBounce 1.8.9");
    public static String hitokoto_tmp;

    @Override
    public void onEnable() {
        String t = title.get();
        if (hitokoto.get()) {
            TitleThread thread = new TitleThread(Hitokoto.Category.get());
            thread.start();
            t = t.replace("{hitokoto}", hitokoto_tmp);
        }

        Display.setTitle(t);
    }
}

class TitleThread extends Thread {
    private String category;

    TitleThread(String c) {
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
        Title.hitokoto_tmp = (String) map.get("hitokoto");
    }
}
