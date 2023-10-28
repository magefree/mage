package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class TheLostCavernsOfIxalan extends ExpansionSet {

    private static final TheLostCavernsOfIxalan instance = new TheLostCavernsOfIxalan();

    public static TheLostCavernsOfIxalan getInstance() {
        return instance;
    }

    private TheLostCavernsOfIxalan() {
        super("The Lost Caverns of Ixalan", "LCI", ExpansionSet.buildDate(2023, 11, 17), SetType.EXPANSION);
        this.hasBoosters = false; // TODO: enable boosters
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abuelo, Ancestral Echo", 219, Rarity.RARE, mage.cards.a.AbueloAncestralEcho.class));
        cards.add(new SetCardInfo("Abyssal Gorestalker", 87, Rarity.UNCOMMON, mage.cards.a.AbyssalGorestalker.class));
        cards.add(new SetCardInfo("Amalia Benavides Aguirre", 221, Rarity.RARE, mage.cards.a.AmaliaBenavidesAguirre.class));
        cards.add(new SetCardInfo("Bartolome del Presidio", 224, Rarity.UNCOMMON, mage.cards.b.BartolomeDelPresidio.class));
        cards.add(new SetCardInfo("Bladewheel Chariot", 36, Rarity.UNCOMMON, mage.cards.b.BladewheelChariot.class));
        cards.add(new SetCardInfo("Breeches, Eager Pillager", 137, Rarity.RARE, mage.cards.b.BreechesEagerPillager.class));
        cards.add(new SetCardInfo("Captain Storm, Cosmium Raider", 227, Rarity.UNCOMMON, mage.cards.c.CaptainStormCosmiumRaider.class));
        cards.add(new SetCardInfo("Careening Mine Cart", 247, Rarity.UNCOMMON, mage.cards.c.CareeningMineCart.class));
        cards.add(new SetCardInfo("Cavern of Souls", 269, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Cenote Scout", 178, Rarity.UNCOMMON, mage.cards.c.CenoteScout.class));
        cards.add(new SetCardInfo("Chart a Course", 48, Rarity.UNCOMMON, mage.cards.c.ChartACourse.class));
        cards.add(new SetCardInfo("Clay-Fired Bricks", 6, Rarity.UNCOMMON, mage.cards.c.ClayFiredBricks.class));
        cards.add(new SetCardInfo("Coati Scavenger", 179, Rarity.UNCOMMON, mage.cards.c.CoatiScavenger.class));
        cards.add(new SetCardInfo("Confounding Riddle", 50, Rarity.UNCOMMON, mage.cards.c.ConfoundingRiddle.class));
        cards.add(new SetCardInfo("Cosmium Kiln", 6, Rarity.UNCOMMON, mage.cards.c.CosmiumKiln.class));
        cards.add(new SetCardInfo("Deeproot Pilgrimage", 52, Rarity.RARE, mage.cards.d.DeeprootPilgrimage.class));
        cards.add(new SetCardInfo("Didact Echo", 53, Rarity.COMMON, mage.cards.d.DidactEcho.class));
        cards.add(new SetCardInfo("Dinotomaton", 144, Rarity.COMMON, mage.cards.d.Dinotomaton.class));
        cards.add(new SetCardInfo("Enterprising Scallywag", 148, Rarity.UNCOMMON, mage.cards.e.EnterprisingScallywag.class));
        cards.add(new SetCardInfo("Forest", 401, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geological Appraiser", 150, Rarity.UNCOMMON, mage.cards.g.GeologicalAppraiser.class));
        cards.add(new SetCardInfo("Get Lost", 14, Rarity.RARE, mage.cards.g.GetLost.class));
        cards.add(new SetCardInfo("Ghalta, Stampede Tyrant", 185, Rarity.MYTHIC, mage.cards.g.GhaltaStampedeTyrant.class));
        cards.add(new SetCardInfo("Gishath, Sun's Avatar", 229, Rarity.MYTHIC, mage.cards.g.GishathSunsAvatar.class));
        cards.add(new SetCardInfo("Goldfury Strider", 152, Rarity.UNCOMMON, mage.cards.g.GoldfuryStrider.class));
        cards.add(new SetCardInfo("Growing Rites of Itlimoc", 188, Rarity.RARE, mage.cards.g.GrowingRitesOfItlimoc.class));
        cards.add(new SetCardInfo("Helping Hand", 17, Rarity.UNCOMMON, mage.cards.h.HelpingHand.class));
        cards.add(new SetCardInfo("Hidden Cataract", 273, Rarity.COMMON, mage.cards.h.HiddenCataract.class));
        cards.add(new SetCardInfo("Hidden Courtyard", 274, Rarity.COMMON, mage.cards.h.HiddenCourtyard.class));
        cards.add(new SetCardInfo("Hidden Necropolis", 275, Rarity.COMMON, mage.cards.h.HiddenNecropolis.class));
        cards.add(new SetCardInfo("Hidden Nursery", 276, Rarity.COMMON, mage.cards.h.HiddenNursery.class));
        cards.add(new SetCardInfo("Hidden Volcano", 277, Rarity.COMMON, mage.cards.h.HiddenVolcano.class));
        cards.add(new SetCardInfo("Huatli, Poet of Unity", 189, Rarity.MYTHIC, mage.cards.h.HuatliPoetOfUnity.class));
        cards.add(new SetCardInfo("Island", 395, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Itlimoc, Cradle of the Sun", 188, Rarity.RARE, mage.cards.i.ItlimocCradleOfTheSun.class));
        cards.add(new SetCardInfo("Jadelight Spelunker", 196, Rarity.RARE, mage.cards.j.JadelightSpelunker.class));
        cards.add(new SetCardInfo("Kellan, Daring Traveler", 231, Rarity.RARE, mage.cards.k.KellanDaringTraveler.class));
        cards.add(new SetCardInfo("Kinjalli's Dawnrunner", 19, Rarity.UNCOMMON, mage.cards.k.KinjallisDawnrunner.class));
        cards.add(new SetCardInfo("Malamet War Scribe", 21, Rarity.UNCOMMON, mage.cards.m.MalametWarScribe.class));
        cards.add(new SetCardInfo("Miner's Guidewing", 24, Rarity.COMMON, mage.cards.m.MinersGuidewing.class));
        cards.add(new SetCardInfo("Mischievous Pup", 25, Rarity.UNCOMMON, mage.cards.m.MischievousPup.class));
        cards.add(new SetCardInfo("Mountain", 399, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nurturing Bristleback", 203, Rarity.COMMON, mage.cards.n.NurturingBristleback.class));
        cards.add(new SetCardInfo("Ojer Axonil, Deepest Might", 158, Rarity.MYTHIC, mage.cards.o.OjerAxonilDeepestMight.class));
        cards.add(new SetCardInfo("Ojer Taq, Deepest Foundation", 26, Rarity.MYTHIC, mage.cards.o.OjerTaqDeepestFoundation.class));
        cards.add(new SetCardInfo("Oltec Cloud Guard", 28, Rarity.COMMON, mage.cards.o.OltecCloudGuard.class));
        cards.add(new SetCardInfo("Palani's Hatcher", 237, Rarity.RARE, mage.cards.p.PalanisHatcher.class));
        cards.add(new SetCardInfo("Plains", 393, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Poison Dart Frog", 207, Rarity.COMMON, mage.cards.p.PoisonDartFrog.class));
        cards.add(new SetCardInfo("Pugnacious Hammerskull", 208, Rarity.RARE, mage.cards.p.PugnaciousHammerskull.class));
        cards.add(new SetCardInfo("Quintorius Kand", 238, Rarity.MYTHIC, mage.cards.q.QuintoriusKand.class));
        cards.add(new SetCardInfo("Rampaging Spiketail", 116, Rarity.COMMON, mage.cards.r.RampagingSpiketail.class));
        cards.add(new SetCardInfo("Resplendent Angel", 32, Rarity.MYTHIC, mage.cards.r.ResplendentAngel.class));
        cards.add(new SetCardInfo("Restless Prairie", 281, Rarity.RARE, mage.cards.r.RestlessPrairie.class));
        cards.add(new SetCardInfo("Roar of the Fifth People", 189, Rarity.MYTHIC, mage.cards.r.RoarOfTheFifthPeople.class));
        cards.add(new SetCardInfo("Saheeli, the Sun's Brilliance", 239, Rarity.MYTHIC, mage.cards.s.SaheeliTheSunsBrilliance.class));
        cards.add(new SetCardInfo("Sanguine Evangelist", 34, Rarity.RARE, mage.cards.s.SanguineEvangelist.class));
        cards.add(new SetCardInfo("Skullcap Snail", 119, Rarity.COMMON, mage.cards.s.SkullcapSnail.class));
        cards.add(new SetCardInfo("Song of Stupefaction", 77, Rarity.COMMON, mage.cards.s.SongOfStupefaction.class));
        cards.add(new SetCardInfo("Spring-Loaded Sawblades", 36, Rarity.UNCOMMON, mage.cards.s.SpringLoadedSawblades.class));
        cards.add(new SetCardInfo("Spyglass Siren", 78, Rarity.UNCOMMON, mage.cards.s.SpyglassSiren.class));
        cards.add(new SetCardInfo("Sunbird Effigy", 262, Rarity.UNCOMMON, mage.cards.s.SunbirdEffigy.class));
        cards.add(new SetCardInfo("Sunbird Standard", 262, Rarity.UNCOMMON, mage.cards.s.SunbirdStandard.class));
        cards.add(new SetCardInfo("Swamp", 397, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Civilization", 26, Rarity.MYTHIC, mage.cards.t.TempleOfCivilization.class));
        cards.add(new SetCardInfo("Temple of Power", 158, Rarity.MYTHIC, mage.cards.t.TempleOfPower.class));
        cards.add(new SetCardInfo("The Belligerent", 225, Rarity.RARE, mage.cards.t.TheBelligerent.class));
        cards.add(new SetCardInfo("The Grim Captain", 266, Rarity.RARE, mage.cards.t.TheGrimCaptain.class));
        cards.add(new SetCardInfo("The Skullspore Nexus", 212, Rarity.MYTHIC, mage.cards.t.TheSkullsporeNexus.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 216, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Threefold Thunderhulk", 265, Rarity.RARE, mage.cards.t.ThreefoldThunderhulk.class));
        cards.add(new SetCardInfo("Throne of the Grim Captain", 266, Rarity.RARE, mage.cards.t.ThroneOfTheGrimCaptain.class));
        cards.add(new SetCardInfo("Treasure Cove", 267, Rarity.RARE, mage.cards.t.TreasureCove.class));
        cards.add(new SetCardInfo("Treasure Map", 267, Rarity.RARE, mage.cards.t.TreasureMap.class));
        cards.add(new SetCardInfo("Vito, Fanatic of Aclazotz", 243, Rarity.MYTHIC, mage.cards.v.VitoFanaticOfAclazotz.class));
        cards.add(new SetCardInfo("Waterwind Scout", 84, Rarity.COMMON, mage.cards.w.WaterwindScout.class));
    }
}
