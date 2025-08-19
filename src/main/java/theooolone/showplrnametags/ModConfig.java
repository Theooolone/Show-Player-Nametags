package theooolone.showplrnametags;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "show-player-nametags")
public class ModConfig implements ConfigData {
	public boolean enabled = true;
	public boolean show_other_player_nametags = true;
	public boolean show_own_nametag = false;
	public boolean show_entity_nametags = true;
}
