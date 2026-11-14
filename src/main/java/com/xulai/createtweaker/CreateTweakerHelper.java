package com.xulai.createtweaker;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.fluids.crafting.TagFluidIngredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class CreateTweakerHelper {

    private static final Map<Class<?>, StandardProcessingRecipe.Factory<?>> STANDARD_FACTORIES = new HashMap<>();
    private static final Map<Class<?>, ItemApplicationRecipe.Factory<?>> APPLICATION_FACTORIES = new HashMap<>();

    static {
        STANDARD_FACTORIES.put(CuttingRecipe.class, CuttingRecipe::new);
        STANDARD_FACTORIES.put(FillingRecipe.class, FillingRecipe::new);
        STANDARD_FACTORIES.put(PressingRecipe.class, PressingRecipe::new);
        APPLICATION_FACTORIES.put(DeployerApplicationRecipe.class, DeployerApplicationRecipe::new);
    }

    public static SizedFluidIngredient mapFluidIngredients(CTFluidIngredient ingredient) {

        return ingredient.mapTo(
                stack -> SizedFluidIngredient.of(stack.<FluidStack>getInternal()),
                SizedFluidIngredient::of,
                stream -> {
                    throw new IllegalArgumentException("Unable to use a compound ingredient for Create!");
                });
    }

    public static CTFluidIngredient mapFluidIngredientsToCT(SizedFluidIngredient ingredient) {

        if(ingredient.ingredient() instanceof TagFluidIngredient tagIngredient) {
            KnownTag<Fluid> tag = CraftTweakerTagRegistry.INSTANCE.knownTagManager(Registries.FLUID)
                    .tag(tagIngredient.tag()
                            .location());
            return new CTFluidIngredient.FluidTagWithAmountIngredient(tag.withAmount(ingredient.amount()));
        }
        Optional<CTFluidIngredient> reduce = Arrays.stream(ingredient.getFluids())
                .map(stack -> IFluidStack.of(stack))
                .map(IFluidStack::asFluidIngredient)
                .reduce(CTFluidIngredient::asCompound);
        return reduce.orElseThrow();
    }

    public static Percentaged<IItemStack> mapProcessingResult(ProcessingOutput result) {

        return IItemStack.of(result.getStack())
                .percent(result.getChance() * 100);
    }

    public static ProcessingOutput mapPercentagedToProcessingOutput(Percentaged<IItemStack> stack) {

        return new ProcessingOutput(stack.getData()
                .getInternal(), (float) stack.getPercentage());
    }

    public static Ingredient[] itemInputsToIngredients(IIngredientWithAmount[] itemInputs) {

        List<Ingredient> ingredients = new ArrayList<>();
        for(IIngredientWithAmount input : itemInputs) {
            for(int i = 0; i < input.amount(); i++) {
                ingredients.add(input.ingredient()
                        .asVanillaIngredient());
            }
        }
        return ingredients.toArray(new Ingredient[0]);
    }

    public static List<IFluidStack> getRecipeFluidResults(ProcessingRecipe<?, ?> recipe) {

        return recipe.getFluidResults()
                .stream()
                .map(stack -> IFluidStack.of(stack))
                .toList();
    }

    public static List<IFluidStack> getMatchingFluidStacks(SizedFluidIngredient ingredient) {

        return Arrays.stream(ingredient.getFluids())
                .map(stack -> IFluidStack.of(stack))
                .toList();
    }

    public static boolean testFluidIngredient(SizedFluidIngredient ingredient, IFluidStack stack) {

        return ingredient.test(stack.getInternal());
    }

    public static <T extends ProcessingRecipe<?, ?>> boolean doFluidIngredientsConflict(T first, T second) {

        List<SizedFluidIngredient> firstIngredients = first.getFluidIngredients();
        List<SizedFluidIngredient> secondIngredients = second.getFluidIngredients();
        if(firstIngredients.size() != secondIngredients.size()) {
            return false;
        }
        for(int i = 0; i < firstIngredients.size(); i++) {
            SizedFluidIngredient firstIngredient = firstIngredients.get(i);
            SizedFluidIngredient secondIngredient = secondIngredients.get(i);
            if(firstIngredient.ingredient()
                    .hasNoFluids() || secondIngredient.ingredient()
                    .hasNoFluids()) {
                continue;
            }
            boolean conflicts = false;
            for(FluidStack firstStack : firstIngredient.getFluids()) {
                for(FluidStack secondStack : secondIngredient.getFluids()) {
                    if(FluidStack.isSameFluidSameComponents(firstStack, secondStack) && firstStack
                            .getAmount() >= secondStack.getAmount()) {
                        conflicts = true;
                        break;
                    }
                }
                if(conflicts) {
                    break;
                }
            }
            if(!conflicts) {
                return false;
            }
        }
        return true;
    }

    public static SequencedAssemblyRecipeBuilder addStep(SequencedAssemblyRecipeBuilder builder, Class<?> clazz, Function<ProcessingRecipeBuilder, ProcessingRecipeBuilder> step) {

        StandardProcessingRecipe.Factory<?> standardFactory = STANDARD_FACTORIES.get(clazz);
        if(standardFactory != null) {
            return builder.addStep(standardFactory, recipeBuilder -> GenericUtil.uncheck(step.apply(recipeBuilder)));
        }
        ItemApplicationRecipe.Factory<?> applicationFactory = APPLICATION_FACTORIES.get(clazz);
        if(applicationFactory != null) {
            return builder.addStep(applicationFactory, recipeBuilder -> GenericUtil.uncheck(step.apply(recipeBuilder)));
        }
        throw new IllegalArgumentException("Unable to use non Assembly recipe: '%s' in Sequenced Assembly Recipe!".formatted(clazz
                .getName()));
    }

    public static SequencedAssemblyRecipeBuilder addStep(SequencedAssemblyRecipeBuilder builder, Class<?> clazz) {

        return addStep(builder, clazz, recipeBuilder -> recipeBuilder);
    }
}
