package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ZendikarRisingCommander extends ExpansionSet {

    private static final ZendikarRisingCommander instance = new ZendikarRisingCommander();

    public static ZendikarRisingCommander getInstance() {
        return instance;
    }

    private ZendikarRisingCommander() {
        super("Zendikar Rising Commander", "ZNC", ExpansionSet.buildDate(2020, 9, 25), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Anowon, the Ruin Thief", 1, Rarity.MYTHIC, mage.cards.a.AnowonTheRuinThief.class));
        cards.add(new SetCardInfo("Enigma Thief", 4, Rarity.RARE, mage.cards.e.EnigmaThief.class));
        cards.add(new SetCardInfo("Geode Rager", 6, Rarity.RARE, mage.cards.g.GeodeRager.class));
        cards.add(new SetCardInfo("Obuun, Mul Daya Ancestor", 2, Rarity.MYTHIC, mage.cards.o.ObuunMulDayaAncestor.class));
        cards.add(new SetCardInfo("Trove Warden", 3, Rarity.RARE, mage.cards.t.TroveWarden.class));
        cards.add(new SetCardInfo("Whispersteel Dagger", 5, Rarity.RARE, mage.cards.w.WhispersteelDagger.class));
    }
}
