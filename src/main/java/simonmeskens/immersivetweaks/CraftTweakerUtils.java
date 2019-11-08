package simonmeskens.immersivetweaks;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.item.ItemStack;
import scala.tools.nsc.Global;

import java.util.ArrayList;
import java.util.List;

public class CraftTweakerUtils {
    public static IIngredient toIIngredient(Object o) {
        if (o == null)
            return null;
        else if (o instanceof blusunrize.immersiveengineering.api.crafting.IngredientStack) {
            blusunrize.immersiveengineering.api.crafting.IngredientStack stack = (blusunrize.immersiveengineering.api.crafting.IngredientStack)o;

            return CraftTweakerMC.getIIngredient(stack.oreName).amount(stack.inputSize);
        }
        else {
            return CraftTweakerMC.getIIngredient(o);
        }
    }
}
