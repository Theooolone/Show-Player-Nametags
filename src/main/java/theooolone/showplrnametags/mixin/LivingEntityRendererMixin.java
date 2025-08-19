package theooolone.showplrnametags.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import theooolone.showplrnametags.ModConfig;
import theooolone.showplrnametags.ShowPlayerNametags;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

	@Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("TAIL"), cancellable = true)
	private <T extends LivingEntity> void forceNametagVisibility(T livingEntity, double distance, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) return;
		ModConfig config = ShowPlayerNametags.getConfig();
		if (!config.enabled) return;

		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;

		boolean hudEnabled = MinecraftClient.isHudEnabled();
		boolean isPlayer = livingEntity.isPlayer();
		boolean isCameraEntity = livingEntity == client.getCameraEntity() && !config.show_own_nametag;
		boolean isVisible = !livingEntity.isInvisibleTo(player);
		boolean hasPassengers = livingEntity.hasPassengers();

		cir.setReturnValue(
				(hudEnabled || (isPlayer ? config.show_other_player_nametags : config.show_entity_nametags))
						&& (!isCameraEntity || config.show_own_nametag)
						&& isVisible
						&& !hasPassengers
		);
	}
}