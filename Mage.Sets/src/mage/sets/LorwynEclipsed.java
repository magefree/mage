package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class LorwynEclipsed extends ExpansionSet {

    private static final LorwynEclipsed instance = new LorwynEclipsed();

    public static LorwynEclipsed getInstance() {
        return instance;
    }

    private LorwynEclipsed() {
        super("Lorwyn Eclipsed", "ECL", ExpansionSet.buildDate(2026, 1, 23), SetType.EXPANSION);
        this.blockName = "Lorwyn Eclipsed"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Appeal to Eirdu", 5, Rarity.COMMON, mage.cards.a.AppealToEirdu.class));
        cards.add(new SetCardInfo("Ashling's Command", 205, Rarity.RARE, mage.cards.a.AshlingsCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashling's Command", 330, Rarity.RARE, mage.cards.a.AshlingsCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashling, Rekindled", 124, Rarity.RARE, mage.cards.a.AshlingRekindled.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashling, Rekindled", 290, Rarity.RARE, mage.cards.a.AshlingRekindled.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurora Awakener", 165, Rarity.MYTHIC, mage.cards.a.AuroraAwakener.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurora Awakener", 323, Rarity.MYTHIC, mage.cards.a.AuroraAwakener.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bile-Vial Boggart", 87, Rarity.COMMON, mage.cards.b.BileVialBoggart.class));
        cards.add(new SetCardInfo("Bitterbloom Bearer", 310, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterbloom Bearer", 352, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterbloom Bearer", 88, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", "349b", Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 262, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 349, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloom Tender", 166, Rarity.MYTHIC, mage.cards.b.BloomTender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloom Tender", 324, Rarity.MYTHIC, mage.cards.b.BloomTender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloom Tender", 390, Rarity.MYTHIC, mage.cards.b.BloomTender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloom Tender", 400, Rarity.MYTHIC, mage.cards.b.BloomTender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blossoming Defense", 167, Rarity.UNCOMMON, mage.cards.b.BlossomingDefense.class));
        cards.add(new SetCardInfo("Boggart Mischief", 92, Rarity.UNCOMMON, mage.cards.b.BoggartMischief.class));
        cards.add(new SetCardInfo("Champion of the Clachan", 353, Rarity.RARE, mage.cards.c.ChampionOfTheClachan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Champion of the Clachan", 9, Rarity.RARE, mage.cards.c.ChampionOfTheClachan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Champion of the Weird", 360, Rarity.RARE, mage.cards.c.ChampionOfTheWeird.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Champion of the Weird", 95, Rarity.RARE, mage.cards.c.ChampionOfTheWeird.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Champions of the Perfect", 171, Rarity.RARE, mage.cards.c.ChampionsOfThePerfect.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Champions of the Perfect", 365, Rarity.RARE, mage.cards.c.ChampionsOfThePerfect.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chomping Changeling", 172, Rarity.UNCOMMON, mage.cards.c.ChompingChangeling.class));
        cards.add(new SetCardInfo("Crib Swap", 11, Rarity.UNCOMMON, mage.cards.c.CribSwap.class));
        cards.add(new SetCardInfo("Crossroads Watcher", 173, Rarity.COMMON, mage.cards.c.CrossroadsWatcher.class));
        cards.add(new SetCardInfo("Deceit", 212, Rarity.MYTHIC, mage.cards.d.Deceit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deceit", 293, Rarity.MYTHIC, mage.cards.d.Deceit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dose of Dawnglow", 100, Rarity.UNCOMMON, mage.cards.d.DoseOfDawnglow.class));
        cards.add(new SetCardInfo("Dream Seizer", 101, Rarity.COMMON, mage.cards.d.DreamSeizer.class));
        cards.add(new SetCardInfo("Eclipsed Flamekin", 219, Rarity.UNCOMMON, mage.cards.e.EclipsedFlamekin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eclipsed Flamekin", 337, Rarity.UNCOMMON, mage.cards.e.EclipsedFlamekin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eirdu, Carrier of Dawn", 13, Rarity.MYTHIC, mage.cards.e.EirduCarrierOfDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eirdu, Carrier of Dawn", 286, Rarity.MYTHIC, mage.cards.e.EirduCarrierOfDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elder Auntie", 133, Rarity.COMMON, mage.cards.e.ElderAuntie.class));
        cards.add(new SetCardInfo("Emptiness", 222, Rarity.MYTHIC, mage.cards.e.Emptiness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emptiness", 294, Rarity.MYTHIC, mage.cards.e.Emptiness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Evolving Wilds", 264, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Explosive Prodigy", 136, Rarity.UNCOMMON, mage.cards.e.ExplosiveProdigy.class));
        cards.add(new SetCardInfo("Figure of Fable", 224, Rarity.RARE, mage.cards.f.FigureOfFable.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Figure of Fable", 372, Rarity.RARE, mage.cards.f.FigureOfFable.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 273, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 283, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Formidable Speaker", 176, Rarity.RARE, mage.cards.f.FormidableSpeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Formidable Speaker", 366, Rarity.RARE, mage.cards.f.FormidableSpeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gallant Fowlknight", 17, Rarity.COMMON, mage.cards.g.GallantFowlknight.class));
        cards.add(new SetCardInfo("Gangly Stompling", 226, Rarity.COMMON, mage.cards.g.GanglyStompling.class));
        cards.add(new SetCardInfo("Goatnap", 142, Rarity.UNCOMMON, mage.cards.g.Goatnap.class));
        cards.add(new SetCardInfo("Great Forest Druid", 178, Rarity.COMMON, mage.cards.g.GreatForestDruid.class));
        cards.add(new SetCardInfo("Hallowed Fountain", "347b", Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 265, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 347, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("High Perfect Morcant", 229, Rarity.RARE, mage.cards.h.HighPerfectMorcant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("High Perfect Morcant", 373, Rarity.RARE, mage.cards.h.HighPerfectMorcant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron-Shield Elf", 108, Rarity.UNCOMMON, mage.cards.i.IronShieldElf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron-Shield Elf", 404, Rarity.UNCOMMON, mage.cards.i.IronShieldElf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 275, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kirol, Attentive First-Year", 231, Rarity.RARE, mage.cards.k.KirolAttentiveFirstYear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kirol, Attentive First-Year", 374, Rarity.RARE, mage.cards.k.KirolAttentiveFirstYear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lys Alana Informant", 181, Rarity.COMMON, mage.cards.l.LysAlanaInformant.class));
        cards.add(new SetCardInfo("Midnight Tilling", 182, Rarity.COMMON, mage.cards.m.MidnightTilling.class));
        cards.add(new SetCardInfo("Moonglove Extractor", 109, Rarity.COMMON, mage.cards.m.MoongloveExtractor.class));
        cards.add(new SetCardInfo("Morningtide's Light", 27, Rarity.MYTHIC, mage.cards.m.MorningtidesLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Morningtide's Light", 301, Rarity.MYTHIC, mage.cards.m.MorningtidesLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 272, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 277, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 282, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutable Explorer", 186, Rarity.RARE, mage.cards.m.MutableExplorer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutable Explorer", 327, Rarity.RARE, mage.cards.m.MutableExplorer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", "350b", Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 266, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 350, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Perfect Intimidation", 115, Rarity.UNCOMMON, mage.cards.p.PerfectIntimidation.class));
        cards.add(new SetCardInfo("Plains", 269, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 274, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 279, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prideful Feastling", 238, Rarity.COMMON, mage.cards.p.PridefulFeastling.class));
        cards.add(new SetCardInfo("Run Away Together", 67, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Sanar, Innovative First-Year", 241, Rarity.RARE, mage.cards.s.SanarInnovativeFirstYear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanar, Innovative First-Year", 378, Rarity.RARE, mage.cards.s.SanarInnovativeFirstYear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sear", 154, Rarity.UNCOMMON, mage.cards.s.Sear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sear", 405, Rarity.UNCOMMON, mage.cards.s.Sear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shimmercreep", 120, Rarity.UNCOMMON, mage.cards.s.Shimmercreep.class));
        cards.add(new SetCardInfo("Shinestriker", 68, Rarity.UNCOMMON, mage.cards.s.Shinestriker.class));
        cards.add(new SetCardInfo("Shore Lurker", 34, Rarity.COMMON, mage.cards.s.ShoreLurker.class));
        cards.add(new SetCardInfo("Silvergill Mentor", 403, Rarity.UNCOMMON, mage.cards.s.SilvergillMentor.class));
        cards.add(new SetCardInfo("Silvergill Peddler", 70, Rarity.COMMON, mage.cards.s.SilvergillPeddler.class));
        cards.add(new SetCardInfo("Sizzling Changeling", 155, Rarity.UNCOMMON, mage.cards.s.SizzlingChangeling.class));
        cards.add(new SetCardInfo("Sourbread Auntie", 158, Rarity.UNCOMMON, mage.cards.s.SourbreadAuntie.class));
        cards.add(new SetCardInfo("Spell Snare", 71, Rarity.UNCOMMON, mage.cards.s.SpellSnare.class));
        cards.add(new SetCardInfo("Springleaf Drum", 260, Rarity.UNCOMMON, mage.cards.s.SpringleafDrum.class));
        cards.add(new SetCardInfo("Steam Vents", "348b", Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 267, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 348, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sun-Dappled Celebrant", 37, Rarity.COMMON, mage.cards.s.SunDappledCelebrant.class));
        cards.add(new SetCardInfo("Swamp", 271, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 276, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 281, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sygg, Wanderwine Wisdom", 288, Rarity.RARE, mage.cards.s.SyggWanderwineWisdom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sygg, Wanderwine Wisdom", 76, Rarity.RARE, mage.cards.s.SyggWanderwineWisdom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", "351b", Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 268, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 351, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temporal Cleansing", 78, Rarity.COMMON, mage.cards.t.TemporalCleansing.class));
        cards.add(new SetCardInfo("Thoughtweft Lieutenant", 246, Rarity.UNCOMMON, mage.cards.t.ThoughtweftLieutenant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thoughtweft Lieutenant", 343, Rarity.UNCOMMON, mage.cards.t.ThoughtweftLieutenant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tweeze", 162, Rarity.COMMON, mage.cards.t.Tweeze.class));
        cards.add(new SetCardInfo("Unexpected Assistance", 80, Rarity.COMMON, mage.cards.u.UnexpectedAssistance.class));
        cards.add(new SetCardInfo("Unwelcome Sprite", 81, Rarity.UNCOMMON, mage.cards.u.UnwelcomeSprite.class));
        cards.add(new SetCardInfo("Vibrance", 249, Rarity.MYTHIC, mage.cards.v.Vibrance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vibrance", 295, Rarity.MYTHIC, mage.cards.v.Vibrance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vinebred Brawler", 201, Rarity.UNCOMMON, mage.cards.v.VinebredBrawler.class));
        cards.add(new SetCardInfo("Virulent Emissary", 202, Rarity.UNCOMMON, mage.cards.v.VirulentEmissary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Virulent Emissary", 406, Rarity.UNCOMMON, mage.cards.v.VirulentEmissary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voracious Tome-Skimmer", 250, Rarity.UNCOMMON, mage.cards.v.VoraciousTomeSkimmer.class));
        cards.add(new SetCardInfo("Wanderbrine Preacher", 41, Rarity.COMMON, mage.cards.w.WanderbrinePreacher.class));
        cards.add(new SetCardInfo("Wanderwine Distracter", 82, Rarity.COMMON, mage.cards.w.WanderwineDistracter.class));
        cards.add(new SetCardInfo("Warren Torchmaster", 163, Rarity.UNCOMMON, mage.cards.w.WarrenTorchmaster.class));
    }
}
