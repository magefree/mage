package mage.cards.e;

import java.util.UUID;

import mage.abilities.condition.common.TargetObjectMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TiagoMDG
 */
public final class EntsFury extends CardImpl {

    private static final FilterControlledCreaturePermanent FILTER = new FilterControlledCreaturePermanent("creature with power 4 or greater");

    static {
        FILTER.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public EntsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Put a +1/+1 counter on target creature you control if its power is 4 or greater.
        // Then that creature gets +1/+1 until end of turn and fights target
        // creature you don't control.
        Effect conditionalEffect = new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TargetObjectMatchesFilterCondition(FILTER));
        conditionalEffect.setText("Put a +1/+1 counter on target creature you control if its power is 4 or greater");
        ContinuousEffect boostEffect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        boostEffect.setText("Then that creature gets +1/+1 until end of turn");
        Effect fightEffect = new FightTargetsEffect(false);
        fightEffect.setText(" and fights target creature you don't control.");
        this.getSpellAbility().addEffect(conditionalEffect);
        this.getSpellAbility().addEffect(boostEffect);
        this.getSpellAbility().addEffect(fightEffect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private EntsFury(final EntsFury card) {
        super(card);
    }

    @Override
    public EntsFury copy() {
        return new EntsFury(this);
    }
}
