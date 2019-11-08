package simonmeskens.immersivetweaks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ImmersiveTweaks.MODID, name = ImmersiveTweaks.NAME, version = ImmersiveTweaks.VERSION)
public class ImmersiveTweaks
{
    public static final String MODID = "immersivetweaks";
    public static final String NAME = "Immersive Engineering Tweaks";
    public static final String VERSION = "1.0.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
