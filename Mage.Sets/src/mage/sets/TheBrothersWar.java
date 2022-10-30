package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class TheBrothersWar extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Arcane Proxy", "Depth Charge Colossus", "Phyrexian Fleshgorger", "Rust Goliath", "Skitterbeam Battalion");

    private static final TheBrothersWar instance = new TheBrothersWar();

    public static TheBrothersWar getInstance() {
        return instance;
    }

    private TheBrothersWar() {
        super("The Brothers' War", "BRO", ExpansionSet.buildDate(2022, 11, 18), SetType.EXPANSION);
        this.blockName = "The Brothers' War";
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Argoth, Sanctum of Nature", "256a", Rarity.RARE, mage.cards.a.ArgothSanctumOfNature.class));
        cards.add(new SetCardInfo("Argothian Opportunist", 167, Rarity.COMMON, mage.cards.a.ArgothianOpportunist.class));
        cards.add(new SetCardInfo("Ashnod's Harvester", 117, Rarity.UNCOMMON, mage.cards.a.AshnodsHarvester.class));
        cards.add(new SetCardInfo("Battlefield Forge", 257, Rarity.RARE, mage.cards.b.BattlefieldForge.class));
        cards.add(new SetCardInfo("Bitter Reunion", 127, Rarity.COMMON, mage.cards.b.BitterReunion.class));
        cards.add(new SetCardInfo("Blast Zone", 258, Rarity.RARE, mage.cards.b.BlastZone.class));
        cards.add(new SetCardInfo("Brushland", 259, Rarity.RARE, mage.cards.b.Brushland.class));
        cards.add(new SetCardInfo("Clay Revenant", 118, Rarity.COMMON, mage.cards.c.ClayRevenant.class));
        cards.add(new SetCardInfo("Depth Charge Colossus", 78, Rarity.COMMON, mage.cards.d.DepthChargeColossus.class));
        cards.add(new SetCardInfo("Drafna, Founder of Lat-Nam", 47, Rarity.RARE, mage.cards.d.DrafnaFounderOfLatNam.class));
        cards.add(new SetCardInfo("Feldon, Ronom Excavator", 135, Rarity.RARE, mage.cards.f.FeldonRonomExcavator.class));
        cards.add(new SetCardInfo("Forest", 286, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Courser", 181, Rarity.UNCOMMON, mage.cards.g.GaeasCourser.class));
        cards.add(new SetCardInfo("Giant Growth", 183, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Go for the Throat", 102, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Hall of Tagsin", 263, Rarity.RARE, mage.cards.h.HallOfTagsin.class));
        cards.add(new SetCardInfo("Hurkyl, Master Wizard", 51, Rarity.RARE, mage.cards.h.HurkylMasterWizard.class));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Wastes", 264, Rarity.RARE, mage.cards.l.LlanowarWastes.class));
        cards.add(new SetCardInfo("Mishra's Foundry", 265, Rarity.RARE, mage.cards.m.MishrasFoundry.class));
        cards.add(new SetCardInfo("Mishra's Juggernaut", 161, Rarity.COMMON, mage.cards.m.MishrasJuggernaut.class));
        cards.add(new SetCardInfo("Mishra, Claimed by Gix", 216, Rarity.MYTHIC, mage.cards.m.MishraClaimedByGix.class));
        cards.add(new SetCardInfo("Mishra, Excavation Prodigy", 140, Rarity.UNCOMMON, mage.cards.m.MishraExcavationProdigy.class));
        cards.add(new SetCardInfo("Mishra, Lost to Phyrexia", "163b", Rarity.MYTHIC, mage.cards.m.MishraLostToPhyrexia.class));
        cards.add(new SetCardInfo("Monastery Swiftspear", 144, Rarity.UNCOMMON, mage.cards.m.MonasterySwiftspear.class));
        cards.add(new SetCardInfo("Mountain", 284, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Dragon Engine", "163a", Rarity.RARE, mage.cards.p.PhyrexianDragonEngine.class));
        cards.add(new SetCardInfo("Phyrexian Fleshgorger", 121, Rarity.MYTHIC, mage.cards.p.PhyrexianFleshgorger.class));
        cards.add(new SetCardInfo("Plains", 278, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Queen Kayla bin-Kroog", 218, Rarity.RARE, mage.cards.q.QueenKaylaBinKroog.class));
        cards.add(new SetCardInfo("Recruitment Officer", 23, Rarity.UNCOMMON, mage.cards.r.RecruitmentOfficer.class));
        cards.add(new SetCardInfo("Rust Goliath", 204, Rarity.COMMON, mage.cards.r.RustGoliath.class));
        cards.add(new SetCardInfo("Scrapwork Cohort", 37, Rarity.COMMON, mage.cards.s.ScrapworkCohort.class));
        cards.add(new SetCardInfo("Skitterbeam Battalion", 165, Rarity.MYTHIC, mage.cards.s.SkitterbeamBattalion.class));
        cards.add(new SetCardInfo("Splitting the Powerstone", 63, Rarity.UNCOMMON, mage.cards.s.SplittingThePowerstone.class));
        cards.add(new SetCardInfo("Stern Lesson", 64, Rarity.COMMON, mage.cards.s.SternLesson.class));
        cards.add(new SetCardInfo("Surge Engine", 81, Rarity.MYTHIC, mage.cards.s.SurgeEngine.class));
        cards.add(new SetCardInfo("Swamp", 282, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("The Mightstone and Weakstone", "238a", Rarity.RARE, mage.cards.t.TheMightstoneAndWeakstone.class));
        cards.add(new SetCardInfo("Third Path Savant", 67, Rarity.COMMON, mage.cards.t.ThirdPathSavant.class));
        cards.add(new SetCardInfo("Titania, Gaea Incarnate", "256b", Rarity.MYTHIC, mage.cards.t.TitaniaGaeaIncarnate.class));
        cards.add(new SetCardInfo("Titania, Voice of Gaea", 193, Rarity.MYTHIC, mage.cards.t.TitaniaVoiceOfGaea.class));
        cards.add(new SetCardInfo("Tocasia's Onulet", 39, Rarity.COMMON, mage.cards.t.TocasiasOnulet.class));
        cards.add(new SetCardInfo("Underground River", 267, Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Urza's Command", 70, Rarity.RARE, mage.cards.u.UrzasCommand.class));
        cards.add(new SetCardInfo("Urza, Lord Protector", 225, Rarity.MYTHIC, mage.cards.u.UrzaLordProtector.class));
        cards.add(new SetCardInfo("Urza, Planeswalker", "238b", Rarity.MYTHIC, mage.cards.u.UrzaPlaneswalker.class));
        cards.add(new SetCardInfo("Urza, Powerstone Prodigy", 69, Rarity.UNCOMMON, mage.cards.u.UrzaPowerstoneProdigy.class));
        cards.add(new SetCardInfo("Urza, Prince of Kroog", 226, Rarity.RARE, mage.cards.u.UrzaPrinceOfKroog.class));
        cards.add(new SetCardInfo("Yotian Dissident", 227, Rarity.UNCOMMON, mage.cards.y.YotianDissident.class));
        cards.add(new SetCardInfo("Yotian Medic", 33, Rarity.COMMON, mage.cards.y.YotianMedic.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new TheBrothersWarCollator();
//    }
}
