package mage.abilities.effects.common;

import mage.abilities.Pronoun;

/**
 * @author TheElk801
 */
public class ExileSagaAndReturnTransformedEffect extends ExileAndReturnTransformedSourceEffect {

    public ExileSagaAndReturnTransformedEffect() {
        super(Pronoun.IT, null, true);
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
