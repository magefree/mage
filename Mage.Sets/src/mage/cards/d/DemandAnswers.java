package mage.cards.d;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DemandAnswers extends CardImpl {

    public DemandAnswers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // As an additional cost to cast this spell, sacrifice an artifact or discard a card.
        this.getSpellAbility().addCost(new OrCost(
                "sacrifice an artifact or discard a card",
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN), new DiscardCardCost()
        ));

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private DemandAnswers(final DemandAnswers card) {
        super(card);
    }

    @Override
    public DemandAnswers copy() {
        return new DemandAnswers(this);
    }
}
