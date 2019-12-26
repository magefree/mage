

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ScarsOfMirrodinBlock extends Constructed {

    public ScarsOfMirrodinBlock() {
        super("Constructed - Scars of Mirrodin Block");
        setCodes.add(mage.sets.ScarsOfMirrodin.getInstance().getCode());
        setCodes.add(mage.sets.MirrodinBesieged.getInstance().getCode());
        setCodes.add(mage.sets.NewPhyrexia.getInstance().getCode());
    }

}
