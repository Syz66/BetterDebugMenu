package me.zircta.betterdebug;

import com.gitlab.candicey.zenithloader.ZenithLoader;
import com.gitlab.candicey.zenithloader.dependency.Dependencies;
import me.zircta.betterdebug.config.BetterDebugConfig;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.EventBus;
import net.weavemc.loader.api.event.StartGameEvent;

public class BetterDebugMenu implements ModInitializer {
    public static BetterDebugConfig config;

    @Override
    public void preInit() {
        ZenithLoader.INSTANCE.loadDependencies(
                Dependencies.INSTANCE.getConcentra().invoke(
                        // betterdebug.versions.json
                        "betterdebug"
                )
        );

        EventBus.subscribe(StartGameEvent.Pre.class, (event) -> config = new BetterDebugConfig());
    }
}
