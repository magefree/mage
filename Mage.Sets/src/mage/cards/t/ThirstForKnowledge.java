package mage.cards.t;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ThirstForKnowledge extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard();

    public ThirstForKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Draw three cards. Then discard two cards unless you discard an artifact card.
        DiscardCardCost cost = new DiscardCardCost(filter);
        cost.setText("discard an artifact card instead of discarding two cards");
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2), cost
        ).setText("Then discard two cards unless you discard an artifact card"));
    }

    private ThirstForKnowledge(final ThirstForKnowledge card) {
        super(card);
    }

    @Override
    public ThirstForKnowledge copy() {
        return new ThirstForKnowledge(this);
    }
}
