package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p2hg
 */
public class TwoHeadedGiantTournament extends ExpansionSet {

    private static final TwoHeadedGiantTournament instance = new TwoHeadedGiantTournament();

    public static TwoHeadedGiantTournament getInstance() {
        return instance;
    }

    private TwoHeadedGiantTournament() {
        super("Two-Headed Giant Tournament", "P2HG", ExpansionSet.buildDate(2005, 12, 9), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Underworld Dreams", 1, Rarity.RARE, mage.cards.u.UnderworldDreams.class));
     }
}
