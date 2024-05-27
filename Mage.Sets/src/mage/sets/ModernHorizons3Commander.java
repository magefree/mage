package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons3Commander extends ExpansionSet {

    private static final ModernHorizons3Commander instance = new ModernHorizons3Commander();

    public static ModernHorizons3Commander getInstance() {
        return instance;
    }

    private ModernHorizons3Commander() {
        super("Modern Horizons 3 Commander", "M3C", ExpansionSet.buildDate(2024, 6, 7), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Apex Devastator", 220, Rarity.MYTHIC, mage.cards.a.ApexDevastator.class));
        cards.add(new SetCardInfo("Archon of Cruelty", 197, Rarity.MYTHIC, mage.cards.a.ArchonOfCruelty.class));
        cards.add(new SetCardInfo("Azlask, the Swelling Scourge", 5, Rarity.MYTHIC, mage.cards.a.AzlaskTheSwellingScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azlask, the Swelling Scourge", 9, Rarity.MYTHIC, mage.cards.a.AzlaskTheSwellingScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azlask, the Swelling Scourge", 17, Rarity.MYTHIC, mage.cards.a.AzlaskTheSwellingScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azlask, the Swelling Scourge", 25, Rarity.MYTHIC, mage.cards.a.AzlaskTheSwellingScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodbraid Challenger", 70, Rarity.RARE, mage.cards.b.BloodbraidChallenger.class));
        cards.add(new SetCardInfo("Cayth, Famed Mechanist", 6, Rarity.MYTHIC, mage.cards.c.CaythFamedMechanist.class));
        cards.add(new SetCardInfo("Crib Swap", 168, Rarity.UNCOMMON, mage.cards.c.CribSwap.class));
        cards.add(new SetCardInfo("Drowner of Hope", 182, Rarity.RARE, mage.cards.d.DrownerOfHope.class));
        cards.add(new SetCardInfo("Final Act", 52, Rarity.RARE, mage.cards.f.FinalAct.class));
        cards.add(new SetCardInfo("Hideous Taskmaster", 57, Rarity.RARE, mage.cards.h.HideousTaskmaster.class));
        cards.add(new SetCardInfo("March from Velis Vel", 48, Rarity.RARE, mage.cards.m.MarchFromVelisVel.class));
        cards.add(new SetCardInfo("Oblivion Sower", 158, Rarity.MYTHIC, mage.cards.o.OblivionSower.class));
        cards.add(new SetCardInfo("Siege-Gang Lieutenant", 61, Rarity.RARE, mage.cards.s.SiegeGangLieutenant.class));
    }
}
