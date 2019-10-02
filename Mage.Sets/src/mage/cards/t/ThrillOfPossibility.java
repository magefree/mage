package mage.cards.t;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrillOfPossibility extends CardImpl {

    public ThrillOfPossibility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private ThrillOfPossibility(final ThrillOfPossibility card) {
        super(card);
    }

    @Override
    public ThrillOfPossibility copy() {
        return new ThrillOfPossibility(this);
    }
}
