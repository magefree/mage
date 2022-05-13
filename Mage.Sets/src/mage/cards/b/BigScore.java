package mage.cards.b;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BigScore extends CardImpl {

    public BigScore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // As an additional cost to cast this spell, draw a card.
        this.getSpellAbility().addCost(new DiscardCardCost(false));

        // Draw two cards and create two Treasure tokens.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken(), 2).concatBy("and"));
    }

    private BigScore(final BigScore card) {
        super(card);
    }

    @Override
    public BigScore copy() {
        return new BigScore(this);
    }
}
