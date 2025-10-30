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
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Ashling's Command", 205, Rarity.RARE, mage.cards.a.AshlingsCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashling's Command", 330, Rarity.RARE, mage.cards.a.AshlingsCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterbloom Bearer", 310, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterbloom Bearer", 352, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterbloom Bearer", 88, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 262, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 349, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deceit", 212, Rarity.MYTHIC, mage.cards.d.Deceit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deceit", 293, Rarity.MYTHIC, mage.cards.d.Deceit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eirdu, Carrier of Dawn", 13, Rarity.MYTHIC, mage.cards.e.EirduCarrierOfDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eirdu, Carrier of Dawn", 286, Rarity.MYTHIC, mage.cards.e.EirduCarrierOfDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emptiness", 222, Rarity.MYTHIC, mage.cards.e.Emptiness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emptiness", 294, Rarity.MYTHIC, mage.cards.e.Emptiness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Figure of Fable", 224, Rarity.RARE, mage.cards.f.FigureOfFable.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Figure of Fable", 372, Rarity.RARE, mage.cards.f.FigureOfFable.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Formidable Speaker", 176, Rarity.RARE, mage.cards.f.FormidableSpeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Formidable Speaker", 366, Rarity.RARE, mage.cards.f.FormidableSpeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 265, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 347, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isilu, Carrier of Twilight", 13, Rarity.MYTHIC, mage.cards.i.IsiluCarrierOfTwilight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isilu, Carrier of Twilight", 286, Rarity.MYTHIC, mage.cards.i.IsiluCarrierOfTwilight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Morningtide's Light", 27, Rarity.MYTHIC, mage.cards.m.MorningtidesLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Morningtide's Light", 301, Rarity.MYTHIC, mage.cards.m.MorningtidesLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutable Explorer", 186, Rarity.RARE, mage.cards.m.MutableExplorer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutable Explorer", 327, Rarity.RARE, mage.cards.m.MutableExplorer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 266, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 350, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 267, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 348, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sygg, Wanderbrine Shield", 288, Rarity.RARE, mage.cards.s.SyggWanderbrineShield.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sygg, Wanderbrine Shield", 76, Rarity.RARE, mage.cards.s.SyggWanderbrineShield.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sygg, Wanderwine Wisdom", 288, Rarity.RARE, mage.cards.s.SyggWanderwineWisdom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sygg, Wanderwine Wisdom", 76, Rarity.RARE, mage.cards.s.SyggWanderwineWisdom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 268, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 351, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
    }
}
