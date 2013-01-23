package decoblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import decoblock.gui.GuiHandler;
import decoblock.network.ConnectionHandler;


@Mod(name="DecorationBlock", version="@THIS_MOD_VERSION@", modid = "decoblock")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels=CommonProxy.CHANNEL, packetHandler=decoblock.network.PacketHandler.class)
public class DecorationBlock {
	
	@SidedProxy(clientSide="decoblock.ClientProxy", serverSide="decoblock.CommonProxy")
	public static CommonProxy proxy;
	
	public static String defaultTexture = ModConfiguration.defaultType.typeText() + "/" + ModConfiguration.defaultTexture;
	public static boolean useIcon = true;

	@Instance
	public static DecorationBlock instance;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		ModConfiguration.loadConfiguration(event.getSuggestedConfigurationFile());
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		
		proxy.prepareDir();
		
		ModItems.decoration = new BlockDecoration(ModConfiguration.idDecoration, 1);
		GameRegistry.registerBlock(ModItems.decoration, "decorationBlock");
		GameRegistry.registerTileEntity(TileEntityDecoration.class, "DecorationBlock");
		LanguageRegistry.addName(ModItems.decoration, "Decoration Block");
		proxy.registerRenderer();
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		NetworkRegistry.instance().registerConnectionHandler(new ConnectionHandler());
		
		// Recipes
		ItemStack planks = new ItemStack(Block.planks, 1, -1);
		ItemStack decoration = new ItemStack(ModItems.decoration);
		GameRegistry.addShapelessRecipe(decoration, new Object[] {planks, Block.plantRed});
		GameRegistry.addShapelessRecipe(decoration, new Object[] {planks, Block.plantYellow});
				
	}


}
