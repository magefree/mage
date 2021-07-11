package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ForgottenRealmsCommander extends ExpansionSet {

    private static final ForgottenRealmsCommander instance = new ForgottenRealmsCommander();

    public static ForgottenRealmsCommander getInstance() {
        return instance;
    }

    private ForgottenRealmsCommander() {
        super("Forgotten Realms Commander", "AFC", ExpansionSet.buildDate(2021, 7, 23), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Gratuitous Violence", 127, Rarity.RARE, mage.cards.g.GratuitousViolence.class));
        cards.add(new SetCardInfo("Heroic Intervention", 161, Rarity.RARE, mage.cards.h.HeroicIntervention.class));
        cards.add(new SetCardInfo("Nature's Lore", 164, Rarity.COMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 217, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
    }
}
