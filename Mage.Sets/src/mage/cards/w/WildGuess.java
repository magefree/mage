package mage.cards.w;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author North
 */
public final class WildGuess extends CardImpl {

    public WildGuess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}");

        // As an additional cost to cast Wild Guess, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private WildGuess(final WildGuess card) {
        super(card);
    }

    @Override
    public WildGuess copy() {
        return new WildGuess(this);
    }
}
