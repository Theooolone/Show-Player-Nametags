package theooolone.showplrnametags.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import theooolone.showplrnametags.ShowPlayerNametags;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

	@Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("TAIL"), cancellable = true)
	private <T extends LivingEntity> void forceNametagVisibility(T livingEntity, double distance, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) return;
		if (!ShowPlayerNametags.getConfig().enabled) return;

		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		ClientPlayerEntity clientPlayerEntity = minecraftClient.player;

		boolean hudEnabled = MinecraftClient.isHudEnabled();
		boolean isPlayer = livingEntity.isPlayer();
		boolean isCameraEntity = livingEntity == minecraftClient.getCameraEntity();
		boolean isVisible = !livingEntity.isInvisibleTo(clientPlayerEntity);
		boolean hasPassengers = livingEntity.hasPassengers();

		cir.setReturnValue((hudEnabled || isPlayer) && !isCameraEntity && isVisible && !hasPassengers);
	}
}