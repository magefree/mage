

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author fireshoes
 */
public class BattleForZendikarBlock extends Constructed {

    public BattleForZendikarBlock() {
        super("Constructed - Battle for Zendikar Block");
        setCodes.add(mage.sets.BattleForZendikar.getInstance().getCode());
        setCodes.add(mage.sets.OathOfTheGatewatch.getInstance().getCode());
    }
}
