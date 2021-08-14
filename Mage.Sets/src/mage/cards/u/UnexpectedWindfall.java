package mage.cards.u;

import java.util.UUID;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class UnexpectedWindfall extends CardImpl {

    public UnexpectedWindfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Draw two cards and create two Treasure tokens.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken(), 2).concatBy("and"));
    }

    private UnexpectedWindfall(final UnexpectedWindfall card) {
        super(card);
    }

    @Override
    public UnexpectedWindfall copy() {
        return new UnexpectedWindfall(this);
    }
}
