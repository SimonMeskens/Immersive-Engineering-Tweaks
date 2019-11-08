# Immersive-Engineering-Tweaks
Small tweaks for Immersive Engineering

```cs
// Add recipes with optional NBT-sensitivity
// Works like IE addRecipe, but has extra boolean
mods.immersivetweaks.MetalPress.addRecipe(
    IItemStack output, 
    IIngredient input, 
    IItemStack mold, 
    int energy, 
    @Optional boolean useNBT, 
    @Optional int inputSize
);

// Replace a certain type of slag with another in all recipes
mods.immersivetweaks.Slag.replaceSlag(
    IItemStack toReplace, 
    IItemStack slag
);

// Replace all recipes with said input by another slag
mods.immersivetweaks.Slag.replaceSlagByInput(
    IIngredient input, 
    IItemStack slag
);
```
