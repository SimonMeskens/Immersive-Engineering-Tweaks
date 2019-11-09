package simonmeskens.immersivetweaks;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.common.util.Utils;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemCondition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MetalPressNBTRecipe extends MetalPressRecipe {
    private final String recipeName;

    public MetalPressNBTRecipe(String recipeName, ItemStack output, Object input, ComparableItemStack mold, int energy) {
        super(output, input, mold, energy);

        this.recipeName = "immersivetweaks:" + recipeName;

        MetalPressRecipe.deserializers.put(this.recipeName, nbt -> {
            MetalPressNBTRecipe recipe = new MetalPressNBTRecipe(
                    nbt.getString("type"),
                    new ItemStack(nbt.getCompoundTag("output")),
                    IngredientStack.readFromNBT(nbt.getCompoundTag("input")),
                    ComparableItemStack.readFromNBT(nbt.getCompoundTag("mold")),
                    nbt.getInteger("energy"));
            return recipe;
        });
    }

    @Override
    public boolean matches(ItemStack mold, ItemStack input)
    {
        IItemStack matchInput = CraftTweakerMC.getIItemStack(input);
        IIngredient recipeInput = CraftTweakerMC.getIIngredient(this.input.toRecipeIngredient());

        for (IItemStack item : recipeInput.getItems()) {
            if (item.getDefinition().getId().equals(matchInput.getDefinition().getId()) && matchInput.getTag().contains(item.getTag()))
                return true;
        }

        return false;
    }

    @Override
    public ItemStack getDisplayStack(ItemStack input)
    {
        if(this.matches(null, input))
            return Utils.copyStackWithAmount(input, this.input.inputSize);
        return ItemStack.EMPTY;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setString("type", this.recipeName);
        nbt.setTag("output", this.output.writeToNBT(new NBTTagCompound()));
        nbt.setTag("input", this.input.writeToNBT(new NBTTagCompound()));
        nbt.setTag("mold", this.mold.writeToNBT(new NBTTagCompound()));
        nbt.setInteger("energy", (int)(getTotalProcessEnergy()/energyModifier));
        return nbt;
    }
}
