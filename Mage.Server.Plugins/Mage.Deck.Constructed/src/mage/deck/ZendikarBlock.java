/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ZendikarBlock extends Constructed {

    public ZendikarBlock() {
        super("Constructed - Zendikar Block");
        setCodes.add(mage.sets.Zendikar.getInstance().getCode());
        setCodes.add(mage.sets.Worldwake.getInstance().getCode());
        setCodes.add(mage.sets.RiseOfTheEldrazi.getInstance().getCode());
    }

}
