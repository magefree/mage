package mage.cards.r;

import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RuthlessDisposal extends CardImpl {

    public RuthlessDisposal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // As an additional cost to cast Ruthless Disposal, discard a card and sacrifice a creature.
        this.getSpellAbility().addCost(new CompositeCost(
                new DiscardCardCost(),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT),
                "discard a card and sacrifice a creature"
        ));

        // Two target creatures each get -13/-13 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-13, -13, Duration.EndOfTurn)
                .setText("Two target creatures each get -13/-13 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    private RuthlessDisposal(final RuthlessDisposal card) {
        super(card);
    }

    @Override
    public RuthlessDisposal copy() {
        return new RuthlessDisposal(this);
    }
}
