package mage.abilities.effects.common;

import mage.abilities.Pronoun;
import mage.constants.PutCards;

/**
 * @author TheElk801
 */
public class ExileSourceAndReturnFaceUpEffect extends ExileAndReturnSourceEffect {

    public ExileSourceAndReturnFaceUpEffect() {
        super(PutCards.BATTLEFIELD, Pronoun.IT, true, null);
        staticText = "exile {this}, then return it to the battlefield. <i>(front face up)</i>";
    }

    private ExileSourceAndReturnFaceUpEffect(final ExileSourceAndReturnFaceUpEffect effect) {
        super(effect);
    }

    @Override
    public ExileSourceAndReturnFaceUpEffect copy() {
        return new ExileSourceAndReturnFaceUpEffect(this);
    }
}
