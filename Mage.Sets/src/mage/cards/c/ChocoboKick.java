package mage.cards.c;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChocoboKick extends CardImpl {

    public ChocoboKick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Kicker--Return a land you control to its owner's hand.
        this.addAbility(new KickerAbility(new ReturnToHandChosenControlledPermanentCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_A_LAND)
        )));

        // Target creature you control deals damage equal to its power to target creature an opponent controls. If this spell was kicked, the creature you control deals twice that much damage instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageWithPowerFromOneToAnotherTargetEffect("", 2),
                new DamageWithPowerFromOneToAnotherTargetEffect(), KickedCondition.ONCE, "target creature " +
                "you control deals damage equal to its power to target creature an opponent controls. " +
                "If this spell was kicked, the creature you control deals twice that much damage instead"
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private ChocoboKick(final ChocoboKick card) {
        super(card);
    }

    @Override
    public ChocoboKick copy() {
        return new ChocoboKick(this);
    }
}
