

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author fireshoes
 */
public class ShadowsOverInnistradBlock extends Constructed {

    public ShadowsOverInnistradBlock() {
        super("Constructed - Shadows over Innistrad Block");
        setCodes.add(mage.sets.ShadowsOverInnistrad.getInstance().getCode());
        setCodes.add(mage.sets.EldritchMoon.getInstance().getCode());
    }
}
