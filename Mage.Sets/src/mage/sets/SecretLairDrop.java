package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/sld
 */
public class SecretLairDrop extends ExpansionSet {

    private static final SecretLairDrop instance = new SecretLairDrop();

    public static SecretLairDrop getInstance() {
        return instance;
    }

    private SecretLairDrop() {
        super("Secret Lair Drop", "SLD", ExpansionSet.buildDate(2020, 3, 12), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Arahbo, Roar of the World", 25, Rarity.MYTHIC, mage.cards.a.ArahboRoarOfTheWorld.class));
        cards.add(new SetCardInfo("Athreos, God of Passage", 76, Rarity.MYTHIC, mage.cards.a.AthreosGodOfPassage.class));
        cards.add(new SetCardInfo("Bitterblossom", 12, Rarity.MYTHIC, mage.cards.b.Bitterblossom.class));
        cards.add(new SetCardInfo("Bloodghast", 6, Rarity.RARE, mage.cards.b.Bloodghast.class));
        cards.add(new SetCardInfo("Captain Sisay", 51, Rarity.MYTHIC, mage.cards.c.CaptainSisay.class));
        cards.add(new SetCardInfo("Ephara, God of the Polis", 72, Rarity.MYTHIC, mage.cards.e.EpharaGodOfThePolis.class));
        cards.add(new SetCardInfo("Erebos, God of the Dead", 74, Rarity.MYTHIC, mage.cards.e.ErebosGodOfTheDead.class));
        cards.add(new SetCardInfo("Goblin Bushwhacker", 17, Rarity.RARE, mage.cards.g.GoblinBushwhacker.class));
        cards.add(new SetCardInfo("Goblin King", 19, Rarity.RARE, mage.cards.g.GoblinKing.class));
        cards.add(new SetCardInfo("Goblin Lackey", 20, Rarity.RARE, mage.cards.g.GoblinLackey.class));
        cards.add(new SetCardInfo("Goblin Piledriver", 21, Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Goblin Sharpshooter", 18, Rarity.RARE, mage.cards.g.GoblinSharpshooter.class));
        cards.add(new SetCardInfo("Golgari Thug", 7, Rarity.RARE, mage.cards.g.GolgariThug.class));
        cards.add(new SetCardInfo("Heliod, God of the Sun", 68, Rarity.MYTHIC, mage.cards.h.HeliodGodOfTheSun.class));
        cards.add(new SetCardInfo("Ink-Eyes, Servant of Oni", 33, Rarity.RARE, mage.cards.i.InkEyesServantOfOni.class));
        cards.add(new SetCardInfo("Iroas, God of Victory", 70, Rarity.MYTHIC, mage.cards.i.IroasGodOfVictory.class));
        cards.add(new SetCardInfo("Karametra, God of Harvests", 69, Rarity.MYTHIC, mage.cards.k.KarametraGodOfHarvests.class));
        cards.add(new SetCardInfo("Keranos, God of Storms", 79, Rarity.MYTHIC, mage.cards.k.KeranosGodOfStorms.class));
        cards.add(new SetCardInfo("Kruphix, God of Horizons", 73, Rarity.MYTHIC, mage.cards.k.KruphixGodOfHorizons.class));
        cards.add(new SetCardInfo("Leonin Warleader", 22, Rarity.RARE, mage.cards.l.LeoninWarleader.class));
        cards.add(new SetCardInfo("Life from the Loam", 8, Rarity.RARE, mage.cards.l.LifeFromTheLoam.class));
        cards.add(new SetCardInfo("Marrow-Gnawer", 34, Rarity.RARE, mage.cards.m.MarrowGnawer.class));
        cards.add(new SetCardInfo("Meren of Clan Nel Toth", 52, Rarity.MYTHIC, mage.cards.m.MerenOfClanNelToth.class));
        cards.add(new SetCardInfo("Mirri, Weatherlight Duelist", 26, Rarity.MYTHIC, mage.cards.m.MirriWeatherlightDuelist.class));
        cards.add(new SetCardInfo("Mogis, God of Slaughter", 78, Rarity.MYTHIC, mage.cards.m.MogisGodOfSlaughter.class));
        cards.add(new SetCardInfo("Narset, Enlightened Master", 53, Rarity.MYTHIC, mage.cards.n.NarsetEnlightenedMaster.class));
        cards.add(new SetCardInfo("Nylea, God of the Hunt", 80, Rarity.MYTHIC, mage.cards.n.NyleaGodOfTheHunt.class));
        cards.add(new SetCardInfo("Oona, Queen of the Fae", 54, Rarity.MYTHIC, mage.cards.o.OonaQueenOfTheFae.class));
        cards.add(new SetCardInfo("Pack Rat", 35, Rarity.RARE, mage.cards.p.PackRat.class));
        cards.add(new SetCardInfo("Pharika, God of Affliction", 82, Rarity.MYTHIC, mage.cards.p.PharikaGodOfAffliction.class));
        cards.add(new SetCardInfo("Phenax, God of Deception", 75, Rarity.MYTHIC, mage.cards.p.PhenaxGodOfDeception.class));
        cards.add(new SetCardInfo("Purphoros, God of the Forge", 77, Rarity.MYTHIC, mage.cards.p.PurphorosGodOfTheForge.class));
        cards.add(new SetCardInfo("Qasali Slingers", 24, Rarity.RARE, mage.cards.q.QasaliSlingers.class));
        cards.add(new SetCardInfo("Rat Colony", 36, Rarity.RARE, mage.cards.r.RatColony.class));
        cards.add(new SetCardInfo("Reaper King", 9, Rarity.MYTHIC, mage.cards.r.ReaperKing.class));
        cards.add(new SetCardInfo("Regal Caracal", 23, Rarity.RARE, mage.cards.r.RegalCaracal.class));
        cards.add(new SetCardInfo("Saskia the Unyielding", 55, Rarity.MYTHIC, mage.cards.s.SaskiaTheUnyielding.class));
        cards.add(new SetCardInfo("Serum Visions", 29, Rarity.RARE, mage.cards.s.SerumVisions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serum Visions", 30, Rarity.RARE, mage.cards.s.SerumVisions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serum Visions", 31, Rarity.RARE, mage.cards.s.SerumVisions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serum Visions", 32, Rarity.RARE, mage.cards.s.SerumVisions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sliver Overlord", 10, Rarity.MYTHIC, mage.cards.s.SliverOverlord.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 5, Rarity.LAND, mage.cards.s.SnowCoveredForest.class));
        cards.add(new SetCardInfo("Snow-Covered Island", 2, Rarity.LAND, mage.cards.s.SnowCoveredIsland.class));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 4, Rarity.LAND, mage.cards.s.SnowCoveredMountain.class));
        cards.add(new SetCardInfo("Snow-Covered Plains", 1, Rarity.LAND, mage.cards.s.SnowCoveredPlains.class));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 3, Rarity.LAND, mage.cards.s.SnowCoveredSwamp.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 37, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 38, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 39, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 40, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thassa, God of the Sea", 71, Rarity.MYTHIC, mage.cards.t.ThassaGodOfTheSea.class));
        cards.add(new SetCardInfo("The Ur-Dragon", 11, Rarity.MYTHIC, mage.cards.t.TheUrDragon.class));
        cards.add(new SetCardInfo("Xenagos, God of Revels", 81, Rarity.MYTHIC, mage.cards.x.XenagosGodOfRevels.class));
     }
}
