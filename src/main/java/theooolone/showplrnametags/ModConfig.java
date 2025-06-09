package theooolone.showplrnametags;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "show-player-nametags")
public class ModConfig implements ConfigData {
	public boolean enabled = true;
}
