package theooolone.showplrnametags;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;


public class ModMenuCompatibility implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create()
					.setParentScreen(parent)
					.setTitle(Text.translatable("title.showplrnametags.config"));

			ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.showplrnametags.general"));
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();

			general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.showplrnametags.toggle"), ShowPlayerNametags.enableModSetting)
					.setDefaultValue(true)
					.setSaveConsumer(newValue -> ShowPlayerNametags.enableModSetting = newValue)
					.build());

			return builder.build();
		};
	}
}
