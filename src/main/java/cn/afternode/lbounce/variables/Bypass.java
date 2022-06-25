package cn.afternode.lbounce.variables;

import net.ccbluex.liquidbounce.utils.ClientUtils;

import java.util.HashMap;

public class Bypass {
    public static HashMap<String, String> HuaYuTing = new HashMap<>();

    public static void loadBypass() {
        loadHyt();

        ClientUtils.getLogger().info("[ChatBypass] 加载了 1 个聊天信息绕过");
    }

    public static void loadHyt() {
        HuaYuTing.put("开挂", "开g");
    }
}
