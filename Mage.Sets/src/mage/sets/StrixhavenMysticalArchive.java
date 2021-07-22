package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class StrixhavenMysticalArchive extends ExpansionSet {

    private static final StrixhavenMysticalArchive instance = new StrixhavenMysticalArchive();

    public static StrixhavenMysticalArchive getInstance() {
        return instance;
    }

    private StrixhavenMysticalArchive() {
        super("Strixhaven Mystical Archive", "STA", ExpansionSet.buildDate(2021, 4, 23), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        this.maxCardNumberInBooster = 63;

        cards.add(new SetCardInfo("Abundant Harvest", 111, Rarity.RARE, mage.cards.a.AbundantHarvest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Abundant Harvest", 48, Rarity.RARE, mage.cards.a.AbundantHarvest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adventurous Impulse", 112, Rarity.UNCOMMON, mage.cards.a.AdventurousImpulse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adventurous Impulse", 49, Rarity.UNCOMMON, mage.cards.a.AdventurousImpulse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Agonizing Remorse", 24, Rarity.UNCOMMON, mage.cards.a.AgonizingRemorse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Agonizing Remorse", 87, Rarity.UNCOMMON, mage.cards.a.AgonizingRemorse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Approach of the Second Sun", 1, Rarity.MYTHIC, mage.cards.a.ApproachOfTheSecondSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Approach of the Second Sun", 64, Rarity.MYTHIC, mage.cards.a.ApproachOfTheSecondSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blue Sun's Zenith", 12, Rarity.MYTHIC, mage.cards.b.BlueSunsZenith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blue Sun's Zenith", 75, Rarity.MYTHIC, mage.cards.b.BlueSunsZenith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brainstorm", 13, Rarity.RARE, mage.cards.b.Brainstorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brainstorm", 76, Rarity.RARE, mage.cards.b.Brainstorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Channel", 113, Rarity.MYTHIC, mage.cards.c.Channel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Channel", 50, Rarity.MYTHIC, mage.cards.c.Channel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chaos Warp", 36, Rarity.MYTHIC, mage.cards.c.ChaosWarp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chaos Warp", 99, Rarity.MYTHIC, mage.cards.c.ChaosWarp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Claim the Firstborn", 100, Rarity.UNCOMMON, mage.cards.c.ClaimTheFirstborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Claim the Firstborn", 37, Rarity.UNCOMMON, mage.cards.c.ClaimTheFirstborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Compulsive Research", 14, Rarity.RARE, mage.cards.c.CompulsiveResearch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Compulsive Research", 77, Rarity.RARE, mage.cards.c.CompulsiveResearch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", 15, Rarity.RARE, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", 78, Rarity.RARE, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crux of Fate", 25, Rarity.MYTHIC, mage.cards.c.CruxOfFate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crux of Fate", 88, Rarity.MYTHIC, mage.cards.c.CruxOfFate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cultivate", 114, Rarity.UNCOMMON, mage.cards.c.Cultivate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cultivate", 51, Rarity.UNCOMMON, mage.cards.c.Cultivate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Ritual", 26, Rarity.RARE, mage.cards.d.DarkRitual.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Ritual", 89, Rarity.RARE, mage.cards.d.DarkRitual.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Day of Judgment", 2, Rarity.MYTHIC, mage.cards.d.DayOfJudgment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Day of Judgment", 65, Rarity.MYTHIC, mage.cards.d.DayOfJudgment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiant Strike", 3, Rarity.UNCOMMON, mage.cards.d.DefiantStrike.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiant Strike", 66, Rarity.UNCOMMON, mage.cards.d.DefiantStrike.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demonic Tutor", 27, Rarity.MYTHIC, mage.cards.d.DemonicTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demonic Tutor", 90, Rarity.MYTHIC, mage.cards.d.DemonicTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Despark", 122, Rarity.RARE, mage.cards.d.Despark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Despark", 59, Rarity.RARE, mage.cards.d.Despark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Divine Gambit", 4, Rarity.UNCOMMON, mage.cards.d.DivineGambit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Divine Gambit", 67, Rarity.UNCOMMON, mage.cards.d.DivineGambit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doom Blade", 28, Rarity.RARE, mage.cards.d.DoomBlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doom Blade", 91, Rarity.RARE, mage.cards.d.DoomBlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Duress", 29, Rarity.UNCOMMON, mage.cards.d.Duress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Duress", 92, Rarity.UNCOMMON, mage.cards.d.Duress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Electrolyze", 123, Rarity.RARE, mage.cards.e.Electrolyze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Electrolyze", 60, Rarity.RARE, mage.cards.e.Electrolyze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eliminate", 30, Rarity.UNCOMMON, mage.cards.e.Eliminate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eliminate", 93, Rarity.UNCOMMON, mage.cards.e.Eliminate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ephemerate", 5, Rarity.RARE, mage.cards.e.Ephemerate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ephemerate", 68, Rarity.RARE, mage.cards.e.Ephemerate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faithless Looting", 101, Rarity.RARE, mage.cards.f.FaithlessLooting.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faithless Looting", 38, Rarity.RARE, mage.cards.f.FaithlessLooting.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gift of Estates", 6, Rarity.RARE, mage.cards.g.GiftOfEstates.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gift of Estates", 69, Rarity.RARE, mage.cards.g.GiftOfEstates.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gods Willing", 7, Rarity.RARE, mage.cards.g.GodsWilling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gods Willing", 70, Rarity.RARE, mage.cards.g.GodsWilling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grapeshot", 102, Rarity.RARE, mage.cards.g.Grapeshot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grapeshot", 39, Rarity.RARE, mage.cards.g.Grapeshot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Growth Spiral", 124, Rarity.RARE, mage.cards.g.GrowthSpiral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Growth Spiral", 61, Rarity.RARE, mage.cards.g.GrowthSpiral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harmonize", 115, Rarity.RARE, mage.cards.h.Harmonize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harmonize", 52, Rarity.RARE, mage.cards.h.Harmonize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Increasing Vengeance", 103, Rarity.MYTHIC, mage.cards.i.IncreasingVengeance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Increasing Vengeance", 40, Rarity.MYTHIC, mage.cards.i.IncreasingVengeance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Infuriate", 104, Rarity.UNCOMMON, mage.cards.i.Infuriate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Infuriate", 41, Rarity.UNCOMMON, mage.cards.i.Infuriate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inquisition of Kozilek", 31, Rarity.RARE, mage.cards.i.InquisitionOfKozilek.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inquisition of Kozilek", 94, Rarity.RARE, mage.cards.i.InquisitionOfKozilek.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krosan Grip", 116, Rarity.RARE, mage.cards.k.KrosanGrip.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krosan Grip", 53, Rarity.RARE, mage.cards.k.KrosanGrip.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Bolt", 105, Rarity.RARE, mage.cards.l.LightningBolt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Bolt", 42, Rarity.RARE, mage.cards.l.LightningBolt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Helix", 125, Rarity.RARE, mage.cards.l.LightningHelix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Helix", 62, Rarity.RARE, mage.cards.l.LightningHelix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Tithe", 71, Rarity.RARE, mage.cards.m.ManaTithe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Tithe", 8, Rarity.RARE, mage.cards.m.ManaTithe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memory Lapse", 16, Rarity.RARE, mage.cards.m.MemoryLapse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memory Lapse", 79, Rarity.RARE, mage.cards.m.MemoryLapse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mind's Desire", 17, Rarity.MYTHIC, mage.cards.m.MindsDesire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mind's Desire", 80, Rarity.MYTHIC, mage.cards.m.MindsDesire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mizzix's Mastery", 106, Rarity.MYTHIC, mage.cards.m.MizzixsMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mizzix's Mastery", 43, Rarity.MYTHIC, mage.cards.m.MizzixsMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Natural Order", 117, Rarity.MYTHIC, mage.cards.n.NaturalOrder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Natural Order", 54, Rarity.MYTHIC, mage.cards.n.NaturalOrder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Negate", 18, Rarity.UNCOMMON, mage.cards.n.Negate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Negate", 81, Rarity.UNCOMMON, mage.cards.n.Negate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Opt", 19, Rarity.UNCOMMON, mage.cards.o.Opt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Opt", 82, Rarity.UNCOMMON, mage.cards.o.Opt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primal Command", 118, Rarity.MYTHIC, mage.cards.p.PrimalCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primal Command", 55, Rarity.MYTHIC, mage.cards.p.PrimalCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Putrefy", 126, Rarity.RARE, mage.cards.p.Putrefy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Putrefy", 63, Rarity.RARE, mage.cards.p.Putrefy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Regrowth", 119, Rarity.RARE, mage.cards.r.Regrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Regrowth", 56, Rarity.RARE, mage.cards.r.Regrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Revitalize", 72, Rarity.UNCOMMON, mage.cards.r.Revitalize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Revitalize", 9, Rarity.UNCOMMON, mage.cards.r.Revitalize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shock", 107, Rarity.UNCOMMON, mage.cards.s.Shock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shock", 44, Rarity.UNCOMMON, mage.cards.s.Shock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sign in Blood", 32, Rarity.RARE, mage.cards.s.SignInBlood.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sign in Blood", 95, Rarity.RARE, mage.cards.s.SignInBlood.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snakeskin Veil", 120, Rarity.UNCOMMON, mage.cards.s.SnakeskinVeil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snakeskin Veil", 57, Rarity.UNCOMMON, mage.cards.s.SnakeskinVeil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stone Rain", 108, Rarity.RARE, mage.cards.s.StoneRain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stone Rain", 45, Rarity.RARE, mage.cards.s.StoneRain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strategic Planning", 20, Rarity.UNCOMMON, mage.cards.s.StrategicPlanning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strategic Planning", 83, Rarity.UNCOMMON, mage.cards.s.StrategicPlanning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 10, Rarity.RARE, mage.cards.s.SwordsToPlowshares.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 73, Rarity.RARE, mage.cards.s.SwordsToPlowshares.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tainted Pact", 33, Rarity.MYTHIC, mage.cards.t.TaintedPact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tainted Pact", 96, Rarity.MYTHIC, mage.cards.t.TaintedPact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Protection", 11, Rarity.MYTHIC, mage.cards.t.TeferisProtection.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Protection", 74, Rarity.MYTHIC, mage.cards.t.TeferisProtection.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tendrils of Agony", 34, Rarity.RARE, mage.cards.t.TendrilsOfAgony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tendrils of Agony", 97, Rarity.RARE, mage.cards.t.TendrilsOfAgony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret's Gambit", 21, Rarity.RARE, mage.cards.t.TezzeretsGambit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret's Gambit", 84, Rarity.RARE, mage.cards.t.TezzeretsGambit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrill of Possibility", 109, Rarity.UNCOMMON, mage.cards.t.ThrillOfPossibility.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrill of Possibility", 46, Rarity.UNCOMMON, mage.cards.t.ThrillOfPossibility.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Time Warp", 22, Rarity.MYTHIC, mage.cards.t.TimeWarp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Time Warp", 85, Rarity.MYTHIC, mage.cards.t.TimeWarp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Rage", 110, Rarity.RARE, mage.cards.u.UrzasRage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Rage", 47, Rarity.RARE, mage.cards.u.UrzasRage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Village Rites", 35, Rarity.UNCOMMON, mage.cards.v.VillageRites.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Village Rites", 98, Rarity.UNCOMMON, mage.cards.v.VillageRites.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Weather the Storm", 121, Rarity.RARE, mage.cards.w.WeatherTheStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Weather the Storm", 58, Rarity.RARE, mage.cards.w.WeatherTheStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whirlwind Denial", 23, Rarity.UNCOMMON, mage.cards.w.WhirlwindDenial.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whirlwind Denial", 86, Rarity.UNCOMMON, mage.cards.w.WhirlwindDenial.class, NON_FULL_USE_VARIOUS));
    }
}
