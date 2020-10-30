package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g18
 * @author JayDi85
 */
public final class M19GiftPack extends ExpansionSet {

    private static final M19GiftPack instance = new M19GiftPack();

    public static M19GiftPack getInstance() {
        return instance;
    }

    private M19GiftPack() {
        super("M19 Gift Pack", "G18", ExpansionSet.buildDate(2018, 11, 16), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Angelic Guardian", "GP1", Rarity.RARE, mage.cards.a.AngelicGuardian.class));
        cards.add(new SetCardInfo("Angler Turtle", "GP2", Rarity.RARE, mage.cards.a.AnglerTurtle.class));
        cards.add(new SetCardInfo("Immortal Phoenix", "GP4", Rarity.RARE, mage.cards.i.ImmortalPhoenix.class));
        cards.add(new SetCardInfo("Rampaging Brontodon", "GP5", Rarity.RARE, mage.cards.r.RampagingBrontodon.class));
        cards.add(new SetCardInfo("Vengeant Vampire", "GP3", Rarity.RARE, mage.cards.v.VengeantVampire.class));
    }
}
