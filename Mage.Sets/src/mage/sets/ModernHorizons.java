package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons extends ExpansionSet {

    private static final ModernHorizons instance = new ModernHorizons();

    public static ModernHorizons getInstance() {
        return instance;
    }

    private ModernHorizons() {
        super("Modern Horizons", "MH1", ExpansionSet.buildDate(2019, 6, 14), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Modern Horizons";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Abominable Treefolk", 194, Rarity.UNCOMMON, mage.cards.a.AbominableTreefolk.class));
        cards.add(new SetCardInfo("Altar of Dementia", 218, Rarity.RARE, mage.cards.a.AltarOfDementia.class));
        cards.add(new SetCardInfo("Ayula's Influence", 156, Rarity.RARE, mage.cards.a.AyulasInfluence.class));
        cards.add(new SetCardInfo("Ayula, Queen Among Bears", 155, Rarity.RARE, mage.cards.a.AyulaQueenAmongBears.class));
        cards.add(new SetCardInfo("Cabal Therapist", 80, Rarity.RARE, mage.cards.c.CabalTherapist.class));
        cards.add(new SetCardInfo("Changeling Outcast", 82, Rarity.COMMON, mage.cards.c.ChangelingOutcast.class));
        cards.add(new SetCardInfo("Chillerpillar", 43, Rarity.COMMON, mage.cards.c.Chillerpillar.class));
        cards.add(new SetCardInfo("Choking Tethers", 44, Rarity.COMMON, mage.cards.c.ChokingTethers.class));
        cards.add(new SetCardInfo("Crypt Rats", 84, Rarity.UNCOMMON, mage.cards.c.CryptRats.class));
        cards.add(new SetCardInfo("Deep Forest Hermit", 161, Rarity.RARE, mage.cards.d.DeepForestHermit.class));
        cards.add(new SetCardInfo("Diabolic Edict", 87, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Dismantling Blow", 5, Rarity.UNCOMMON, mage.cards.d.DismantlingBlow.class));
        cards.add(new SetCardInfo("Dregscape Sliver", 88, Rarity.UNCOMMON, mage.cards.d.DregscapeSliver.class));
        cards.add(new SetCardInfo("Elvish Fury", 162, Rarity.COMMON, mage.cards.e.ElvishFury.class));
        cards.add(new SetCardInfo("Exclude", 48, Rarity.UNCOMMON, mage.cards.e.Exclude.class));
        cards.add(new SetCardInfo("Fact or Fiction", 50, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fiery Islet", 238, Rarity.RARE, mage.cards.f.FieryIslet.class));
        cards.add(new SetCardInfo("Firebolt", 122, Rarity.UNCOMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Flusterstorm", 255, Rarity.RARE, mage.cards.f.Flusterstorm.class));
        cards.add(new SetCardInfo("Generous Gift", 11, Rarity.UNCOMMON, mage.cards.g.GenerousGift.class));
        cards.add(new SetCardInfo("Goblin Engineer", 128, Rarity.RARE, mage.cards.g.GoblinEngineer.class));
        cards.add(new SetCardInfo("Goblin Matron", 129, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin War Party", 131, Rarity.COMMON, mage.cards.g.GoblinWarParty.class));
        cards.add(new SetCardInfo("Good-Fortune Unicorn", 201, Rarity.UNCOMMON, mage.cards.g.GoodFortuneUnicorn.class));
        cards.add(new SetCardInfo("Headless Specter", 95, Rarity.COMMON, mage.cards.h.HeadlessSpecter.class));
        cards.add(new SetCardInfo("Ice-Fang Coatl", 203, Rarity.RARE, mage.cards.i.IceFangCoatl.class));
        cards.add(new SetCardInfo("Impostor of the Sixth Pride", 14, Rarity.COMMON, mage.cards.i.ImpostorOfTheSixthPride.class));
        cards.add(new SetCardInfo("Lava Dart", 134, Rarity.COMMON, mage.cards.l.LavaDart.class));
        cards.add(new SetCardInfo("Lavabelly Sliver", 207, Rarity.UNCOMMON, mage.cards.l.LavabellySliver.class));
        cards.add(new SetCardInfo("Lightning Skelemental", 208, Rarity.RARE, mage.cards.l.LightningSkelemental.class));
        cards.add(new SetCardInfo("Man-o'-War", 55, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Martyr's Soul", 19, Rarity.COMMON, mage.cards.m.MartyrsSoul.class));
        cards.add(new SetCardInfo("Mother Bear", 171, Rarity.COMMON, mage.cards.m.MotherBear.class));
        cards.add(new SetCardInfo("Munitions Expert", 209, Rarity.UNCOMMON, mage.cards.m.MunitionsExpert.class));
        cards.add(new SetCardInfo("Nimble Mongoose", 174, Rarity.COMMON, mage.cards.n.NimbleMongoose.class));
        cards.add(new SetCardInfo("Nurturing Peatland", 243, Rarity.RARE, mage.cards.n.NurturingPeatland.class));
        cards.add(new SetCardInfo("Pondering Mage", 63, Rarity.COMMON, mage.cards.p.PonderingMage.class));
        cards.add(new SetCardInfo("Prismatic Vista", 244, Rarity.RARE, mage.cards.p.PrismaticVista.class));
        cards.add(new SetCardInfo("Prohibit", 64, Rarity.COMMON, mage.cards.p.Prohibit.class));
        cards.add(new SetCardInfo("Ranger-Captain of Eos", 21, Rarity.MYTHIC, mage.cards.r.RangerCaptainOfEos.class));
        cards.add(new SetCardInfo("Ravenous Giant", 143, Rarity.UNCOMMON, mage.cards.r.RavenousGiant.class));
        cards.add(new SetCardInfo("Regrowth", 175, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Scour All Possibilities", 67, Rarity.COMMON, mage.cards.s.ScourAllPossibilities.class));
        cards.add(new SetCardInfo("Scrapyard Recombiner", 227, Rarity.RARE, mage.cards.s.ScrapyardRecombiner.class));
        cards.add(new SetCardInfo("Seasoned Pyromancer", 145, Rarity.MYTHIC, mage.cards.s.SeasonedPyromancer.class));
        cards.add(new SetCardInfo("Serra the Benevolent", 26, Rarity.MYTHIC, mage.cards.s.SerraTheBenevolent.class));
        cards.add(new SetCardInfo("Silent Clearing", 246, Rarity.RARE, mage.cards.s.SilentClearing.class));
        cards.add(new SetCardInfo("Sisay, Weatherlight Captain", 29, Rarity.RARE, mage.cards.s.SisayWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 254, Rarity.LAND, mage.cards.s.SnowCoveredForest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Island", 251, Rarity.LAND, mage.cards.s.SnowCoveredIsland.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 253, Rarity.LAND, mage.cards.s.SnowCoveredMountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Plains", 250, Rarity.LAND, mage.cards.s.SnowCoveredPlains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 252, Rarity.LAND, mage.cards.s.SnowCoveredSwamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Spore Frog", 180, Rarity.COMMON, mage.cards.s.SporeFrog.class));
        cards.add(new SetCardInfo("Squirrel Nest", 182, Rarity.UNCOMMON, mage.cards.s.SquirrelNest.class));
        cards.add(new SetCardInfo("Stream of Thought", 71, Rarity.COMMON, mage.cards.s.StreamOfThought.class));
        cards.add(new SetCardInfo("Sunbaked Canyon", 247, Rarity.RARE, mage.cards.s.SunbakedCanyon.class));
        cards.add(new SetCardInfo("Tempered Sliver", 183, Rarity.UNCOMMON, mage.cards.t.TemperedSliver.class));
        cards.add(new SetCardInfo("The First Sliver", 200, Rarity.MYTHIC, mage.cards.t.TheFirstSliver.class));
        cards.add(new SetCardInfo("Undead Augur", 112, Rarity.UNCOMMON, mage.cards.u.UndeadAugur.class));
        cards.add(new SetCardInfo("Urza, Lord High Artificer", 75, Rarity.MYTHIC, mage.cards.u.UrzaLordHighArtificer.class));
        cards.add(new SetCardInfo("Venomous Changeling", 114, Rarity.COMMON, mage.cards.v.VenomousChangeling.class));
        cards.add(new SetCardInfo("Wall of One Thousand Cuts", 36, Rarity.COMMON, mage.cards.w.WallOfOneThousandCuts.class));
        cards.add(new SetCardInfo("Waterlogged Grove", 249, Rarity.RARE, mage.cards.w.WaterloggedGrove.class));
        cards.add(new SetCardInfo("Wing Shards", 38, Rarity.UNCOMMON, mage.cards.w.WingShards.class));
    }
}
