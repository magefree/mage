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
        cards.add(new SetCardInfo("Bartolome del Presidio", 224, Rarity.UNCOMMON, mage.cards.b.BartolomeDelPresidio.class));
        cards.add(new SetCardInfo("Breeches, Eager Pillager", 137, Rarity.RARE, mage.cards.b.BreechesEagerPillager.class));
        cards.add(new SetCardInfo("Careening Mine Cart", 247, Rarity.UNCOMMON, mage.cards.c.CareeningMineCart.class));
        cards.add(new SetCardInfo("Cavern of Souls", 269, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Cenote Scout", 178, Rarity.UNCOMMON, mage.cards.c.CenoteScout.class));
        cards.add(new SetCardInfo("Chart a Course", 48, Rarity.UNCOMMON, mage.cards.c.ChartACourse.class));
        cards.add(new SetCardInfo("Deeproot Pilgrimage", 52, Rarity.RARE, mage.cards.d.DeeprootPilgrimage.class));
        cards.add(new SetCardInfo("Didact Echo", 53, Rarity.COMMON, mage.cards.d.DidactEcho.class));
        cards.add(new SetCardInfo("Dinotomaton", 144, Rarity.COMMON, mage.cards.d.Dinotomaton.class));
        cards.add(new SetCardInfo("Forest", 401, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geological Appraiser", 150, Rarity.UNCOMMON, mage.cards.g.GeologicalAppraiser.class));
        cards.add(new SetCardInfo("Ghalta, Stampede Tyrant", 185, Rarity.MYTHIC, mage.cards.g.GhaltaStampedeTyrant.class));
        cards.add(new SetCardInfo("Gishath, Sun's Avatar", 229, Rarity.MYTHIC, mage.cards.g.GishathSunsAvatar.class));
        cards.add(new SetCardInfo("Growing Rites of Itlimoc", 188, Rarity.RARE, mage.cards.g.GrowingRitesOfItlimoc.class));
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
        cards.add(new SetCardInfo("Miner's Guidewing", 24, Rarity.COMMON, mage.cards.m.MinersGuidewing.class));
        cards.add(new SetCardInfo("Mischievous Pup", 25, Rarity.UNCOMMON, mage.cards.m.MischievousPup.class));
        cards.add(new SetCardInfo("Mountain", 399, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ojer Axonil, Deepest Might", 158, Rarity.MYTHIC, mage.cards.o.OjerAxonilDeepestMight.class));
        cards.add(new SetCardInfo("Ojer Taq, Deepest Foundation", 26, Rarity.MYTHIC, mage.cards.o.OjerTaqDeepestFoundation.class));
        cards.add(new SetCardInfo("Oltec Cloud Guard", 28, Rarity.UNCOMMON, mage.cards.o.OltecCloudGuard.class));
        cards.add(new SetCardInfo("Plains", 393, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Poison Dart Frog", 207, Rarity.COMMON, mage.cards.p.PoisonDartFrog.class));
        cards.add(new SetCardInfo("Rampaging Spiketail", 116, Rarity.COMMON, mage.cards.r.RampagingSpiketail.class));
        cards.add(new SetCardInfo("Restless Prairie", 281, Rarity.RARE, mage.cards.r.RestlessPrairie.class));
        cards.add(new SetCardInfo("Roar of the Fifth People", 189, Rarity.MYTHIC, mage.cards.r.RoarOfTheFifthPeople.class));
        cards.add(new SetCardInfo("Saheeli, the Sun's Brilliance", 239, Rarity.MYTHIC, mage.cards.s.SaheeliTheSunsBrilliance.class));
        cards.add(new SetCardInfo("Sanguine Evangelist", 34, Rarity.RARE, mage.cards.s.SanguineEvangelist.class));
        cards.add(new SetCardInfo("Song of Stupefaction", 77, Rarity.COMMON, mage.cards.s.SongOfStupefaction.class));
        cards.add(new SetCardInfo("Spyglass Siren", 78, Rarity.UNCOMMON, mage.cards.s.SpyglassSiren.class));
        cards.add(new SetCardInfo("Swamp", 397, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Civilization", 26, Rarity.MYTHIC, mage.cards.t.TempleOfCivilization.class));
        cards.add(new SetCardInfo("Temple of Power", 158, Rarity.MYTHIC, mage.cards.t.TempleOfPower.class));
        cards.add(new SetCardInfo("The Skullspore Nexus", 212, Rarity.MYTHIC, mage.cards.t.TheSkullsporeNexus.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 216, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Threefold Thunderhulk", 390, Rarity.RARE, mage.cards.t.ThreefoldThunderhulk.class));
        cards.add(new SetCardInfo("Treasure Cove", 261, Rarity.RARE, mage.cards.t.TreasureCove.class));
        cards.add(new SetCardInfo("Treasure Map", 261, Rarity.RARE, mage.cards.t.TreasureMap.class));
        cards.add(new SetCardInfo("Vito, Fanatic of Aclazotz", 243, Rarity.MYTHIC, mage.cards.v.VitoFanaticOfAclazotz.class));
        cards.add(new SetCardInfo("Waterwind Scout", 84, Rarity.COMMON, mage.cards.w.WaterwindScout.class));
    }
}
