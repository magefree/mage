package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.List;

/**
 * @author TheElk801
 */
public final class StrixhavenSchoolOfMages extends ExpansionSet {

    private static final StrixhavenSchoolOfMages instance = new StrixhavenSchoolOfMages();

    public static StrixhavenSchoolOfMages getInstance() {
        return instance;
    }

    private StrixhavenSchoolOfMages() {
        super("Strixhaven: School of Mages", "STX", ExpansionSet.buildDate(2021, 4, 23), SetType.EXPANSION);
        this.blockName = "Strixhaven: School of Mages";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 7.4;
        this.maxCardNumberInBooster = 275;

        cards.add(new SetCardInfo("Archmage Emeritus", 37, Rarity.RARE, mage.cards.a.ArchmageEmeritus.class));
        cards.add(new SetCardInfo("Dragonsguard Elite", 127, Rarity.RARE, mage.cards.d.DragonsguardElite.class));
        cards.add(new SetCardInfo("Eager First-Year", 16, Rarity.COMMON, mage.cards.e.EagerFirstYear.class));
        cards.add(new SetCardInfo("Enthusiastic Study", 99, Rarity.COMMON, mage.cards.e.EnthusiasticStudy.class));
        cards.add(new SetCardInfo("Environmental Sciences", 1, Rarity.COMMON, mage.cards.e.EnvironmentalSciences.class));
        cards.add(new SetCardInfo("Expanded Anatomy", 2, Rarity.COMMON, mage.cards.e.ExpandedAnatomy.class));
        cards.add(new SetCardInfo("Field Trip", 131, Rarity.COMMON, mage.cards.f.FieldTrip.class));
        cards.add(new SetCardInfo("Forest", 374, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 375, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frostboil Snarl", 265, Rarity.RARE, mage.cards.f.FrostboilSnarl.class));
        cards.add(new SetCardInfo("Furycalm Snarl", 266, Rarity.RARE, mage.cards.f.FurycalmSnarl.class));
        cards.add(new SetCardInfo("Introduction to Annihilation", 3, Rarity.COMMON, mage.cards.i.IntroductionToAnnihilation.class));
        cards.add(new SetCardInfo("Introduction to Prophecy", 4, Rarity.COMMON, mage.cards.i.IntroductionToProphecy.class));
        cards.add(new SetCardInfo("Island", 368, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 369, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kasmina, Enigma Sage", 196, Rarity.MYTHIC, mage.cards.k.KasminaEnigmaSage.class));
        cards.add(new SetCardInfo("Kianne, Dean of Substance", 152, Rarity.RARE, mage.cards.k.KianneDeanOfSubstance.class));
        cards.add(new SetCardInfo("Lorehold Apprentice", 198, Rarity.UNCOMMON, mage.cards.l.LoreholdApprentice.class));
        cards.add(new SetCardInfo("Lorehold Command", 199, Rarity.RARE, mage.cards.l.LoreholdCommand.class));
        cards.add(new SetCardInfo("Mountain", 372, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 373, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necroblossom Snarl", 269, Rarity.RARE, mage.cards.n.NecroblossomSnarl.class));
        cards.add(new SetCardInfo("Pest Summoning", 211, Rarity.COMMON, mage.cards.p.PestSummoning.class));
        cards.add(new SetCardInfo("Plains", 366, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 367, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pop Quiz", 49, Rarity.COMMON, mage.cards.p.PopQuiz.class));
        cards.add(new SetCardInfo("Prismari Apprentice", 213, Rarity.UNCOMMON, mage.cards.p.PrismariApprentice.class));
        cards.add(new SetCardInfo("Prismari Command", 214, Rarity.RARE, mage.cards.p.PrismariCommand.class));
        cards.add(new SetCardInfo("Professor Onyx", 83, Rarity.MYTHIC, mage.cards.p.ProfessorOnyx.class));
        cards.add(new SetCardInfo("Professor of Symbology", 24, Rarity.UNCOMMON, mage.cards.p.ProfessorOfSymbology.class));
        cards.add(new SetCardInfo("Quandrix Apprentice", 216, Rarity.UNCOMMON, mage.cards.q.QuandrixApprentice.class));
        cards.add(new SetCardInfo("Quandrix Command", 217, Rarity.RARE, mage.cards.q.QuandrixCommand.class));
        cards.add(new SetCardInfo("Rip Apart", 318, Rarity.UNCOMMON, mage.cards.r.RipApart.class));
        cards.add(new SetCardInfo("Rise of Extus", 226, Rarity.COMMON, mage.cards.r.RiseOfExtus.class));
        cards.add(new SetCardInfo("Shineshadow Snarl", 272, Rarity.RARE, mage.cards.s.ShineshadowSnarl.class));
        cards.add(new SetCardInfo("Silverquill Apprentice", 231, Rarity.UNCOMMON, mage.cards.s.SilverquillApprentice.class));
        cards.add(new SetCardInfo("Silverquill Command", 232, Rarity.RARE, mage.cards.s.SilverquillCommand.class));
        cards.add(new SetCardInfo("Star Pupil", 30, Rarity.COMMON, mage.cards.s.StarPupil.class));
        cards.add(new SetCardInfo("Storm-Kiln Artist", 115, Rarity.UNCOMMON, mage.cards.s.StormKilnArtist.class));
        cards.add(new SetCardInfo("Study Break", 34, Rarity.COMMON, mage.cards.s.StudyBreak.class));
        cards.add(new SetCardInfo("Swamp", 370, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 371, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torrent Sculptor", 159, Rarity.RARE, mage.cards.t.TorrentSculptor.class));
        cards.add(new SetCardInfo("Valentin, Dean of the Vein", 161, Rarity.RARE, mage.cards.v.ValentinDeanOfTheVein.class));
        cards.add(new SetCardInfo("Venerable Warsinger", 355, Rarity.RARE, mage.cards.v.VenerableWarsinger.class));
        cards.add(new SetCardInfo("Vineglimmer Snarl", 274, Rarity.RARE, mage.cards.v.VineglimmerSnarl.class));
        cards.add(new SetCardInfo("Waterfall Aerialist", 61, Rarity.COMMON, mage.cards.w.WaterfallAerialist.class));
        cards.add(new SetCardInfo("Witherbloom Apprentice", 247, Rarity.UNCOMMON, mage.cards.w.WitherbloomApprentice.class));
        cards.add(new SetCardInfo("Witherbloom Command", 248, Rarity.RARE, mage.cards.w.WitherbloomCommand.class));
    }

    @Override
    public List<Card> tryBooster() {
        List<Card> booster = super.tryBooster();
        addArchive(booster);
        addLesson(booster);
        return booster;
    }

    private void addArchive(List<Card> booster) {
        // Boosters have one card from STA, odds are 2/3 for uncommon, 4/15 for rare, 1/15 for mythic
        final Rarity rarity;
        int i = RandomUtil.nextInt(15);
        if (i == 14) {
            rarity = Rarity.MYTHIC;
        } else if (i >= 10) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.UNCOMMON;
        }
        addToBooster(booster, StrixhavenMysticalArchive.getInstance().getCardsByRarity(rarity));
    }

    private void addLesson(List<Card> booster) {
        // Boosters have one Lesson card
        final Rarity rarity;
        int i = RandomUtil.nextInt(15);
        if (i == 14) { // TODO: change this when correct ratio is known
            rarity = Rarity.MYTHIC;
        } else if (i >= 10) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.COMMON;
        }
        List<CardInfo> cards = super.getCardsByRarity(rarity);
        cards.removeIf(cardInfo -> !cardInfo.getCard().hasSubtype(SubType.LESSON, null));
        addToBooster(booster, cards);
    }

    @Override
    public List<CardInfo> getCardsByRarity(Rarity rarity) {
        List<CardInfo> cards = super.getCardsByRarity(rarity);
        cards.removeIf(cardInfo -> cardInfo.getCard().hasSubtype(SubType.LESSON, null));
        return cards;
    }
}
