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
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Abominable Treefolk", 194, Rarity.UNCOMMON, mage.cards.a.AbominableTreefolk.class));
        cards.add(new SetCardInfo("Altar of Dementia", 218, Rarity.RARE, mage.cards.a.AltarOfDementia.class));
        cards.add(new SetCardInfo("Archmage's Charm", 40, Rarity.RARE, mage.cards.a.ArchmagesCharm.class));
        cards.add(new SetCardInfo("Aria of Flame", 118, Rarity.RARE, mage.cards.a.AriaOfFlame.class));
        cards.add(new SetCardInfo("Astral Drift", 3, Rarity.RARE, mage.cards.a.AstralDrift.class));
        cards.add(new SetCardInfo("Ayula's Influence", 156, Rarity.RARE, mage.cards.a.AyulasInfluence.class));
        cards.add(new SetCardInfo("Ayula, Queen Among Bears", 155, Rarity.RARE, mage.cards.a.AyulaQueenAmongBears.class));
        cards.add(new SetCardInfo("Battle Screech", 4, Rarity.UNCOMMON, mage.cards.b.BattleScreech.class));
        cards.add(new SetCardInfo("Bazaar Trademage", 41, Rarity.RARE, mage.cards.b.BazaarTrademage.class));
        cards.add(new SetCardInfo("Cabal Therapist", 80, Rarity.RARE, mage.cards.c.CabalTherapist.class));
        cards.add(new SetCardInfo("Changeling Outcast", 82, Rarity.COMMON, mage.cards.c.ChangelingOutcast.class));
        cards.add(new SetCardInfo("Chillerpillar", 43, Rarity.COMMON, mage.cards.c.Chillerpillar.class));
        cards.add(new SetCardInfo("Choking Tethers", 44, Rarity.COMMON, mage.cards.c.ChokingTethers.class));
        cards.add(new SetCardInfo("Cloudshredder Sliver", 195, Rarity.RARE, mage.cards.c.CloudshredderSliver.class));
        cards.add(new SetCardInfo("Collected Conjuring", 196, Rarity.RARE, mage.cards.c.CollectedConjuring.class));
        cards.add(new SetCardInfo("Collector Ouphe", 158, Rarity.RARE, mage.cards.c.CollectorOuphe.class));
        cards.add(new SetCardInfo("Crypt Rats", 84, Rarity.UNCOMMON, mage.cards.c.CryptRats.class));
        cards.add(new SetCardInfo("Deep Forest Hermit", 161, Rarity.RARE, mage.cards.d.DeepForestHermit.class));
        cards.add(new SetCardInfo("Diabolic Edict", 87, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Dismantling Blow", 5, Rarity.UNCOMMON, mage.cards.d.DismantlingBlow.class));
        cards.add(new SetCardInfo("Dregscape Sliver", 88, Rarity.UNCOMMON, mage.cards.d.DregscapeSliver.class));
        cards.add(new SetCardInfo("Eladamri's Call", 197, Rarity.RARE, mage.cards.e.EladamrisCall.class));
        cards.add(new SetCardInfo("Elvish Fury", 162, Rarity.COMMON, mage.cards.e.ElvishFury.class));
        cards.add(new SetCardInfo("Etchings of the Chosen", 198, Rarity.UNCOMMON, mage.cards.e.EtchingsOfTheChosen.class));
        cards.add(new SetCardInfo("Exclude", 48, Rarity.UNCOMMON, mage.cards.e.Exclude.class));
        cards.add(new SetCardInfo("Fact or Fiction", 50, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fallen Shinobi", 199, Rarity.RARE, mage.cards.f.FallenShinobi.class));
        cards.add(new SetCardInfo("Farmstead Gleaner", 222, Rarity.UNCOMMON, mage.cards.f.FarmsteadGleaner.class));
        cards.add(new SetCardInfo("Feaster of Fools", 90, Rarity.UNCOMMON, mage.cards.f.FeasterOfFools.class));
        cards.add(new SetCardInfo("Fiery Islet", 238, Rarity.RARE, mage.cards.f.FieryIslet.class));
        cards.add(new SetCardInfo("Firebolt", 122, Rarity.UNCOMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Fists of Flame", 123, Rarity.COMMON, mage.cards.f.FistsOfFlame.class));
        cards.add(new SetCardInfo("Flusterstorm", 255, Rarity.RARE, mage.cards.f.Flusterstorm.class));
        cards.add(new SetCardInfo("Force of Despair", 92, Rarity.RARE, mage.cards.f.ForceOfDespair.class));
        cards.add(new SetCardInfo("Force of Negation", 52, Rarity.RARE, mage.cards.f.ForceOfNegation.class));
        cards.add(new SetCardInfo("Force of Rage", 124, Rarity.RARE, mage.cards.f.ForceOfRage.class));
        cards.add(new SetCardInfo("Force of Vigor", 164, Rarity.RARE, mage.cards.f.ForceOfVigor.class));
        cards.add(new SetCardInfo("Force of Virtue", 10, Rarity.RARE, mage.cards.f.ForceOfVirtue.class));
        cards.add(new SetCardInfo("Frostwalk Bastion", 240, Rarity.UNCOMMON, mage.cards.f.FrostwalkBastion.class));
        cards.add(new SetCardInfo("Generous Gift", 11, Rarity.UNCOMMON, mage.cards.g.GenerousGift.class));
        cards.add(new SetCardInfo("Genesis", 166, Rarity.RARE, mage.cards.g.Genesis.class));
        cards.add(new SetCardInfo("Giver of Runes", 13, Rarity.RARE, mage.cards.g.GiverOfRunes.class));
        cards.add(new SetCardInfo("Glacial Revelation", 167, Rarity.UNCOMMON, mage.cards.g.GlacialRevelation.class));
        cards.add(new SetCardInfo("Goatnap", 126, Rarity.COMMON, mage.cards.g.Goatnap.class));
        cards.add(new SetCardInfo("Goblin Champion", 127, Rarity.COMMON, mage.cards.g.GoblinChampion.class));
        cards.add(new SetCardInfo("Goblin Engineer", 128, Rarity.RARE, mage.cards.g.GoblinEngineer.class));
        cards.add(new SetCardInfo("Goblin Matron", 129, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin War Party", 131, Rarity.COMMON, mage.cards.g.GoblinWarParty.class));
        cards.add(new SetCardInfo("Good-Fortune Unicorn", 201, Rarity.UNCOMMON, mage.cards.g.GoodFortuneUnicorn.class));
        cards.add(new SetCardInfo("Headless Specter", 95, Rarity.COMMON, mage.cards.h.HeadlessSpecter.class));
        cards.add(new SetCardInfo("Hexdrinker", 168, Rarity.MYTHIC, mage.cards.h.Hexdrinker.class));
        cards.add(new SetCardInfo("Hollowhead Sliver", 132, Rarity.UNCOMMON, mage.cards.h.HollowheadSliver.class));
        cards.add(new SetCardInfo("Ice-Fang Coatl", 203, Rarity.RARE, mage.cards.i.IceFangCoatl.class));
        cards.add(new SetCardInfo("Impostor of the Sixth Pride", 14, Rarity.COMMON, mage.cards.i.ImpostorOfTheSixthPride.class));
        cards.add(new SetCardInfo("Kess, Dissident Mage", 206, Rarity.MYTHIC, mage.cards.k.KessDissidentMage.class));
        cards.add(new SetCardInfo("King of the Pride", 16, Rarity.UNCOMMON, mage.cards.k.KingOfThePride.class));
        cards.add(new SetCardInfo("Lava Dart", 134, Rarity.COMMON, mage.cards.l.LavaDart.class));
        cards.add(new SetCardInfo("Lavabelly Sliver", 207, Rarity.UNCOMMON, mage.cards.l.LavabellySliver.class));
        cards.add(new SetCardInfo("Lesser Masticore", 225, Rarity.UNCOMMON, mage.cards.l.LesserMasticore.class));
        cards.add(new SetCardInfo("Lightning Skelemental", 208, Rarity.RARE, mage.cards.l.LightningSkelemental.class));
        cards.add(new SetCardInfo("Man-o'-War", 55, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Martyr's Soul", 19, Rarity.COMMON, mage.cards.m.MartyrsSoul.class));
        cards.add(new SetCardInfo("Morophon, the Boundless", 1, Rarity.MYTHIC, mage.cards.m.MorophonTheBoundless.class));
        cards.add(new SetCardInfo("Mother Bear", 171, Rarity.COMMON, mage.cards.m.MotherBear.class));
        cards.add(new SetCardInfo("Mox Tantalite", 226, Rarity.MYTHIC, mage.cards.m.MoxTantalite.class));
        cards.add(new SetCardInfo("Munitions Expert", 209, Rarity.UNCOMMON, mage.cards.m.MunitionsExpert.class));
        cards.add(new SetCardInfo("Nature's Chant", 210, Rarity.COMMON, mage.cards.n.NaturesChant.class));
        cards.add(new SetCardInfo("Nimble Mongoose", 174, Rarity.COMMON, mage.cards.n.NimbleMongoose.class));
        cards.add(new SetCardInfo("Nurturing Peatland", 243, Rarity.RARE, mage.cards.n.NurturingPeatland.class));
        cards.add(new SetCardInfo("Orcish Hellraiser", 136, Rarity.COMMON, mage.cards.o.OrcishHellraiser.class));
        cards.add(new SetCardInfo("Pillage", 139, Rarity.UNCOMMON, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Plague Engineer", 100, Rarity.RARE, mage.cards.p.PlagueEngineer.class));
        cards.add(new SetCardInfo("Planebound Accomplice", 140, Rarity.RARE, mage.cards.p.PlaneboundAccomplice.class));
        cards.add(new SetCardInfo("Pondering Mage", 63, Rarity.COMMON, mage.cards.p.PonderingMage.class));
        cards.add(new SetCardInfo("Prismatic Vista", 244, Rarity.RARE, mage.cards.p.PrismaticVista.class));
        cards.add(new SetCardInfo("Prohibit", 64, Rarity.COMMON, mage.cards.p.Prohibit.class));
        cards.add(new SetCardInfo("Pyrophobia", 141, Rarity.COMMON, mage.cards.p.Pyrophobia.class));
        cards.add(new SetCardInfo("Ranger-Captain of Eos", 21, Rarity.MYTHIC, mage.cards.r.RangerCaptainOfEos.class));
        cards.add(new SetCardInfo("Ravenous Giant", 143, Rarity.UNCOMMON, mage.cards.r.RavenousGiant.class));
        cards.add(new SetCardInfo("Regrowth", 175, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Savage Swipe", 178, Rarity.COMMON, mage.cards.s.SavageSwipe.class));
        cards.add(new SetCardInfo("Scale Up", 179, Rarity.UNCOMMON, mage.cards.s.ScaleUp.class));
        cards.add(new SetCardInfo("Scour All Possibilities", 67, Rarity.COMMON, mage.cards.s.ScourAllPossibilities.class));
        cards.add(new SetCardInfo("Scrapyard Recombiner", 227, Rarity.RARE, mage.cards.s.ScrapyardRecombiner.class));
        cards.add(new SetCardInfo("Scuttling Sliver", 68, Rarity.UNCOMMON, mage.cards.s.ScuttlingSliver.class));
        cards.add(new SetCardInfo("Seasoned Pyromancer", 145, Rarity.MYTHIC, mage.cards.s.SeasonedPyromancer.class));
        cards.add(new SetCardInfo("Segovian Angel", 25, Rarity.COMMON, mage.cards.s.SegovianAngel.class));
        cards.add(new SetCardInfo("Serra the Benevolent", 26, Rarity.MYTHIC, mage.cards.s.SerraTheBenevolent.class));
        cards.add(new SetCardInfo("Silent Clearing", 246, Rarity.RARE, mage.cards.s.SilentClearing.class));
        cards.add(new SetCardInfo("Sisay, Weatherlight Captain", 29, Rarity.RARE, mage.cards.s.SisayWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Sling-Gang Lieutenant", 108, Rarity.UNCOMMON, mage.cards.s.SlingGangLieutenant.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 254, Rarity.LAND, mage.cards.s.SnowCoveredForest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Island", 251, Rarity.LAND, mage.cards.s.SnowCoveredIsland.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 253, Rarity.LAND, mage.cards.s.SnowCoveredMountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Plains", 250, Rarity.LAND, mage.cards.s.SnowCoveredPlains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 252, Rarity.LAND, mage.cards.s.SnowCoveredSwamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Spell Snuff", 70, Rarity.COMMON, mage.cards.s.SpellSnuff.class));
        cards.add(new SetCardInfo("Splicer's Skill", 31, Rarity.UNCOMMON, mage.cards.s.SplicersSkill.class));
        cards.add(new SetCardInfo("Spore Frog", 180, Rarity.COMMON, mage.cards.s.SporeFrog.class));
        cards.add(new SetCardInfo("Springbloom Druid", 181, Rarity.COMMON, mage.cards.s.SpringbloomDruid.class));
        cards.add(new SetCardInfo("Squirrel Nest", 182, Rarity.UNCOMMON, mage.cards.s.SquirrelNest.class));
        cards.add(new SetCardInfo("Stream of Thought", 71, Rarity.COMMON, mage.cards.s.StreamOfThought.class));
        cards.add(new SetCardInfo("String of Disappearances", 72, Rarity.COMMON, mage.cards.s.StringOfDisappearances.class));
        cards.add(new SetCardInfo("Sunbaked Canyon", 247, Rarity.RARE, mage.cards.s.SunbakedCanyon.class));
        cards.add(new SetCardInfo("Sword of Sinew and Steel", 228, Rarity.MYTHIC, mage.cards.s.SwordOfSinewAndSteel.class));
        cards.add(new SetCardInfo("Sword of Truth and Justice", 229, Rarity.MYTHIC, mage.cards.s.SwordOfTruthAndJustice.class));
        cards.add(new SetCardInfo("Talisman of Creativity", 231, Rarity.UNCOMMON, mage.cards.t.TalismanOfCreativity.class));
        cards.add(new SetCardInfo("Talisman of Curiosity", 232, Rarity.COMMON, mage.cards.t.TalismanOfCuriosity.class));
        cards.add(new SetCardInfo("Talisman of Resilience", 234, Rarity.UNCOMMON, mage.cards.t.TalismanOfResilience.class));
        cards.add(new SetCardInfo("Tempered Sliver", 183, Rarity.UNCOMMON, mage.cards.t.TemperedSliver.class));
        cards.add(new SetCardInfo("The First Sliver", 200, Rarity.MYTHIC, mage.cards.t.TheFirstSliver.class));
        cards.add(new SetCardInfo("Thundering Djinn", 215, Rarity.UNCOMMON, mage.cards.t.ThunderingDjinn.class));
        cards.add(new SetCardInfo("Umezawa's Charm", 111, Rarity.COMMON, mage.cards.u.UmezawasCharm.class));
        cards.add(new SetCardInfo("Undead Augur", 112, Rarity.UNCOMMON, mage.cards.u.UndeadAugur.class));
        cards.add(new SetCardInfo("Urza, Lord High Artificer", 75, Rarity.MYTHIC, mage.cards.u.UrzaLordHighArtificer.class));
        cards.add(new SetCardInfo("Venomous Changeling", 114, Rarity.COMMON, mage.cards.v.VenomousChangeling.class));
        cards.add(new SetCardInfo("Wall of One Thousand Cuts", 36, Rarity.COMMON, mage.cards.w.WallOfOneThousandCuts.class));
        cards.add(new SetCardInfo("Waterlogged Grove", 249, Rarity.RARE, mage.cards.w.WaterloggedGrove.class));
        cards.add(new SetCardInfo("Winds of Abandon", 37, Rarity.RARE, mage.cards.w.WindsOfAbandon.class));
        cards.add(new SetCardInfo("Wing Shards", 38, Rarity.UNCOMMON, mage.cards.w.WingShards.class));
        cards.add(new SetCardInfo("Wrenn and Six", 217, Rarity.MYTHIC, mage.cards.w.WrennAndSix.class));
        cards.add(new SetCardInfo("Yawgmoth, Thran Physician", 116, Rarity.MYTHIC, mage.cards.y.YawgmothThranPhysician.class));
        cards.add(new SetCardInfo("Zhalfirin Decoy", 39, Rarity.UNCOMMON, mage.cards.z.ZhalfirinDecoy.class));
    }
}
