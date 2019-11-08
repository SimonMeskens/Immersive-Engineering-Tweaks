package simonmeskens.immersivetweaks;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.HashMap;

@ZenClass("mods.immersivetweaks.Slag")
@ZenRegister
public class SlagReplacer {
    @ZenMethod
    public static void replaceSlag(IItemStack toReplace, IItemStack slag) {
        CraftTweakerAPI.apply(new Slag(null, toReplace, slag));
    }

    @ZenMethod
    public static void replaceSlagByInput(IIngredient input, IItemStack slag) {
        CraftTweakerAPI.apply(new Slag(input, null, slag));
    }

    private static class Slag implements IAction {
        private IIngredient input;
        private IItemStack slag;
        private IItemStack toReplace;

        public Slag(IIngredient input, IItemStack toReplace, IItemStack slag) {
            this.input = input;
            this.toReplace = toReplace;
            this.slag = slag;
        }

        @Override
        public void apply() {
            try {
                Field slagFieldBF = BlastFurnaceRecipe.class.getField("slag");
                slagFieldBF.setAccessible(true);

                for (BlastFurnaceRecipe recipe : BlastFurnaceRecipe.recipeList) {
                    if (recipe.slag == null || recipe.slag.isEmpty())
                        continue;

                    ItemStack slag = recipe.slag.copy();
                    slag.setCount(1);

                    if ((this.toReplace == null || CraftTweakerMC.getIItemStack(slag).matches(this.toReplace))
                            && (this.input == null || CraftTweakerUtils.toIIngredient(recipe.input).contains(this.input))) {
                        slag = CraftTweakerMC.getItemStack(this.slag);
                        slag.setCount(recipe.slag.getCount());

                        slagFieldBF.set(recipe, slag);
                    }
                }

                Field slagFieldAF = ArcFurnaceRecipe.class.getField("slag");
                slagFieldAF.setAccessible(true);

                for (ArcFurnaceRecipe recipe : ArcFurnaceRecipe.recipeList) {
                    if (recipe.slag == null || recipe.slag.isEmpty())
                        continue;

                    ItemStack slag = recipe.slag.copy();
                    slag.setCount(1);

                    if ((this.toReplace == null || CraftTweakerMC.getIItemStack(slag).matches(this.toReplace))
                            && (this.input == null || CraftTweakerUtils.toIIngredient(recipe.input).contains(this.input))) {
                        slag = CraftTweakerMC.getItemStack(this.slag);
                        slag.setCount(recipe.slag.getCount());

                        slagFieldAF.set(recipe, slag);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        @Override
        public String describe() {
            return "Replacing slag items";
        }
    }
}
