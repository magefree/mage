
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class LostCavernsOfIxalanCommander extends ExpansionSet {

    private static final LostCavernsOfIxalanCommander instance = new LostCavernsOfIxalanCommander();

    public static LostCavernsOfIxalanCommander getInstance() {
        return instance;
    }

    private LostCavernsOfIxalanCommander() {
        super("Lost Caverns of Ixalan Commander", "LCC", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Amulet of Vigor", 103, Rarity.RARE, mage.cards.a.AmuletOfVigor.class));
        cards.add(new SetCardInfo("Arcane Signet", 299, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Chalice of the Void", 105, Rarity.MYTHIC, mage.cards.c.ChaliceOfTheVoid.class));
        cards.add(new SetCardInfo("Chimil, the Inner Sun", 106, Rarity.MYTHIC, mage.cards.c.ChimilTheInnerSun.class));
        cards.add(new SetCardInfo("Chromatic Orrery", 107, Rarity.MYTHIC, mage.cards.c.ChromaticOrrery.class));
        cards.add(new SetCardInfo("Coat of Arms", 108, Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Coercive Portal", 109, Rarity.MYTHIC, mage.cards.c.CoercivePortal.class));
        cards.add(new SetCardInfo("Colossus Hammer", 110, Rarity.UNCOMMON, mage.cards.c.ColossusHammer.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 111, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Expedition Map", 112, Rarity.UNCOMMON, mage.cards.e.ExpeditionMap.class));
        cards.add(new SetCardInfo("Fist of Suns", 113, Rarity.RARE, mage.cards.f.FistOfSuns.class));
        cards.add(new SetCardInfo("Lightning Greaves", 114, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Mimic Vat", 115, Rarity.RARE, mage.cards.m.MimicVat.class));
        cards.add(new SetCardInfo("Mist Dancer", 76, Rarity.RARE, mage.cards.m.MistDancer.class));
        cards.add(new SetCardInfo("Order of Sacred Dusk", 97, Rarity.RARE, mage.cards.o.OrderOfSacredDusk.class));
        cards.add(new SetCardInfo("Path of Ancestry", 346, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Strionic Resonator", 116, Rarity.RARE, mage.cards.s.StrionicResonator.class));
        cards.add(new SetCardInfo("Temple Bell", 117, Rarity.RARE, mage.cards.t.TempleBell.class));
        cards.add(new SetCardInfo("Thought Vessel", 118, Rarity.UNCOMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Tributary Instructor", 96, Rarity.RARE, mage.cards.t.TributaryInstructor.class));
        cards.add(new SetCardInfo("Wave Goodbye", 79, Rarity.RARE, mage.cards.w.WaveGoodbye.class));
        cards.add(new SetCardInfo("Wedding Ring", 102, Rarity.MYTHIC, mage.cards.w.WeddingRing.class));
        cards.add(new SetCardInfo("Whispersilk Cloak", 119, Rarity.UNCOMMON, mage.cards.w.WhispersilkCloak.class));
        cards.add(new SetCardInfo("Worn Powerstone", 120, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
    }
}
