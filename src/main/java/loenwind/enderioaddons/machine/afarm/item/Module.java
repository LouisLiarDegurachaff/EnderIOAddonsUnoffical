package loenwind.enderioaddons.machine.afarm.item;

import javax.annotation.Nonnull;

import loenwind.enderioaddons.EnderIOAddons;
import loenwind.enderioaddons.machine.afarm.module.BestOnlyModule;
import loenwind.enderioaddons.machine.afarm.module.BreedModule;
import loenwind.enderioaddons.machine.afarm.module.CrossBreedModule;
import loenwind.enderioaddons.machine.afarm.module.EjectSeedsModule;
import loenwind.enderioaddons.machine.afarm.module.HarvestSeedsModule;
import loenwind.enderioaddons.machine.afarm.module.HarvestUnanalyzedModule;
import loenwind.enderioaddons.machine.afarm.module.IAfarmControlModule;
import loenwind.enderioaddons.machine.afarm.module.MultiplyModule;
import loenwind.enderioaddons.machine.afarm.module.ReplaceBetterModule;
import loenwind.enderioaddons.machine.afarm.module.SeedAnalyzerModule;
import loenwind.enderioaddons.machine.afarm.module.WeedModule;

public enum Module {

    BREED("BreedModule", new BreedModule()),
    CROSSBREED("CrossBreedModule", new CrossBreedModule()),
    HARVESTSEEDS("HarvestSeedsModule", new HarvestSeedsModule()),
    ANALYZESEEDS("SeedAnalyzerModule", new SeedAnalyzerModule()),
    MULTIPLY("MultiplyModule", new MultiplyModule()),
    HARVESTUNANALYZED("HarvestUnanalyzedModule", new HarvestUnanalyzedModule()),
    REPLACEBETTER("ReplaceBetterModule", new ReplaceBetterModule()),
    WEED("WeedModule", new WeedModule()),
    EJECTSEEDS("EjectSeedsModule", new EjectSeedsModule()),
    BESTONLY("BestOnlyModule", new BestOnlyModule()),;

    @Nonnull
    public final String unlocalisedName;
    @Nonnull
    public final String iconKey;
    @Nonnull
    public final IAfarmControlModule module;

    private Module(@Nonnull String unlocalisedName, @Nonnull IAfarmControlModule module) {
        this.unlocalisedName = EnderIOAddons.DOMAIN + ".fcm" + unlocalisedName;
        this.iconKey = EnderIOAddons.DOMAIN + ":fcm" + unlocalisedName;
        this.module = module;
    }

}
