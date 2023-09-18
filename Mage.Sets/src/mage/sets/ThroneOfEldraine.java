package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class ThroneOfEldraine extends ExpansionSet {

    private static final ThroneOfEldraine instance = new ThroneOfEldraine();

    public static ThroneOfEldraine getInstance() {
        return instance;
    }

    private ThroneOfEldraine() {
        super("Throne of Eldraine", "ELD", ExpansionSet.buildDate(2019, 10, 4), SetType.EXPANSION);
        this.blockName = "Throne of Eldraine";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 269;

        cards.add(new SetCardInfo("Acclaimed Contender", 1, Rarity.RARE, mage.cards.a.AcclaimedContender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Acclaimed Contender", 334, Rarity.RARE, mage.cards.a.AcclaimedContender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alela, Artful Provocateur", 324, Rarity.MYTHIC, mage.cards.a.AlelaArtfulProvocateur.class));
        cards.add(new SetCardInfo("All That Glitters", 2, Rarity.UNCOMMON, mage.cards.a.AllThatGlitters.class));
        cards.add(new SetCardInfo("Animating Faerie", 280, Rarity.UNCOMMON, mage.cards.a.AnimatingFaerie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Animating Faerie", 38, Rarity.UNCOMMON, mage.cards.a.AnimatingFaerie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Signet", 331, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Arcanist's Owl", 206, Rarity.UNCOMMON, mage.cards.a.ArcanistsOwl.class));
        cards.add(new SetCardInfo("Archon of Absolution", 3, Rarity.UNCOMMON, mage.cards.a.ArchonOfAbsolution.class));
        cards.add(new SetCardInfo("Ardenvale Paladin", 4, Rarity.COMMON, mage.cards.a.ArdenvalePaladin.class));
        cards.add(new SetCardInfo("Ardenvale Tactician", 273, Rarity.COMMON, mage.cards.a.ArdenvaleTactician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ardenvale Tactician", 5, Rarity.COMMON, mage.cards.a.ArdenvaleTactician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ayara, First of Locthwain", 350, Rarity.RARE, mage.cards.a.AyaraFirstOfLocthwain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ayara, First of Locthwain", 75, Rarity.RARE, mage.cards.a.AyaraFirstOfLocthwain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bake into a Pie", 76, Rarity.COMMON, mage.cards.b.BakeIntoAPie.class));
        cards.add(new SetCardInfo("Banish into Fable", 325, Rarity.RARE, mage.cards.b.BanishIntoFable.class));
        cards.add(new SetCardInfo("Barge In", 112, Rarity.COMMON, mage.cards.b.BargeIn.class));
        cards.add(new SetCardInfo("Barrow Witches", 77, Rarity.COMMON, mage.cards.b.BarrowWitches.class));
        cards.add(new SetCardInfo("Bartered Cow", 6, Rarity.COMMON, mage.cards.b.BarteredCow.class));
        cards.add(new SetCardInfo("Beanstalk Giant", 149, Rarity.UNCOMMON, mage.cards.b.BeanstalkGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Beanstalk Giant", 295, Rarity.UNCOMMON, mage.cards.b.BeanstalkGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Belle of the Brawl", 78, Rarity.UNCOMMON, mage.cards.b.BelleOfTheBrawl.class));
        cards.add(new SetCardInfo("Beloved Princess", 7, Rarity.COMMON, mage.cards.b.BelovedPrincess.class));
        cards.add(new SetCardInfo("Blacklance Paragon", 351, Rarity.RARE, mage.cards.b.BlacklanceParagon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blacklance Paragon", 79, Rarity.RARE, mage.cards.b.BlacklanceParagon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodhaze Wolverine", 113, Rarity.COMMON, mage.cards.b.BloodhazeWolverine.class));
        cards.add(new SetCardInfo("Blow Your House Down", 114, Rarity.COMMON, mage.cards.b.BlowYourHouseDown.class));
        cards.add(new SetCardInfo("Bog Naughty", 80, Rarity.UNCOMMON, mage.cards.b.BogNaughty.class));
        cards.add(new SetCardInfo("Bonecrusher Giant", 115, Rarity.RARE, mage.cards.b.BonecrusherGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bonecrusher Giant", 291, Rarity.RARE, mage.cards.b.BonecrusherGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bramblefort Fink", 311, Rarity.UNCOMMON, mage.cards.b.BramblefortFink.class));
        cards.add(new SetCardInfo("Brazen Borrower", 281, Rarity.MYTHIC, mage.cards.b.BrazenBorrower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brazen Borrower", 39, Rarity.MYTHIC, mage.cards.b.BrazenBorrower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brimstone Trebuchet", 116, Rarity.COMMON, mage.cards.b.BrimstoneTrebuchet.class));
        cards.add(new SetCardInfo("Burning-Yard Trainer", 117, Rarity.UNCOMMON, mage.cards.b.BurningYardTrainer.class));
        cards.add(new SetCardInfo("Castle Ardenvale", 238, Rarity.RARE, mage.cards.c.CastleArdenvale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Ardenvale", 386, Rarity.RARE, mage.cards.c.CastleArdenvale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Embereth", 239, Rarity.RARE, mage.cards.c.CastleEmbereth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Embereth", 387, Rarity.RARE, mage.cards.c.CastleEmbereth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Garenbrig", 240, Rarity.RARE, mage.cards.c.CastleGarenbrig.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Garenbrig", 388, Rarity.RARE, mage.cards.c.CastleGarenbrig.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Locthwain", 241, Rarity.RARE, mage.cards.c.CastleLocthwain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Locthwain", 389, Rarity.RARE, mage.cards.c.CastleLocthwain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Vantress", 242, Rarity.RARE, mage.cards.c.CastleVantress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Vantress", 390, Rarity.RARE, mage.cards.c.CastleVantress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cauldron Familiar", 81, Rarity.UNCOMMON, mage.cards.c.CauldronFamiliar.class));
        cards.add(new SetCardInfo("Cauldron's Gift", 83, Rarity.UNCOMMON, mage.cards.c.CauldronsGift.class));
        cards.add(new SetCardInfo("Charmed Sleep", 40, Rarity.COMMON, mage.cards.c.CharmedSleep.class));
        cards.add(new SetCardInfo("Charming Prince", 335, Rarity.RARE, mage.cards.c.CharmingPrince.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Charming Prince", 8, Rarity.RARE, mage.cards.c.CharmingPrince.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chittering Witch", 319, Rarity.RARE, mage.cards.c.ChitteringWitch.class));
        cards.add(new SetCardInfo("Chulane, Teller of Tales", 326, Rarity.MYTHIC, mage.cards.c.ChulaneTellerOfTales.class));
        cards.add(new SetCardInfo("Clackbridge Troll", 353, Rarity.RARE, mage.cards.c.ClackbridgeTroll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clackbridge Troll", 84, Rarity.RARE, mage.cards.c.ClackbridgeTroll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Claim the Firstborn", 118, Rarity.UNCOMMON, mage.cards.c.ClaimTheFirstborn.class));
        cards.add(new SetCardInfo("Clockwork Servant", 216, Rarity.UNCOMMON, mage.cards.c.ClockworkServant.class));
        cards.add(new SetCardInfo("Command Tower", 333, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Corridor Monitor", 41, Rarity.COMMON, mage.cards.c.CorridorMonitor.class));
        cards.add(new SetCardInfo("Covetous Urge", 207, Rarity.UNCOMMON, mage.cards.c.CovetousUrge.class));
        cards.add(new SetCardInfo("Crashing Drawbridge", 217, Rarity.COMMON, mage.cards.c.CrashingDrawbridge.class));
        cards.add(new SetCardInfo("Crystal Slipper", 119, Rarity.COMMON, mage.cards.c.CrystalSlipper.class));
        cards.add(new SetCardInfo("Curious Pair", 150, Rarity.COMMON, mage.cards.c.CuriousPair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curious Pair", 296, Rarity.COMMON, mage.cards.c.CuriousPair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dance of the Manse", 186, Rarity.RARE, mage.cards.d.DanceOfTheManse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dance of the Manse", 377, Rarity.RARE, mage.cards.d.DanceOfTheManse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deafening Silence", 10, Rarity.UNCOMMON, mage.cards.d.DeafeningSilence.class));
        cards.add(new SetCardInfo("Deathless Knight", 208, Rarity.UNCOMMON, mage.cards.d.DeathlessKnight.class));
        cards.add(new SetCardInfo("Didn't Say Please", 42, Rarity.COMMON, mage.cards.d.DidntSayPlease.class));
        cards.add(new SetCardInfo("Doom Foretold", 187, Rarity.RARE, mage.cards.d.DoomForetold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doom Foretold", 378, Rarity.RARE, mage.cards.d.DoomForetold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drown in the Loch", 188, Rarity.UNCOMMON, mage.cards.d.DrownInTheLoch.class));
        cards.add(new SetCardInfo("Dwarven Mine", 243, Rarity.COMMON, mage.cards.d.DwarvenMine.class));
        cards.add(new SetCardInfo("Edgewall Innkeeper", 151, Rarity.UNCOMMON, mage.cards.e.EdgewallInnkeeper.class));
        cards.add(new SetCardInfo("Elite Headhunter", 209, Rarity.UNCOMMON, mage.cards.e.EliteHeadhunter.class));
        cards.add(new SetCardInfo("Embercleave", 120, Rarity.MYTHIC, mage.cards.e.Embercleave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Embercleave", 359, Rarity.MYTHIC, mage.cards.e.Embercleave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Embereth Paladin", 121, Rarity.COMMON, mage.cards.e.EmberethPaladin.class));
        cards.add(new SetCardInfo("Embereth Shieldbreaker", 122, Rarity.UNCOMMON, mage.cards.e.EmberethShieldbreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Embereth Shieldbreaker", 292, Rarity.UNCOMMON, mage.cards.e.EmberethShieldbreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Embereth Skyblazer", 321, Rarity.RARE, mage.cards.e.EmberethSkyblazer.class));
        cards.add(new SetCardInfo("Emry, Lurker of the Loch", 342, Rarity.RARE, mage.cards.e.EmryLurkerOfTheLoch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emry, Lurker of the Loch", 43, Rarity.RARE, mage.cards.e.EmryLurkerOfTheLoch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enchanted Carriage", 218, Rarity.UNCOMMON, mage.cards.e.EnchantedCarriage.class));
        cards.add(new SetCardInfo("Epic Downfall", 85, Rarity.UNCOMMON, mage.cards.e.EpicDownfall.class));
        cards.add(new SetCardInfo("Escape to the Wilds", 189, Rarity.RARE, mage.cards.e.EscapeToTheWilds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Escape to the Wilds", 379, Rarity.RARE, mage.cards.e.EscapeToTheWilds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eye Collector", 86, Rarity.COMMON, mage.cards.e.EyeCollector.class));
        cards.add(new SetCardInfo("Fabled Passage", 244, Rarity.RARE, mage.cards.f.FabledPassage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fabled Passage", 391, Rarity.RARE, mage.cards.f.FabledPassage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fae of Wishes", 282, Rarity.RARE, mage.cards.f.FaeOfWishes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fae of Wishes", 44, Rarity.RARE, mage.cards.f.FaeOfWishes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faeburrow Elder", 190, Rarity.RARE, mage.cards.f.FaeburrowElder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faeburrow Elder", 380, Rarity.RARE, mage.cards.f.FaeburrowElder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faerie Formation", 316, Rarity.RARE, mage.cards.f.FaerieFormation.class));
        cards.add(new SetCardInfo("Faerie Guidemother", 11, Rarity.COMMON, mage.cards.f.FaerieGuidemother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faerie Guidemother", 274, Rarity.COMMON, mage.cards.f.FaerieGuidemother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faerie Vandal", 45, Rarity.UNCOMMON, mage.cards.f.FaerieVandal.class));
        cards.add(new SetCardInfo("Feasting Troll King", 152, Rarity.RARE, mage.cards.f.FeastingTrollKing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feasting Troll King", 368, Rarity.RARE, mage.cards.f.FeastingTrollKing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fell the Pheasant", 153, Rarity.COMMON, mage.cards.f.FellThePheasant.class));
        cards.add(new SetCardInfo("Ferocity of the Wilds", 123, Rarity.UNCOMMON, mage.cards.f.FerocityOfTheWilds.class));
        cards.add(new SetCardInfo("Fervent Champion", 124, Rarity.RARE, mage.cards.f.FerventChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fervent Champion", 360, Rarity.RARE, mage.cards.f.FerventChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Festive Funeral", 87, Rarity.COMMON, mage.cards.f.FestiveFuneral.class));
        cards.add(new SetCardInfo("Fierce Witchstalker", 154, Rarity.COMMON, mage.cards.f.FierceWitchstalker.class));
        cards.add(new SetCardInfo("Fireborn Knight", 210, Rarity.UNCOMMON, mage.cards.f.FirebornKnight.class));
        cards.add(new SetCardInfo("Fires of Invention", 125, Rarity.RARE, mage.cards.f.FiresOfInvention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fires of Invention", 361, Rarity.RARE, mage.cards.f.FiresOfInvention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flaxen Intruder", 155, Rarity.UNCOMMON, mage.cards.f.FlaxenIntruder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flaxen Intruder", 297, Rarity.UNCOMMON, mage.cards.f.FlaxenIntruder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fling", 126, Rarity.COMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Flutterfox", 12, Rarity.COMMON, mage.cards.f.Flutterfox.class));
        cards.add(new SetCardInfo("Folio of Fancies", 343, Rarity.RARE, mage.cards.f.FolioOfFancies.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Folio of Fancies", 46, Rarity.RARE, mage.cards.f.FolioOfFancies.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foreboding Fruit", 88, Rarity.COMMON, mage.cards.f.ForebodingFruit.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 267, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 268, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forever Young", 89, Rarity.COMMON, mage.cards.f.ForeverYoung.class));
        cards.add(new SetCardInfo("Fortifying Provisions", 13, Rarity.COMMON, mage.cards.f.FortifyingProvisions.class));
        cards.add(new SetCardInfo("Foulmire Knight", 286, Rarity.UNCOMMON, mage.cards.f.FoulmireKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foulmire Knight", 90, Rarity.UNCOMMON, mage.cards.f.FoulmireKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frogify", 47, Rarity.UNCOMMON, mage.cards.f.Frogify.class));
        cards.add(new SetCardInfo("Gadwick, the Wizened", 344, Rarity.RARE, mage.cards.g.GadwickTheWizened.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gadwick, the Wizened", 48, Rarity.RARE, mage.cards.g.GadwickTheWizened.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garenbrig Carver", 156, Rarity.COMMON, mage.cards.g.GarenbrigCarver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garenbrig Carver", 298, Rarity.COMMON, mage.cards.g.GarenbrigCarver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garenbrig Paladin", 157, Rarity.COMMON, mage.cards.g.GarenbrigPaladin.class));
        cards.add(new SetCardInfo("Garenbrig Squire", 158, Rarity.COMMON, mage.cards.g.GarenbrigSquire.class));
        cards.add(new SetCardInfo("Garrison Griffin", 305, Rarity.COMMON, mage.cards.g.GarrisonGriffin.class));
        cards.add(new SetCardInfo("Garruk, Cursed Huntsman", 191, Rarity.MYTHIC, mage.cards.g.GarrukCursedHuntsman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk, Cursed Huntsman", 270, Rarity.MYTHIC, mage.cards.g.GarrukCursedHuntsman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Killer", 14, Rarity.RARE, mage.cards.g.GiantKiller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Killer", 275, Rarity.RARE, mage.cards.g.GiantKiller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Opportunity", 159, Rarity.UNCOMMON, mage.cards.g.GiantOpportunity.class));
        cards.add(new SetCardInfo("Giant's Skewer", 91, Rarity.COMMON, mage.cards.g.GiantsSkewer.class));
        cards.add(new SetCardInfo("Gilded Goose", 160, Rarity.RARE, mage.cards.g.GildedGoose.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gilded Goose", 369, Rarity.RARE, mage.cards.g.GildedGoose.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gingerbread Cabin", 245, Rarity.COMMON, mage.cards.g.GingerbreadCabin.class));
        cards.add(new SetCardInfo("Gingerbrute", 219, Rarity.COMMON, mage.cards.g.Gingerbrute.class));
        cards.add(new SetCardInfo("Glass Casket", 15, Rarity.UNCOMMON, mage.cards.g.GlassCasket.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glass Casket", 393, Rarity.UNCOMMON, mage.cards.g.GlassCasket.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gluttonous Troll", 327, Rarity.RARE, mage.cards.g.GluttonousTroll.class));
        cards.add(new SetCardInfo("Golden Egg", 220, Rarity.COMMON, mage.cards.g.GoldenEgg.class));
        cards.add(new SetCardInfo("Grumgully, the Generous", 192, Rarity.UNCOMMON, mage.cards.g.GrumgullyTheGenerous.class));
        cards.add(new SetCardInfo("Happily Ever After", 16, Rarity.RARE, mage.cards.h.HappilyEverAfter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Happily Ever After", 337, Rarity.RARE, mage.cards.h.HappilyEverAfter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harmonious Archon", 17, Rarity.MYTHIC, mage.cards.h.HarmoniousArchon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harmonious Archon", 338, Rarity.MYTHIC, mage.cards.h.HarmoniousArchon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Henge Walker", 221, Rarity.COMMON, mage.cards.h.HengeWalker.class));
        cards.add(new SetCardInfo("Heraldic Banner", 222, Rarity.UNCOMMON, mage.cards.h.HeraldicBanner.class));
        cards.add(new SetCardInfo("Hushbringer", 18, Rarity.RARE, mage.cards.h.Hushbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hushbringer", 339, Rarity.RARE, mage.cards.h.Hushbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hypnotic Sprite", 283, Rarity.UNCOMMON, mage.cards.h.HypnoticSprite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hypnotic Sprite", 49, Rarity.UNCOMMON, mage.cards.h.HypnoticSprite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Idyllic Grange", 246, Rarity.COMMON, mage.cards.i.IdyllicGrange.class));
        cards.add(new SetCardInfo("Improbable Alliance", 193, Rarity.UNCOMMON, mage.cards.i.ImprobableAlliance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Improbable Alliance", 396, Rarity.UNCOMMON, mage.cards.i.ImprobableAlliance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inquisitive Puppet", 223, Rarity.UNCOMMON, mage.cards.i.InquisitivePuppet.class));
        cards.add(new SetCardInfo("Insatiable Appetite", 162, Rarity.COMMON, mage.cards.i.InsatiableAppetite.class));
        cards.add(new SetCardInfo("Inspiring Veteran", 194, Rarity.UNCOMMON, mage.cards.i.InspiringVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspiring Veteran", 397, Rarity.UNCOMMON, mage.cards.i.InspiringVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Into the Story", 50, Rarity.UNCOMMON, mage.cards.i.IntoTheStory.class));
        cards.add(new SetCardInfo("Irencrag Feat", 127, Rarity.RARE, mage.cards.i.IrencragFeat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Irencrag Feat", 362, Rarity.RARE, mage.cards.i.IrencragFeat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Irencrag Pyromancer", 128, Rarity.RARE, mage.cards.i.IrencragPyromancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Irencrag Pyromancer", 363, Rarity.RARE, mage.cards.i.IrencragPyromancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 256, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 257, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Joust", 129, Rarity.UNCOMMON, mage.cards.j.Joust.class));
        cards.add(new SetCardInfo("Jousting Dummy", 224, Rarity.COMMON, mage.cards.j.JoustingDummy.class));
        cards.add(new SetCardInfo("Keeper of Fables", 163, Rarity.UNCOMMON, mage.cards.k.KeeperOfFables.class));
        cards.add(new SetCardInfo("Kenrith's Transformation", 164, Rarity.UNCOMMON, mage.cards.k.KenrithsTransformation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kenrith's Transformation", 395, Rarity.UNCOMMON, mage.cards.k.KenrithsTransformation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kenrith, the Returned King", 303, Rarity.MYTHIC, mage.cards.k.KenrithTheReturnedKing.class));
        cards.add(new SetCardInfo("Knight of the Keep", 19, Rarity.COMMON, mage.cards.k.KnightOfTheKeep.class));
        cards.add(new SetCardInfo("Knights' Charge", 328, Rarity.RARE, mage.cards.k.KnightsCharge.class));
        cards.add(new SetCardInfo("Korvold, Fae-Cursed King", 329, Rarity.MYTHIC, mage.cards.k.KorvoldFaeCursedKing.class));
        cards.add(new SetCardInfo("Lash of Thorns", 92, Rarity.COMMON, mage.cards.l.LashOfThorns.class));
        cards.add(new SetCardInfo("Linden, the Steadfast Queen", 20, Rarity.RARE, mage.cards.l.LindenTheSteadfastQueen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Linden, the Steadfast Queen", 340, Rarity.RARE, mage.cards.l.LindenTheSteadfastQueen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loch Dragon", 211, Rarity.UNCOMMON, mage.cards.l.LochDragon.class));
        cards.add(new SetCardInfo("Lochmere Serpent", 195, Rarity.RARE, mage.cards.l.LochmereSerpent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lochmere Serpent", 381, Rarity.RARE, mage.cards.l.LochmereSerpent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Locthwain Gargoyle", 225, Rarity.COMMON, mage.cards.l.LocthwainGargoyle.class));
        cards.add(new SetCardInfo("Locthwain Paladin", 93, Rarity.COMMON, mage.cards.l.LocthwainPaladin.class));
        cards.add(new SetCardInfo("Lonesome Unicorn", 21, Rarity.COMMON, mage.cards.l.LonesomeUnicorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lonesome Unicorn", 276, Rarity.COMMON, mage.cards.l.LonesomeUnicorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lost Legion", 94, Rarity.COMMON, mage.cards.l.LostLegion.class));
        cards.add(new SetCardInfo("Lovestruck Beast", 165, Rarity.RARE, mage.cards.l.LovestruckBeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lovestruck Beast", 299, Rarity.RARE, mage.cards.l.LovestruckBeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lucky Clover", 226, Rarity.UNCOMMON, mage.cards.l.LuckyClover.class));
        cards.add(new SetCardInfo("Mace of the Valiant", 314, Rarity.RARE, mage.cards.m.MaceOfTheValiant.class));
        cards.add(new SetCardInfo("Mad Ratter", 130, Rarity.UNCOMMON, mage.cards.m.MadRatter.class));
        cards.add(new SetCardInfo("Malevolent Noble", 95, Rarity.COMMON, mage.cards.m.MalevolentNoble.class));
        cards.add(new SetCardInfo("Mantle of Tides", 52, Rarity.COMMON, mage.cards.m.MantleOfTides.class));
        cards.add(new SetCardInfo("Maraleaf Pixie", 196, Rarity.UNCOMMON, mage.cards.m.MaraleafPixie.class));
        cards.add(new SetCardInfo("Maraleaf Rider", 166, Rarity.COMMON, mage.cards.m.MaraleafRider.class));
        cards.add(new SetCardInfo("Memory Theft", 96, Rarity.COMMON, mage.cards.m.MemoryTheft.class));
        cards.add(new SetCardInfo("Merchant of the Vale", 131, Rarity.COMMON, mage.cards.m.MerchantOfTheVale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Merchant of the Vale", 293, Rarity.COMMON, mage.cards.m.MerchantOfTheVale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Merfolk Secretkeeper", 284, Rarity.COMMON, mage.cards.m.MerfolkSecretkeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Merfolk Secretkeeper", 53, Rarity.COMMON, mage.cards.m.MerfolkSecretkeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Midnight Clock", 346, Rarity.RARE, mage.cards.m.MidnightClock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Midnight Clock", 54, Rarity.RARE, mage.cards.m.MidnightClock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirrormade", 347, Rarity.RARE, mage.cards.m.Mirrormade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirrormade", 55, Rarity.RARE, mage.cards.m.Mirrormade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mistford River Turtle", 56, Rarity.COMMON, mage.cards.m.MistfordRiverTurtle.class));
        cards.add(new SetCardInfo("Moonlit Scavengers", 57, Rarity.COMMON, mage.cards.m.MoonlitScavengers.class));
        cards.add(new SetCardInfo("Mountain", 262, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murderous Rider", 287, Rarity.RARE, mage.cards.m.MurderousRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murderous Rider", 97, Rarity.RARE, mage.cards.m.MurderousRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mysterious Pathlighter", 22, Rarity.UNCOMMON, mage.cards.m.MysteriousPathlighter.class));
        cards.add(new SetCardInfo("Mystic Sanctuary", 247, Rarity.COMMON, mage.cards.m.MysticSanctuary.class));
        cards.add(new SetCardInfo("Mystical Dispute", 58, Rarity.UNCOMMON, mage.cards.m.MysticalDispute.class));
        cards.add(new SetCardInfo("Oakhame Adversary", 167, Rarity.UNCOMMON, mage.cards.o.OakhameAdversary.class));
        cards.add(new SetCardInfo("Oakhame Ranger", 212, Rarity.UNCOMMON, mage.cards.o.OakhameRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oakhame Ranger", 302, Rarity.UNCOMMON, mage.cards.o.OakhameRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oathsworn Knight", 354, Rarity.RARE, mage.cards.o.OathswornKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oathsworn Knight", 98, Rarity.RARE, mage.cards.o.OathswornKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ogre Errant", 132, Rarity.COMMON, mage.cards.o.OgreErrant.class));
        cards.add(new SetCardInfo("Oko's Accomplices", 310, Rarity.COMMON, mage.cards.o.OkosAccomplices.class));
        cards.add(new SetCardInfo("Oko's Hospitality", 312, Rarity.RARE, mage.cards.o.OkosHospitality.class));
        cards.add(new SetCardInfo("Oko, Thief of Crowns", 197, Rarity.MYTHIC, mage.cards.o.OkoThiefOfCrowns.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oko, Thief of Crowns", 271, Rarity.MYTHIC, mage.cards.o.OkoThiefOfCrowns.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oko, the Trickster", 309, Rarity.MYTHIC, mage.cards.o.OkoTheTrickster.class));
        cards.add(new SetCardInfo("Once Upon a Time", 169, Rarity.RARE, mage.cards.o.OnceUponATime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Once Upon a Time", 371, Rarity.RARE, mage.cards.o.OnceUponATime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Once and Future", 168, Rarity.UNCOMMON, mage.cards.o.OnceAndFuture.class));
        cards.add(new SetCardInfo("Opportunistic Dragon", 133, Rarity.RARE, mage.cards.o.OpportunisticDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Opportunistic Dragon", 364, Rarity.RARE, mage.cards.o.OpportunisticDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Opt", 59, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Order of Midnight", 288, Rarity.UNCOMMON, mage.cards.o.OrderOfMidnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Order of Midnight", 99, Rarity.UNCOMMON, mage.cards.o.OrderOfMidnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outflank", 23, Rarity.COMMON, mage.cards.o.Outflank.class));
        cards.add(new SetCardInfo("Outlaws' Merriment", 198, Rarity.MYTHIC, mage.cards.o.OutlawsMerriment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outlaws' Merriment", 382, Rarity.MYTHIC, mage.cards.o.OutlawsMerriment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outmuscle", 170, Rarity.COMMON, mage.cards.o.Outmuscle.class));
        cards.add(new SetCardInfo("Overwhelmed Apprentice", 60, Rarity.UNCOMMON, mage.cards.o.OverwhelmedApprentice.class));
        cards.add(new SetCardInfo("Piper of the Swarm", 100, Rarity.RARE, mage.cards.p.PiperOfTheSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Piper of the Swarm", 355, Rarity.RARE, mage.cards.p.PiperOfTheSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Piper of the Swarm", 392, Rarity.RARE, mage.cards.p.PiperOfTheSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prized Griffin", 24, Rarity.COMMON, mage.cards.p.PrizedGriffin.class));
        cards.add(new SetCardInfo("Prophet of the Peak", 227, Rarity.COMMON, mage.cards.p.ProphetOfThePeak.class));
        cards.add(new SetCardInfo("Queen of Ice", 285, Rarity.COMMON, mage.cards.q.QueenOfIce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Queen of Ice", 61, Rarity.COMMON, mage.cards.q.QueenOfIce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Questing Beast", 171, Rarity.MYTHIC, mage.cards.q.QuestingBeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Questing Beast", 372, Rarity.MYTHIC, mage.cards.q.QuestingBeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raging Redcap", 134, Rarity.COMMON, mage.cards.r.RagingRedcap.class));
        cards.add(new SetCardInfo("Rally for the Throne", 25, Rarity.UNCOMMON, mage.cards.r.RallyForTheThrone.class));
        cards.add(new SetCardInfo("Rampart Smasher", 213, Rarity.UNCOMMON, mage.cards.r.RampartSmasher.class));
        cards.add(new SetCardInfo("Rankle, Master of Pranks", 101, Rarity.MYTHIC, mage.cards.r.RankleMasterOfPranks.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rankle, Master of Pranks", 356, Rarity.MYTHIC, mage.cards.r.RankleMasterOfPranks.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Realm-Cloaked Giant", 26, Rarity.MYTHIC, mage.cards.r.RealmCloakedGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Realm-Cloaked Giant", 277, Rarity.MYTHIC, mage.cards.r.RealmCloakedGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reaper of Night", 102, Rarity.COMMON, mage.cards.r.ReaperOfNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reaper of Night", 289, Rarity.COMMON, mage.cards.r.ReaperOfNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reave Soul", 103, Rarity.COMMON, mage.cards.r.ReaveSoul.class));
        cards.add(new SetCardInfo("Redcap Melee", 135, Rarity.UNCOMMON, mage.cards.r.RedcapMelee.class));
        cards.add(new SetCardInfo("Redcap Raiders", 136, Rarity.COMMON, mage.cards.r.RedcapRaiders.class));
        cards.add(new SetCardInfo("Resolute Rider", 214, Rarity.UNCOMMON, mage.cards.r.ResoluteRider.class));
        cards.add(new SetCardInfo("Return of the Wildspeaker", 172, Rarity.RARE, mage.cards.r.ReturnOfTheWildspeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Return of the Wildspeaker", 373, Rarity.RARE, mage.cards.r.ReturnOfTheWildspeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Return to Nature", 173, Rarity.COMMON, mage.cards.r.ReturnToNature.class));
        cards.add(new SetCardInfo("Revenge of Ravens", 104, Rarity.UNCOMMON, mage.cards.r.RevengeOfRavens.class));
        cards.add(new SetCardInfo("Righteousness", 27, Rarity.UNCOMMON, mage.cards.r.Righteousness.class));
        cards.add(new SetCardInfo("Rimrock Knight", 137, Rarity.COMMON, mage.cards.r.RimrockKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rimrock Knight", 294, Rarity.COMMON, mage.cards.r.RimrockKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Robber of the Rich", 138, Rarity.MYTHIC, mage.cards.r.RobberOfTheRich.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Robber of the Rich", 365, Rarity.MYTHIC, mage.cards.r.RobberOfTheRich.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rosethorn Acolyte", 174, Rarity.COMMON, mage.cards.r.RosethornAcolyte.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rosethorn Acolyte", 300, Rarity.COMMON, mage.cards.r.RosethornAcolyte.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rosethorn Halberd", 175, Rarity.COMMON, mage.cards.r.RosethornHalberd.class));
        cards.add(new SetCardInfo("Roving Keep", 228, Rarity.COMMON, mage.cards.r.RovingKeep.class));
        cards.add(new SetCardInfo("Rowan's Battleguard", 306, Rarity.UNCOMMON, mage.cards.r.RowansBattleguard.class));
        cards.add(new SetCardInfo("Rowan's Stalwarts", 307, Rarity.RARE, mage.cards.r.RowansStalwarts.class));
        cards.add(new SetCardInfo("Rowan, Fearless Sparkmage", 304, Rarity.MYTHIC, mage.cards.r.RowanFearlessSparkmage.class));
        cards.add(new SetCardInfo("Run Away Together", 62, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Sage of the Falls", 63, Rarity.UNCOMMON, mage.cards.s.SageOfTheFalls.class));
        cards.add(new SetCardInfo("Savvy Hunter", 200, Rarity.UNCOMMON, mage.cards.s.SavvyHunter.class));
        cards.add(new SetCardInfo("Scalding Cauldron", 229, Rarity.COMMON, mage.cards.s.ScaldingCauldron.class));
        cards.add(new SetCardInfo("Scorching Dragonfire", 139, Rarity.COMMON, mage.cards.s.ScorchingDragonfire.class));
        cards.add(new SetCardInfo("Searing Barrage", 140, Rarity.COMMON, mage.cards.s.SearingBarrage.class));
        cards.add(new SetCardInfo("Seven Dwarves", 141, Rarity.COMMON, mage.cards.s.SevenDwarves.class));
        cards.add(new SetCardInfo("Shambling Suit", 230, Rarity.UNCOMMON, mage.cards.s.ShamblingSuit.class));
        cards.add(new SetCardInfo("Shepherd of the Flock", 278, Rarity.UNCOMMON, mage.cards.s.ShepherdOfTheFlock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shepherd of the Flock", 28, Rarity.UNCOMMON, mage.cards.s.ShepherdOfTheFlock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shimmer Dragon", 317, Rarity.RARE, mage.cards.s.ShimmerDragon.class));
        cards.add(new SetCardInfo("Shinechaser", 201, Rarity.UNCOMMON, mage.cards.s.Shinechaser.class));
        cards.add(new SetCardInfo("Shining Armor", 29, Rarity.COMMON, mage.cards.s.ShiningArmor.class));
        cards.add(new SetCardInfo("Signpost Scarecrow", 231, Rarity.COMMON, mage.cards.s.SignpostScarecrow.class));
        cards.add(new SetCardInfo("Silverflame Ritual", 30, Rarity.COMMON, mage.cards.s.SilverflameRitual.class));
        cards.add(new SetCardInfo("Silverflame Squire", 279, Rarity.COMMON, mage.cards.s.SilverflameSquire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverflame Squire", 31, Rarity.COMMON, mage.cards.s.SilverflameSquire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverwing Squadron", 315, Rarity.RARE, mage.cards.s.SilverwingSquadron.class));
        cards.add(new SetCardInfo("Skullknocker Ogre", 142, Rarity.UNCOMMON, mage.cards.s.SkullknockerOgre.class));
        cards.add(new SetCardInfo("Slaying Fire", 143, Rarity.UNCOMMON, mage.cards.s.SlayingFire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slaying Fire", 394, Rarity.UNCOMMON, mage.cards.s.SlayingFire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smitten Swordmaster", 105, Rarity.COMMON, mage.cards.s.SmittenSwordmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smitten Swordmaster", 290, Rarity.COMMON, mage.cards.s.SmittenSwordmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("So Tiny", 64, Rarity.COMMON, mage.cards.s.SoTiny.class));
        cards.add(new SetCardInfo("Sorcerer's Broom", 232, Rarity.UNCOMMON, mage.cards.s.SorcerersBroom.class));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 233, Rarity.RARE, mage.cards.s.SorcerousSpyglass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 384, Rarity.RARE, mage.cards.s.SorcerousSpyglass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Specter's Shriek", 106, Rarity.UNCOMMON, mage.cards.s.SpectersShriek.class));
        cards.add(new SetCardInfo("Spinning Wheel", 234, Rarity.UNCOMMON, mage.cards.s.SpinningWheel.class));
        cards.add(new SetCardInfo("Sporecap Spider", 176, Rarity.COMMON, mage.cards.s.SporecapSpider.class));
        cards.add(new SetCardInfo("Steelbane Hydra", 322, Rarity.RARE, mage.cards.s.SteelbaneHydra.class));
        cards.add(new SetCardInfo("Steelclaw Lance", 202, Rarity.UNCOMMON, mage.cards.s.SteelclawLance.class));
        cards.add(new SetCardInfo("Steelgaze Griffin", 65, Rarity.COMMON, mage.cards.s.SteelgazeGriffin.class));
        cards.add(new SetCardInfo("Stolen by the Fae", 348, Rarity.RARE, mage.cards.s.StolenByTheFae.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stolen by the Fae", 66, Rarity.RARE, mage.cards.s.StolenByTheFae.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stonecoil Serpent", 235, Rarity.RARE, mage.cards.s.StonecoilSerpent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stonecoil Serpent", 385, Rarity.RARE, mage.cards.s.StonecoilSerpent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stormfist Crusader", 203, Rarity.RARE, mage.cards.s.StormfistCrusader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stormfist Crusader", 383, Rarity.RARE, mage.cards.s.StormfistCrusader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sundering Stroke", 144, Rarity.RARE, mage.cards.s.SunderingStroke.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sundering Stroke", 366, Rarity.RARE, mage.cards.s.SunderingStroke.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 259, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 260, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syr Alin, the Lion's Claw", 32, Rarity.UNCOMMON, mage.cards.s.SyrAlinTheLionsClaw.class));
        cards.add(new SetCardInfo("Syr Carah, the Bold", 145, Rarity.UNCOMMON, mage.cards.s.SyrCarahTheBold.class));
        cards.add(new SetCardInfo("Syr Elenora, the Discerning", 67, Rarity.UNCOMMON, mage.cards.s.SyrElenoraTheDiscerning.class));
        cards.add(new SetCardInfo("Syr Faren, the Hengehammer", 177, Rarity.UNCOMMON, mage.cards.s.SyrFarenTheHengehammer.class));
        cards.add(new SetCardInfo("Syr Gwyn, Hero of Ashvale", 330, Rarity.MYTHIC, mage.cards.s.SyrGwynHeroOfAshvale.class));
        cards.add(new SetCardInfo("Syr Konrad, the Grim", 107, Rarity.UNCOMMON, mage.cards.s.SyrKonradTheGrim.class));
        cards.add(new SetCardInfo("Tall as a Beanstalk", 178, Rarity.COMMON, mage.cards.t.TallAsABeanstalk.class));
        cards.add(new SetCardInfo("Taste of Death", 320, Rarity.RARE, mage.cards.t.TasteOfDeath.class));
        cards.add(new SetCardInfo("Tempting Witch", 108, Rarity.COMMON, mage.cards.t.TemptingWitch.class));
        cards.add(new SetCardInfo("The Cauldron of Eternity", 352, Rarity.MYTHIC, mage.cards.t.TheCauldronOfEternity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Cauldron of Eternity", 82, Rarity.MYTHIC, mage.cards.t.TheCauldronOfEternity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Circle of Loyalty", 336, Rarity.MYTHIC, mage.cards.t.TheCircleOfLoyalty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Circle of Loyalty", 9, Rarity.MYTHIC, mage.cards.t.TheCircleOfLoyalty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Great Henge", 161, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Great Henge", 370, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Magic Mirror", 345, Rarity.MYTHIC, mage.cards.t.TheMagicMirror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Magic Mirror", 51, Rarity.MYTHIC, mage.cards.t.TheMagicMirror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Royal Scions", 199, Rarity.MYTHIC, mage.cards.t.TheRoyalScions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Royal Scions", 272, Rarity.MYTHIC, mage.cards.t.TheRoyalScions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorn Mammoth", 323, Rarity.RARE, mage.cards.t.ThornMammoth.class));
        cards.add(new SetCardInfo("Thornwood Falls", 313, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 146, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Thunderous Snapper", 215, Rarity.UNCOMMON, mage.cards.t.ThunderousSnapper.class));
        cards.add(new SetCardInfo("Tome Raider", 68, Rarity.COMMON, mage.cards.t.TomeRaider.class));
        cards.add(new SetCardInfo("Tome of Legends", 332, Rarity.RARE, mage.cards.t.TomeOfLegends.class));
        cards.add(new SetCardInfo("Torbran, Thane of Red Fell", 147, Rarity.RARE, mage.cards.t.TorbranThaneOfRedFell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torbran, Thane of Red Fell", 367, Rarity.RARE, mage.cards.t.TorbranThaneOfRedFell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tournament Grounds", 248, Rarity.UNCOMMON, mage.cards.t.TournamentGrounds.class));
        cards.add(new SetCardInfo("Trail of Crumbs", 179, Rarity.UNCOMMON, mage.cards.t.TrailOfCrumbs.class));
        cards.add(new SetCardInfo("Trapped in the Tower", 33, Rarity.COMMON, mage.cards.t.TrappedInTheTower.class));
        cards.add(new SetCardInfo("True Love's Kiss", 34, Rarity.COMMON, mage.cards.t.TrueLovesKiss.class));
        cards.add(new SetCardInfo("Tuinvale Treefolk", 180, Rarity.COMMON, mage.cards.t.TuinvaleTreefolk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tuinvale Treefolk", 301, Rarity.COMMON, mage.cards.t.TuinvaleTreefolk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turn into a Pumpkin", 69, Rarity.UNCOMMON, mage.cards.t.TurnIntoAPumpkin.class));
        cards.add(new SetCardInfo("Unexplained Vision", 70, Rarity.COMMON, mage.cards.u.UnexplainedVision.class));
        cards.add(new SetCardInfo("Vantress Gargoyle", 349, Rarity.RARE, mage.cards.v.VantressGargoyle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vantress Gargoyle", 71, Rarity.RARE, mage.cards.v.VantressGargoyle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vantress Paladin", 72, Rarity.COMMON, mage.cards.v.VantressPaladin.class));
        cards.add(new SetCardInfo("Venerable Knight", 35, Rarity.UNCOMMON, mage.cards.v.VenerableKnight.class));
        cards.add(new SetCardInfo("Wandermare", 204, Rarity.UNCOMMON, mage.cards.w.Wandermare.class));
        cards.add(new SetCardInfo("Weapon Rack", 236, Rarity.COMMON, mage.cards.w.WeaponRack.class));
        cards.add(new SetCardInfo("Weaselback Redcap", 148, Rarity.COMMON, mage.cards.w.WeaselbackRedcap.class));
        cards.add(new SetCardInfo("Wicked Guardian", 109, Rarity.COMMON, mage.cards.w.WickedGuardian.class));
        cards.add(new SetCardInfo("Wicked Wolf", 181, Rarity.RARE, mage.cards.w.WickedWolf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wicked Wolf", 374, Rarity.RARE, mage.cards.w.WickedWolf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wildborn Preserver", 182, Rarity.RARE, mage.cards.w.WildbornPreserver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wildborn Preserver", 375, Rarity.RARE, mage.cards.w.WildbornPreserver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wildwood Tracker", 183, Rarity.COMMON, mage.cards.w.WildwoodTracker.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 308, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Wintermoor Commander", 205, Rarity.UNCOMMON, mage.cards.w.WintermoorCommander.class));
        cards.add(new SetCardInfo("Wishclaw Talisman", 110, Rarity.RARE, mage.cards.w.WishclawTalisman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wishclaw Talisman", 357, Rarity.RARE, mage.cards.w.WishclawTalisman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wishful Merfolk", 73, Rarity.COMMON, mage.cards.w.WishfulMerfolk.class));
        cards.add(new SetCardInfo("Witch's Cottage", 249, Rarity.COMMON, mage.cards.w.WitchsCottage.class));
        cards.add(new SetCardInfo("Witch's Oven", 237, Rarity.UNCOMMON, mage.cards.w.WitchsOven.class));
        cards.add(new SetCardInfo("Witch's Vengeance", 111, Rarity.RARE, mage.cards.w.WitchsVengeance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witch's Vengeance", 358, Rarity.RARE, mage.cards.w.WitchsVengeance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witching Well", 74, Rarity.COMMON, mage.cards.w.WitchingWell.class));
        cards.add(new SetCardInfo("Wolf's Quarry", 184, Rarity.COMMON, mage.cards.w.WolfsQuarry.class));
        cards.add(new SetCardInfo("Workshop Elders", 318, Rarity.RARE, mage.cards.w.WorkshopElders.class));
        cards.add(new SetCardInfo("Worthy Knight", 341, Rarity.RARE, mage.cards.w.WorthyKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Worthy Knight", 36, Rarity.RARE, mage.cards.w.WorthyKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yorvo, Lord of Garenbrig", 185, Rarity.RARE, mage.cards.y.YorvoLordOfGarenbrig.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yorvo, Lord of Garenbrig", 376, Rarity.RARE, mage.cards.y.YorvoLordOfGarenbrig.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Youthful Knight", 37, Rarity.COMMON, mage.cards.y.YouthfulKnight.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ThroneOfEldraineCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/eld.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class ThroneOfEldraineCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "141", "23", "59", "113", "29", "62", "131", "37", "61", "132", "13", "68", "119", "6", "57", "112", "4", "56", "146", "31", "64", "137", "23", "42", "148", "24", "52", "134", "21", "74", "141", "11", "62", "121", "29", "68", "132", "34", "73", "113", "13", "57", "131", "37", "59", "146", "6", "64", "112", "4", "56", "148", "11", "61", "119", "31", "52", "134", "21", "42", "137", "34", "74", "121", "24", "73");
    private final CardRun commonB = new CardRun(true, "96", "175", "108", "162", "89", "174", "92", "176", "88", "150", "102", "180", "105", "156", "91", "184", "87", "173", "94", "153", "108", "176", "95", "162", "96", "174", "92", "175", "88", "158", "105", "180", "89", "156", "87", "150", "94", "184", "102", "173", "96", "153", "92", "162", "91", "158", "95", "175", "88", "176", "89", "174", "108", "184", "105", "156", "87", "180", "102", "173", "91", "150", "94", "158", "95", "153");
    private final CardRun commonC1 = new CardRun(true, "228", "41", "12", "217", "219", "93", "249", "166", "30", "140", "86", "40", "225", "33", "126", "229", "77", "41", "157", "243", "236", "116", "220", "178", "72", "246", "225", "103", "126", "12", "249", "166", "221", "140", "7", "40", "228", "243", "93", "219", "33", "220", "246", "157", "86", "229", "116", "217", "178", "77", "236", "7", "72", "221", "103");
    private final CardRun commonC2 = new CardRun(true, "5", "224", "170", "114", "76", "231", "70", "19", "136", "247", "53", "154", "227", "109", "245", "65", "183", "139", "224", "5", "19", "70", "231", "76", "114", "5", "247", "170", "227", "136", "245", "109", "114", "154", "224", "139", "183", "76", "30", "65", "231", "136", "53", "170", "247", "70", "19", "109", "227", "139", "53", "154", "245", "65", "183");
    private final CardRun uncommonA = new CardRun(true, "209", "216", "202", "104", "214", "38", "223", "25", "201", "122", "159", "204", "15", "297", "118", "212", "230", "58", "218", "207", "85", "28", "205", "232", "283", "106", "188", "168", "145", "210", "163", "142", "196", "226", "25", "38", "209", "90", "60", "234", "204", "155", "223", "202", "85", "214", "248", "188", "216", "104", "292", "208", "168", "118", "201", "232", "15", "159", "212", "90", "58", "230", "207", "163", "106", "196", "278", "49", "10", "205", "218", "60", "145", "210", "226", "142", "204", "216", "25", "223", "209", "280", "104", "234", "214", "155", "248", "208", "15", "201", "159", "202", "230", "118", "85", "302", "168", "106", "207", "232", "122", "58", "205", "49", "10", "218", "210", "226", "248", "188", "28", "163", "60", "196", "234", "142", "286", "208", "145", "10");
    private final CardRun uncommonB = new CardRun(true, "45", "213", "123", "149", "83", "237", "2", "78", "129", "194", "63", "32", "151", "193", "81", "67", "130", "3", "211", "69", "22", "107", "143", "50", "206", "179", "27", "222", "99", "47", "200", "177", "192", "80", "35", "117", "149", "237", "215", "45", "83", "32", "130", "193", "167", "50", "107", "2", "123", "194", "69", "3", "135", "164", "211", "47", "81", "213", "22", "222", "143", "63", "206", "179", "27", "78", "200", "45", "129", "151", "288", "35", "192", "177", "117", "67", "3", "167", "215", "80", "50", "237", "135", "164", "83", "193", "2", "211", "130", "194", "179", "107", "63", "123", "213", "177", "32", "69", "22", "206", "78", "129", "295", "222", "67", "81", "151", "215", "143", "27", "135", "200", "99", "167", "117", "47", "192", "35", "80", "164");
    private final CardRun rare = new CardRun(false, "1", "8", "14", "16", "18", "20", "36", "43", "44", "46", "48", "54", "55", "66", "71", "75", "79", "84", "97", "98", "100", "110", "111", "115", "124", "125", "127", "128", "133", "144", "147", "152", "160", "165", "169", "172", "181", "182", "185", "186", "187", "189", "190", "195", "203", "233", "235", "238", "239", "240", "241", "242", "244", "1", "8", "14", "16", "18", "20", "36", "43", "44", "46", "48", "54", "55", "66", "71", "75", "79", "84", "97", "98", "100", "110", "111", "115", "124", "125", "127", "128", "133", "144", "147", "152", "160", "165", "169", "172", "181", "182", "185", "186", "187", "189", "190", "195", "203", "233", "235", "238", "239", "240", "241", "242", "244", "9", "17", "26", "39", "51", "82", "101", "120", "138", "161", "171", "191", "197", "198", "199");
    private final CardRun rareVariant = new CardRun(false, "275", "282", "287", "291", "299", "275", "282", "287", "291", "299", "270", "271", "272", "277", "281");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure R2 = new BoosterStructure(rareVariant);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(
            R1, R1, R1, R1, R1, R1, R1, R1, R1,
            R1, R1, R1, R1, R1, R1, R1, R1, R1,
            R1, R1, R1, R1, R1, R1, R1, R1, R2
    );
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
