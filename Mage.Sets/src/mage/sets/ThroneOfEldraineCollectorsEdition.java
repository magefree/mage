package mage.sets;

import mage.cards.AdventureCard;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ThroneOfEldraineCollectorsEdition extends ExpansionSet {

    private static final ThroneOfEldraineCollectorsEdition instance = new ThroneOfEldraineCollectorsEdition();

    public static ThroneOfEldraineCollectorsEdition getInstance() {
        return instance;
    }

    private ThroneOfEldraineCollectorsEdition() {
        super("Throne of Eldraine Collector's Edition", "CELD", ExpansionSet.buildDate(2019, 10, 4), SetType.PROMOTIONAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Acclaimed Contender", 334, Rarity.RARE, mage.cards.a.AcclaimedContender.class));
        cards.add(new SetCardInfo("Animating Faerie", 280, Rarity.UNCOMMON, mage.cards.a.AnimatingFaerie.class));
        cards.add(new SetCardInfo("Ardenvale Tactician", 273, Rarity.COMMON, mage.cards.a.ArdenvaleTactician.class));
        cards.add(new SetCardInfo("Ayara, First of Locthwain", 350, Rarity.RARE, mage.cards.a.AyaraFirstOfLocthwain.class));
        cards.add(new SetCardInfo("Beanstalk Giant", 295, Rarity.UNCOMMON, mage.cards.b.BeanstalkGiant.class));
        cards.add(new SetCardInfo("Blacklance Paragon", 351, Rarity.RARE, mage.cards.b.BlacklanceParagon.class));
        cards.add(new SetCardInfo("Bonecrusher Giant", 291, Rarity.RARE, mage.cards.b.BonecrusherGiant.class));
        cards.add(new SetCardInfo("Brazen Borrower", 281, Rarity.MYTHIC, mage.cards.b.BrazenBorrower.class));
        cards.add(new SetCardInfo("Castle Ardenvale", 386, Rarity.RARE, mage.cards.c.CastleArdenvale.class));
        cards.add(new SetCardInfo("Castle Embereth", 387, Rarity.RARE, mage.cards.c.CastleEmbereth.class));
        cards.add(new SetCardInfo("Castle Garenbrig", 388, Rarity.RARE, mage.cards.c.CastleGarenbrig.class));
        cards.add(new SetCardInfo("Castle Locthwain", 389, Rarity.RARE, mage.cards.c.CastleLocthwain.class));
        cards.add(new SetCardInfo("Castle Vantress", 390, Rarity.RARE, mage.cards.c.CastleVantress.class));
        cards.add(new SetCardInfo("Charming Prince", 335, Rarity.RARE, mage.cards.c.CharmingPrince.class));
        cards.add(new SetCardInfo("Clackbridge Troll", 353, Rarity.RARE, mage.cards.c.ClackbridgeTroll.class));
        cards.add(new SetCardInfo("Curious Pair", 296, Rarity.COMMON, mage.cards.c.CuriousPair.class));
        cards.add(new SetCardInfo("Dance of the Manse", 377, Rarity.RARE, mage.cards.d.DanceOfTheManse.class));
        cards.add(new SetCardInfo("Doom Foretold", 378, Rarity.RARE, mage.cards.d.DoomForetold.class));
        cards.add(new SetCardInfo("Embercleave", 359, Rarity.MYTHIC, mage.cards.e.Embercleave.class));
        cards.add(new SetCardInfo("Embereth Shieldbreaker", 292, Rarity.UNCOMMON, mage.cards.e.EmberethShieldbreaker.class));
        cards.add(new SetCardInfo("Emry, Lurker of the Loch", 342, Rarity.RARE, mage.cards.e.EmryLurkerOfTheLoch.class));
        cards.add(new SetCardInfo("Escape to the Wilds", 379, Rarity.RARE, mage.cards.e.EscapeToTheWilds.class));
        cards.add(new SetCardInfo("Fabled Passage", 391, Rarity.RARE, mage.cards.f.FabledPassage.class));
        cards.add(new SetCardInfo("Fae of Wishes", 282, Rarity.RARE, mage.cards.f.FaeOfWishes.class));
        cards.add(new SetCardInfo("Faeburrow Elder", 380, Rarity.RARE, mage.cards.f.FaeburrowElder.class));
        cards.add(new SetCardInfo("Faerie Guidemother", 274, Rarity.COMMON, mage.cards.f.FaerieGuidemother.class));
        cards.add(new SetCardInfo("Feasting Troll King", 368, Rarity.RARE, mage.cards.f.FeastingTrollKing.class));
        cards.add(new SetCardInfo("Fervent Champion", 360, Rarity.RARE, mage.cards.f.FerventChampion.class));
        cards.add(new SetCardInfo("Fires of Invention", 361, Rarity.RARE, mage.cards.f.FiresOfInvention.class));
        cards.add(new SetCardInfo("Flaxen Intruder", 297, Rarity.UNCOMMON, mage.cards.f.FlaxenIntruder.class));
        cards.add(new SetCardInfo("Folio of Fancies", 343, Rarity.RARE, mage.cards.f.FolioOfFancies.class));
        cards.add(new SetCardInfo("Foulmire Knight", 286, Rarity.UNCOMMON, mage.cards.f.FoulmireKnight.class));
        cards.add(new SetCardInfo("Gadwick, the Wizened", 344, Rarity.RARE, mage.cards.g.GadwickTheWizened.class));
        cards.add(new SetCardInfo("Garenbrig Carver", 298, Rarity.COMMON, mage.cards.g.GarenbrigCarver.class));
        cards.add(new SetCardInfo("Garruk, Cursed Huntsman", 270, Rarity.MYTHIC, mage.cards.g.GarrukCursedHuntsman.class));
        cards.add(new SetCardInfo("Giant Killer", 275, Rarity.RARE, mage.cards.g.GiantKiller.class));
        cards.add(new SetCardInfo("Gilded Goose", 369, Rarity.RARE, mage.cards.g.GildedGoose.class));
        cards.add(new SetCardInfo("Happily Ever After", 337, Rarity.RARE, mage.cards.h.HappilyEverAfter.class));
        cards.add(new SetCardInfo("Harmonious Archon", 338, Rarity.MYTHIC, mage.cards.h.HarmoniousArchon.class));
        cards.add(new SetCardInfo("Hushbringer", 339, Rarity.RARE, mage.cards.h.Hushbringer.class));
        cards.add(new SetCardInfo("Hypnotic Sprite", 283, Rarity.UNCOMMON, mage.cards.h.HypnoticSprite.class));
        cards.add(new SetCardInfo("Irencrag Feat", 362, Rarity.RARE, mage.cards.i.IrencragFeat.class));
        cards.add(new SetCardInfo("Irencrag Pyromancer", 363, Rarity.RARE, mage.cards.i.IrencragPyromancer.class));
        cards.add(new SetCardInfo("Linden, the Steadfast Queen", 340, Rarity.RARE, mage.cards.l.LindenTheSteadfastQueen.class));
        cards.add(new SetCardInfo("Lochmere Serpent", 381, Rarity.RARE, mage.cards.l.LochmereSerpent.class));
        cards.add(new SetCardInfo("Lonesome Unicorn", 276, Rarity.COMMON, mage.cards.l.LonesomeUnicorn.class));
        cards.add(new SetCardInfo("Lovestruck Beast", 299, Rarity.RARE, mage.cards.l.LovestruckBeast.class));
        cards.add(new SetCardInfo("Merchant of the Vale", 293, Rarity.COMMON, mage.cards.m.MerchantOfTheVale.class));
        cards.add(new SetCardInfo("Merfolk Secretkeeper", 284, Rarity.COMMON, mage.cards.m.MerfolkSecretkeeper.class));
        cards.add(new SetCardInfo("Midnight Clock", 346, Rarity.RARE, mage.cards.m.MidnightClock.class));
        cards.add(new SetCardInfo("Mirrormade", 347, Rarity.RARE, mage.cards.m.Mirrormade.class));
        cards.add(new SetCardInfo("Murderous Rider", 287, Rarity.RARE, mage.cards.m.MurderousRider.class));
        cards.add(new SetCardInfo("Oakhame Ranger", 302, Rarity.UNCOMMON, mage.cards.o.OakhameRanger.class));
        cards.add(new SetCardInfo("Oathsworn Knight", 354, Rarity.RARE, mage.cards.o.OathswornKnight.class));
        cards.add(new SetCardInfo("Oko, Thief of Crowns", 271, Rarity.MYTHIC, mage.cards.o.OkoThiefOfCrowns.class));
        cards.add(new SetCardInfo("Once Upon a Time", 371, Rarity.RARE, mage.cards.o.OnceUponATime.class));
        cards.add(new SetCardInfo("Opportunistic Dragon", 364, Rarity.RARE, mage.cards.o.OpportunisticDragon.class));
        cards.add(new SetCardInfo("Order of Midnight", 288, Rarity.UNCOMMON, mage.cards.o.OrderOfMidnight.class));
        cards.add(new SetCardInfo("Outlaws' Merriment", 382, Rarity.MYTHIC, mage.cards.o.OutlawsMerriment.class));
        cards.add(new SetCardInfo("Piper of the Swarm", 355, Rarity.RARE, mage.cards.p.PiperOfTheSwarm.class));
        cards.add(new SetCardInfo("Queen of Ice", 285, Rarity.COMMON, mage.cards.q.QueenOfIce.class));
        cards.add(new SetCardInfo("Questing Beast", 372, Rarity.MYTHIC, mage.cards.q.QuestingBeast.class));
        cards.add(new SetCardInfo("Rankle, Master of Pranks", 356, Rarity.MYTHIC, mage.cards.r.RankleMasterOfPranks.class));
        cards.add(new SetCardInfo("Realm-Cloaked Giant", 277, Rarity.MYTHIC, mage.cards.r.RealmCloakedGiant.class));
        cards.add(new SetCardInfo("Reaper of Night", 289, Rarity.COMMON, mage.cards.r.ReaperOfNight.class));
        cards.add(new SetCardInfo("Return of the Wildspeaker", 373, Rarity.RARE, mage.cards.r.ReturnOfTheWildspeaker.class));
        cards.add(new SetCardInfo("Rimrock Knight", 294, Rarity.COMMON, mage.cards.r.RimrockKnight.class));
        cards.add(new SetCardInfo("Robber of the Rich", 365, Rarity.MYTHIC, mage.cards.r.RobberOfTheRich.class));
        cards.add(new SetCardInfo("Rosethorn Acolyte", 300, Rarity.COMMON, mage.cards.r.RosethornAcolyte.class));
        cards.add(new SetCardInfo("Shepherd of the Flock", 278, Rarity.UNCOMMON, mage.cards.s.ShepherdOfTheFlock.class));
        cards.add(new SetCardInfo("Silverflame Squire", 279, Rarity.COMMON, mage.cards.s.SilverflameSquire.class));
        cards.add(new SetCardInfo("Smitten Swordmaster", 290, Rarity.COMMON, mage.cards.s.SmittenSwordmaster.class));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 384, Rarity.RARE, mage.cards.s.SorcerousSpyglass.class));
        cards.add(new SetCardInfo("Stolen by the Fae", 348, Rarity.RARE, mage.cards.s.StolenByTheFae.class));
        cards.add(new SetCardInfo("Stonecoil Serpent", 385, Rarity.RARE, mage.cards.s.StonecoilSerpent.class));
        cards.add(new SetCardInfo("Stormfist Crusader", 383, Rarity.RARE, mage.cards.s.StormfistCrusader.class));
        cards.add(new SetCardInfo("Sundering Stroke", 366, Rarity.RARE, mage.cards.s.SunderingStroke.class));
        cards.add(new SetCardInfo("The Cauldron of Eternity", 352, Rarity.MYTHIC, mage.cards.t.TheCauldronOfEternity.class));
        cards.add(new SetCardInfo("The Circle of Loyalty", 336, Rarity.MYTHIC, mage.cards.t.TheCircleOfLoyalty.class));
        cards.add(new SetCardInfo("The Great Henge", 370, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
        cards.add(new SetCardInfo("The Magic Mirror", 345, Rarity.MYTHIC, mage.cards.t.TheMagicMirror.class));
        cards.add(new SetCardInfo("The Royal Scions", 272, Rarity.MYTHIC, mage.cards.t.TheRoyalScions.class));
        cards.add(new SetCardInfo("Torbran, Thane of Red Fell", 367, Rarity.RARE, mage.cards.t.TorbranThaneOfRedFell.class));
        cards.add(new SetCardInfo("Tuinvale Treefolk", 301, Rarity.COMMON, mage.cards.t.TuinvaleTreefolk.class));
        cards.add(new SetCardInfo("Vantress Gargoyle", 349, Rarity.RARE, mage.cards.v.VantressGargoyle.class));
        cards.add(new SetCardInfo("Wicked Wolf", 374, Rarity.RARE, mage.cards.w.WickedWolf.class));
        cards.add(new SetCardInfo("Wildborn Preserver", 375, Rarity.RARE, mage.cards.w.WildbornPreserver.class));
        cards.add(new SetCardInfo("Wishclaw Talisman", 357, Rarity.RARE, mage.cards.w.WishclawTalisman.class));
        cards.add(new SetCardInfo("Witch's Vengeance", 358, Rarity.RARE, mage.cards.w.WitchsVengeance.class));
        cards.add(new SetCardInfo("Worthy Knight", 341, Rarity.RARE, mage.cards.w.WorthyKnight.class));
        cards.add(new SetCardInfo("Yorvo, Lord of Garenbrig", 376, Rarity.RARE, mage.cards.y.YorvoLordOfGarenbrig.class));

        // This is here to prevent the incomplete adventure implementation from causing problems and will be removed
        cards.removeIf(setCardInfo -> AdventureCard.class.isAssignableFrom(setCardInfo.getCardClass()));
    }
}
