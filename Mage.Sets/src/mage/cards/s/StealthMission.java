package mage.cards.s;

import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StealthMission extends CardImpl {

    public StealthMission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Put two +1/+1 counters on target creature you control. That creature can't be blocked this turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addEffect(new CantBeBlockedByAllTargetEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, Duration.EndOfTurn
        ).setText("That creature can't be blocked this turn."));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private StealthMission(final StealthMission card) {
        super(card);
    }

    @Override
    public StealthMission copy() {
        return new StealthMission(this);
    }
}
