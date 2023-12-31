package me.zircta.betterdebug.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class BetterDebugConfig extends Config {
    // TODO: Add modern crosshair
    @Dropdown(
            name = "Crosshair",
            description = "Change crosshair style.",
            options = {"1.7", "1.8"},
            subcategory = "Debug Style"
    )
    public int crosshair = 1;

    @Dropdown(
            name = "Information",
            description = "Change information style.",
            options = {"1.7", "1.8"},
            subcategory = "Debug Style"
    )
    public int information = 1;

    @Switch(
            name = "Shadow",
            description = "Toggle text shadow.",
            subcategory = "Text Style"
    )
    public boolean shadow = true;

    @Switch(
            name = "Background",
            description = "Toggle text background.",
            subcategory = "Text Style"
    )
    public boolean background = false;

    @Color(
            name = "Text color",
            description = "Set text color.",
            subcategory = "Text Style"
    )
    public OneColor textColor = new OneColor(224, 224, 224);

    @Color(
            name = "Background color",
            description = "Set Background color.",
            subcategory = "Text Style"
    )
    public OneColor backgroundColor = new OneColor(0, 0, 0, 80);

    @Switch(
            name = "Hide debug crosshair in F5",
            description = "Removes debug crosshair in third person.",
            subcategory = "Fixes"
    )
    public boolean crosshairfix = true;

    @Switch(
            name = "Add pitch",
            description = "Adds pitch to 1.7 information.",
            subcategory = "Fixes"
    )
    public boolean pitch = false;

    public BetterDebugConfig() {
        super(new Mod("Better Debug Menu", ModType.HUD), "better-debug-menu.json");

        this.initialize();
        this.addDependency("pitch", "information", () -> information == 0);
    }
}
