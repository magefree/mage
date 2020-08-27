package mage.sets;

import mage.cards.CardGraphicInfo;
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

        final CardGraphicInfo FULL_ART = FULL_ART_BFZ_VARIOUS;

        cards.add(new SetCardInfo("Acidic Slime", 134, Rarity.RARE, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Ajani Steadfast", 87, Rarity.MYTHIC, mage.cards.a.AjaniSteadfast.class));
        cards.add(new SetCardInfo("Ancient Grudge", 98, Rarity.RARE, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Arahbo, Roar of the World", 25, Rarity.MYTHIC, mage.cards.a.ArahboRoarOfTheWorld.class));
        cards.add(new SetCardInfo("Arcbound Ravager", 56, Rarity.RARE, mage.cards.a.ArcboundRavager.class));
        cards.add(new SetCardInfo("Athreos, God of Passage", 76, Rarity.MYTHIC, mage.cards.a.AthreosGodOfPassage.class, FULL_ART));
        cards.add(new SetCardInfo("Baleful Strix", 94, Rarity.RARE, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Birds of Paradise", 92, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Bitterblossom", 12, Rarity.MYTHIC, mage.cards.b.Bitterblossom.class));
        cards.add(new SetCardInfo("Blood Artist", 42, Rarity.RARE, mage.cards.b.BloodArtist.class));
        cards.add(new SetCardInfo("Bloodghast", 6, Rarity.RARE, mage.cards.b.Bloodghast.class));
        cards.add(new SetCardInfo("Captain Sisay", 51, Rarity.MYTHIC, mage.cards.c.CaptainSisay.class));
        cards.add(new SetCardInfo("Darksteel Colossus", 57, Rarity.MYTHIC, mage.cards.d.DarksteelColossus.class));
        cards.add(new SetCardInfo("Dig Through Time", 97, Rarity.RARE, mage.cards.d.DigThroughTime.class));
        cards.add(new SetCardInfo("Domri Rade", 88, Rarity.MYTHIC, mage.cards.d.DomriRade.class));
        cards.add(new SetCardInfo("Dovescape", 95, Rarity.RARE, mage.cards.d.Dovescape.class));
        cards.add(new SetCardInfo("Ephara, God of the Polis", 72, Rarity.MYTHIC, mage.cards.e.EpharaGodOfThePolis.class, FULL_ART));
        cards.add(new SetCardInfo("Erebos, God of the Dead", 74, Rarity.MYTHIC, mage.cards.e.ErebosGodOfTheDead.class, FULL_ART));
        cards.add(new SetCardInfo("Eternal Witness", 43, Rarity.RARE, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Forest", 67, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Gilded Goose", 93, Rarity.RARE, mage.cards.g.GildedGoose.class));
        cards.add(new SetCardInfo("Goblin Bushwhacker", 17, Rarity.RARE, mage.cards.g.GoblinBushwhacker.class));
        cards.add(new SetCardInfo("Goblin King", 19, Rarity.RARE, mage.cards.g.GoblinKing.class));
        cards.add(new SetCardInfo("Goblin Lackey", 20, Rarity.RARE, mage.cards.g.GoblinLackey.class));
        cards.add(new SetCardInfo("Goblin Piledriver", 21, Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Goblin Sharpshooter", 18, Rarity.RARE, mage.cards.g.GoblinSharpshooter.class));
        cards.add(new SetCardInfo("Goblin Snowman", 61, Rarity.RARE, mage.cards.g.GoblinSnowman.class, FULL_ART));
        cards.add(new SetCardInfo("Golgari Thug", 7, Rarity.RARE, mage.cards.g.GolgariThug.class));
        cards.add(new SetCardInfo("Heliod, God of the Sun", 68, Rarity.MYTHIC, mage.cards.h.HeliodGodOfTheSun.class, FULL_ART));
        cards.add(new SetCardInfo("Ink-Eyes, Servant of Oni", 33, Rarity.RARE, mage.cards.i.InkEyesServantOfOni.class));
        cards.add(new SetCardInfo("Inkmoth Nexus", 45, Rarity.RARE, mage.cards.i.InkmothNexus.class));
        cards.add(new SetCardInfo("Iroas, God of Victory", 70, Rarity.MYTHIC, mage.cards.i.IroasGodOfVictory.class, FULL_ART));
        cards.add(new SetCardInfo("Island", 64, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Karametra, God of Harvests", 69, Rarity.MYTHIC, mage.cards.k.KarametraGodOfHarvests.class, FULL_ART));
        cards.add(new SetCardInfo("Keranos, God of Storms", 79, Rarity.MYTHIC, mage.cards.k.KeranosGodOfStorms.class, FULL_ART));
        cards.add(new SetCardInfo("Kruphix, God of Horizons", 73, Rarity.MYTHIC, mage.cards.k.KruphixGodOfHorizons.class, FULL_ART));
        cards.add(new SetCardInfo("Leonin Warleader", 22, Rarity.RARE, mage.cards.l.LeoninWarleader.class));
        cards.add(new SetCardInfo("Life from the Loam", 8, Rarity.RARE, mage.cards.l.LifeFromTheLoam.class));
        cards.add(new SetCardInfo("Lightning Bolt", 83, Rarity.RARE, mage.cards.l.LightningBolt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Bolt", 84, Rarity.RARE, mage.cards.l.LightningBolt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Bolt", 85, Rarity.RARE, mage.cards.l.LightningBolt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Bolt", 86, Rarity.RARE, mage.cards.l.LightningBolt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Greaves", 99, Rarity.RARE, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Marrow-Gnawer", 34, Rarity.RARE, mage.cards.m.MarrowGnawer.class));
        cards.add(new SetCardInfo("Meren of Clan Nel Toth", 52, Rarity.MYTHIC, mage.cards.m.MerenOfClanNelToth.class));
        cards.add(new SetCardInfo("Mirri, Weatherlight Duelist", 26, Rarity.MYTHIC, mage.cards.m.MirriWeatherlightDuelist.class));
        cards.add(new SetCardInfo("Mogis, God of Slaughter", 78, Rarity.MYTHIC, mage.cards.m.MogisGodOfSlaughter.class, FULL_ART));
        cards.add(new SetCardInfo("Mountain", 66, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Mudhole", 62, Rarity.RARE, mage.cards.m.Mudhole.class, FULL_ART));
        cards.add(new SetCardInfo("Narset, Enlightened Master", 53, Rarity.MYTHIC, mage.cards.n.NarsetEnlightenedMaster.class));
        cards.add(new SetCardInfo("Necrotic Ooze", 133, Rarity.RARE, mage.cards.n.NecroticOoze.class));
        cards.add(new SetCardInfo("Nylea, God of the Hunt", 80, Rarity.MYTHIC, mage.cards.n.NyleaGodOfTheHunt.class, FULL_ART));
        cards.add(new SetCardInfo("Oona, Queen of the Fae", 54, Rarity.MYTHIC, mage.cards.o.OonaQueenOfTheFae.class));
        cards.add(new SetCardInfo("Pack Rat", 35, Rarity.RARE, mage.cards.p.PackRat.class));
        cards.add(new SetCardInfo("Pharika, God of Affliction", 82, Rarity.MYTHIC, mage.cards.p.PharikaGodOfAffliction.class, FULL_ART));
        cards.add(new SetCardInfo("Phenax, God of Deception", 75, Rarity.MYTHIC, mage.cards.p.PhenaxGodOfDeception.class, FULL_ART));
        cards.add(new SetCardInfo("Pithing Needle", 44, Rarity.RARE, mage.cards.p.PithingNeedle.class));
        cards.add(new SetCardInfo("Plains", 63, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Purphoros, God of the Forge", 77, Rarity.MYTHIC, mage.cards.p.PurphorosGodOfTheForge.class, FULL_ART));
        cards.add(new SetCardInfo("Qasali Slingers", 24, Rarity.RARE, mage.cards.q.QasaliSlingers.class));
        cards.add(new SetCardInfo("Rat Colony", 36, Rarity.RARE, mage.cards.r.RatColony.class));
        cards.add(new SetCardInfo("Reaper King", 9, Rarity.MYTHIC, mage.cards.r.ReaperKing.class));
        cards.add(new SetCardInfo("Regal Caracal", 23, Rarity.RARE, mage.cards.r.RegalCaracal.class));
        cards.add(new SetCardInfo("Rest in Peace", 96, Rarity.RARE, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Saskia the Unyielding", 55, Rarity.MYTHIC, mage.cards.s.SaskiaTheUnyielding.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 135, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
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
        cards.add(new SetCardInfo("Spell Pierce", 41, Rarity.RARE, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Storm Crow", 60, Rarity.RARE, mage.cards.s.StormCrow.class, FULL_ART));
        cards.add(new SetCardInfo("Swamp", 65, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Swan Song", 91, Rarity.RARE, mage.cards.s.SwanSong.class));
        cards.add(new SetCardInfo("Tamiyo, Field Researcher", 89, Rarity.MYTHIC, mage.cards.t.TamiyoFieldResearcher.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 37, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 38, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 39, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 40, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thassa, God of the Sea", 71, Rarity.MYTHIC, mage.cards.t.ThassaGodOfTheSea.class, FULL_ART));
        cards.add(new SetCardInfo("The Mimeoplasm", 136, Rarity.MYTHIC, mage.cards.t.TheMimeoplasm.class));
        cards.add(new SetCardInfo("The Ur-Dragon", 11, Rarity.MYTHIC, mage.cards.t.TheUrDragon.class));
        cards.add(new SetCardInfo("Voidslime", 167, Rarity.RARE, mage.cards.v.Voidslime.class));
        cards.add(new SetCardInfo("Vraska, Golgari Queen", 90, Rarity.MYTHIC, mage.cards.v.VraskaGolgariQueen.class));
        cards.add(new SetCardInfo("Walking Ballista", 58, Rarity.RARE, mage.cards.w.WalkingBallista.class));
        cards.add(new SetCardInfo("Xenagos, God of Revels", 81, Rarity.MYTHIC, mage.cards.x.XenagosGodOfRevels.class, FULL_ART));
    }
}
