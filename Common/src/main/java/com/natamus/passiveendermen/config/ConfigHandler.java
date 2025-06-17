package com.natamus.passiveendermen.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.passiveendermen.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean preventEndermenFromTeleporting = true;
	@Entry public static boolean preventEndermenFromGriefing = true;
	@Entry public static boolean preventEndermenFromAttackingFirst = true;
	@Entry public static boolean enableTeleportAnchors = true;
	@Entry public static int teleportAnchorRange = 32;

	public static void initConfig() {
		configMetaData.put("preventEndermenFromTeleporting", Arrays.asList(
			"If enabled, prevents the endermen from teleporting."
		));
		configMetaData.put("preventEndermenFromGriefing", Arrays.asList(
			"If enabled, prevents from picking up and placing blocks."
		));
		configMetaData.put("preventEndermenFromAttackingFirst", Arrays.asList(
			"If enabled, stops the endermen from attacking."
		));
		configMetaData.put("enableTeleportAnchors", Arrays.asList(
			"If enabled, redstone torches named 'Ender Tether' will redirect enderman teleportation within range instead of preventing it."
		));
		configMetaData.put("teleportAnchorRange", Arrays.asList(
			"The range in blocks around Ender Tether torches where endermen teleportation will be redirected. Default: 32"
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}