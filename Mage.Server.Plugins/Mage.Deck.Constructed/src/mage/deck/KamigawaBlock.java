

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class KamigawaBlock extends Constructed {

    public KamigawaBlock() {
        super("Constructed - Kamigawa Block");
        setCodes.add(mage.sets.ChampionsOfKamigawa.getInstance().getCode());
        setCodes.add(mage.sets.BetrayersOfKamigawa.getInstance().getCode());
        setCodes.add(mage.sets.SaviorsOfKamigawa.getInstance().getCode());
    }

}
