package mage.cards.r;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RushingRiver extends CardImpl {

    public RushingRiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(StaticFilters.FILTER_LAND)));

        // Return target nonland permanent to its owner's hand. If Rushing River was kicked, return another target nonland permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect()
                .setText("Return target nonland permanent to its owner's hand. " +
                        "If this spell was kicked, return another target nonland permanent to its owner's hand"));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(KickedCondition.ONCE,
                new TargetNonlandPermanent(2)));
    }

    private RushingRiver(final RushingRiver card) {
        super(card);
    }

    @Override
    public RushingRiver copy() {
        return new RushingRiver(this);
    }
}
