package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Jumpstart2022 extends ExpansionSet {

    private static final Jumpstart2022 instance = new Jumpstart2022();

    public static Jumpstart2022 getInstance() {
        return instance;
    }

    private Jumpstart2022() {
        super("Jumpstart 2022", "J22", ExpansionSet.buildDate(2020, 12, 2), SetType.SUPPLEMENTAL);
        this.blockName = "Jumpstart";
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ardoz, Cobbler of War", 29, Rarity.RARE, mage.cards.a.ArdozCobblerOfWar.class));
        cards.add(new SetCardInfo("Coldsteel Heart", 94, Rarity.UNCOMMON, mage.cards.c.ColdsteelHeart.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", 79, Rarity.MYTHIC, mage.cards.k.KikiJikiMirrorBreaker.class));
    }
}
