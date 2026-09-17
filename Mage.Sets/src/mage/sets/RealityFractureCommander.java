package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class RealityFractureCommander extends ExpansionSet {

    private static final RealityFractureCommander instance = new RealityFractureCommander();

    public static RealityFractureCommander getInstance() {
        return instance;
    }

    private RealityFractureCommander() {
        super("Reality Fracture Commander", "FRC", ExpansionSet.buildDate(2026, 10, 2), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Akroma, Angel of Fury", 19, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfFury.class));
        cards.add(new SetCardInfo("Arcane Signet", 20, Rarity.UNCOMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Command Tower", 22, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Darksteel Angel", 98, Rarity.RARE, mage.cards.d.DarksteelAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Darksteel Angel", 13, Rarity.RARE, mage.cards.d.DarksteelAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memnarch, the Warden", 15, Rarity.RARE, mage.cards.m.MemnarchTheWarden.class));
        cards.add(new SetCardInfo("Reflecting Pool", 23, Rarity.RARE, mage.cards.r.ReflectingPool.class));
        cards.add(new SetCardInfo("Sol Ring", 21, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Turbulent Crater", 16, Rarity.RARE, mage.cards.t.TurbulentCrater.class));
        cards.add(new SetCardInfo("Turbulent Shore", 17, Rarity.RARE, mage.cards.t.TurbulentShore.class));
        cards.add(new SetCardInfo("Turbulent Wetlands", 18, Rarity.RARE, mage.cards.t.TurbulentWetlands.class));
    }
}
