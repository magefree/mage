package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class NeonDynastyCommander extends ExpansionSet {

    private static final NeonDynastyCommander instance = new NeonDynastyCommander();

    public static NeonDynastyCommander getInstance() {
        return instance;
    }

    private NeonDynastyCommander() {
        super("Neon Dynasty Commander", "NEC", ExpansionSet.buildDate(2022, 2, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chishiro, the Shattered Blade", 1, Rarity.MYTHIC, mage.cards.c.ChishiroTheShatteredBlade.class));
        cards.add(new SetCardInfo("Kotori, Pilot Prodigy", 2, Rarity.MYTHIC, mage.cards.k.KotoriPilotProdigy.class));
        cards.add(new SetCardInfo("Myojin of Roaring Blades", 36, Rarity.RARE, mage.cards.m.MyojinOfRoaringBlades.class));
    }
}
