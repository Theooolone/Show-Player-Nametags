package theooolone.showplrnametags.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import theooolone.showplrnametags.ModConfig;
import theooolone.showplrnametags.ShowPlayerNametags;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

	@Inject(method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z", at = @At("TAIL"), cancellable = true)
	private void forceNametagVisibility(T entity, double distanceToCameraSq, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) return;
		ModConfig config = ShowPlayerNametags.getConfig();
		if (!config.enabled) return;

		Minecraft client = Minecraft.getInstance();
		LocalPlayer player = client.player;
		if (player == null) return;

		boolean hudEnabled = !Minecraft.getInstance().gui.hud.isHidden();
		boolean isPlayer = entity instanceof Player;
		boolean isCameraEntity = entity == client.getCameraEntity() && !config.show_own_nametag;
		boolean isVisible = !entity.isInvisibleTo(player);
		boolean hasPassengers = !entity.getPassengers().isEmpty();

		cir.setReturnValue(
				(hudEnabled || (isPlayer ? config.show_other_player_nametags : config.show_entity_nametags))
						&& (!isCameraEntity || config.show_own_nametag)
						&& isVisible
						&& !hasPassengers
		);
	}
}