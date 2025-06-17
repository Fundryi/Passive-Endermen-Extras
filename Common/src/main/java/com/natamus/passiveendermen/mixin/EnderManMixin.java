package com.natamus.passiveendermen.mixin;

import com.natamus.passiveendermen.config.ConfigHandler;
import com.natamus.passiveendermen.util.TeleportAnchorUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EnderMan.class, priority = 1001)
public class EnderManMixin {
	@Inject(method = "teleport(DDD)Z", at = @At(value = "HEAD"), cancellable = true)
	private void teleport(double p_32544_, double p_32545_, double p_32546_, CallbackInfoReturnable<Boolean> cir) {
		EnderMan enderMan = (EnderMan) (Object) this;
		Level level = enderMan.level();
		Vec3 currentPos = enderMan.position();
		Vec3 targetPos = new Vec3(p_32544_, p_32545_, p_32546_);
		
		// Check if teleport anchors are enabled and should redirect teleportation
		if (ConfigHandler.enableTeleportAnchors) {
			BlockPos nearestAnchor = TeleportAnchorUtil.findNearestTeleportAnchor(level, targetPos);
			if (nearestAnchor != null) {
				// Redirect teleportation to the top of the anchor
				Vec3 anchorPos = TeleportAnchorUtil.getAnchorTeleportPosition(nearestAnchor);
				enderMan.teleportTo(anchorPos.x, anchorPos.y, anchorPos.z);
				cir.setReturnValue(true);
				return;
			}
		}
		
		// Original behavior: prevent teleporting entirely if configured
		if (ConfigHandler.preventEndermenFromTeleporting) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "isLookingAtMe(Lnet/minecraft/world/entity/player/Player;)Z", at = @At(value = "HEAD"), cancellable = true)
	void isLookingAtMe(Player player, CallbackInfoReturnable<Boolean> cir) {
		if (ConfigHandler.preventEndermenFromAttackingFirst) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "getCarriedBlock()Lnet/minecraft/world/level/block/state/BlockState;", at = @At(value = "HEAD"), cancellable = true)
	public void getCarriedBlock(CallbackInfoReturnable<BlockState> cir) {
		if (ConfigHandler.preventEndermenFromGriefing) {
			cir.setReturnValue(null);
		}
	}
}
