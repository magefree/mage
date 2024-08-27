package mage.cards.w;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildfireHowl extends CardImpl {

    public WildfireHowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Wildfire Howl deals 2 damage to each creature. If the gift was promised, instead Wildfire Howl deals 1 damage to any target and 2 damage to each creature.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(1),
                new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE),
                GiftWasPromisedCondition.TRUE, "{this} deals 2 damage to each creature. " +
                "If the gift was promised, instead {this} deals 1 damage to any target and 2 damage to each creature"
        ).addEffect(new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE)));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
                GiftWasPromisedCondition.TRUE, new TargetAnyTarget()
        ));
    }

    private WildfireHowl(final WildfireHowl card) {
        super(card);
    }

    @Override
    public WildfireHowl copy() {
        return new WildfireHowl(this);
    }
}
