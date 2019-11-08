package simonmeskens.immersivetweaks;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.immersivetweaks.MetalPress")
@ZenRegister
public class MetalPress {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, IItemStack mold, int energy, @Optional boolean useNBT, @Optional int inputSize) {
        Object oInput = CraftTweakerHelper.toObject(input);
        if (oInput == null)
            return;
        ItemStack sOut = CraftTweakerHelper.toStack(output);
        ItemStack sMold = CraftTweakerHelper.toStack(mold);
        if (!sOut.isEmpty() && !sMold.isEmpty()) {
            MetalPressRecipe r = new MetalPressRecipe(sOut, oInput, ApiUtils.createComparableItemStack(sMold, true, useNBT), energy);
            r.input.setUseNBT(useNBT);
            if (inputSize > 0)
                r.setInputSize(inputSize);
            CraftTweakerAPI.apply(new Add(r));
        }
    }

    private static class Add implements IAction {
        private final MetalPressRecipe recipe;

        public Add(MetalPressRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public void apply() {
            MetalPressRecipe.recipeList.put(recipe.mold, recipe);
        }

        @Override
        public String describe() {
            return "Adding Metal Press Recipe for " + recipe.output.getDisplayName();
        }
    }
}
