package mage.cards.i;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsatiableAppetite extends CardImpl {

    public InsatiableAppetite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // You may sacrifice a Food. If you do, target creature gets +5/+5 until end of turn. Otherwise, that creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new BoostTargetEffect(5, 5, Duration.EndOfTurn),
                new BoostTargetEffect(3, 3, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_FOOD))
        ).setText("You may sacrifice a Food. If you do, target creature gets +5/+5 until end of turn. " +
                "Otherwise, that creature gets +3/+3 until end of turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private InsatiableAppetite(final InsatiableAppetite card) {
        super(card);
    }

    @Override
    public InsatiableAppetite copy() {
        return new InsatiableAppetite(this);
    }
}
