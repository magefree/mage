package mage.abilities.effects.common;

import mage.abilities.Pronoun;
import mage.constants.PutCards;

/**
 * @author TheElk801
 */
public class ExileSagaAndReturnTransformedEffect extends ExileAndReturnSourceEffect {

    public ExileSagaAndReturnTransformedEffect() {
        super(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.IT, true, null);
        staticText = "exile this Saga, then return it to the battlefield transformed under your control";
    }

    private ExileSagaAndReturnTransformedEffect(final ExileSagaAndReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public ExileSagaAndReturnTransformedEffect copy() {
        return new ExileSagaAndReturnTransformedEffect(this);
    }
}
