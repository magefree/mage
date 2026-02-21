package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StudyTheClassics extends CardImpl {

    public StudyTheClassics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Put a +1/+1 counter on target creature, then double the number of +1/+1 counters on it. You gain life equal to the number of +1/+1 counters on that creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new DoubleCountersTargetEffect(CounterType.P1P1)
                .withTargetDescription("it").concatBy(", then"));
        this.getSpellAbility().addEffect(new GainLifeEffect(StudyTheClassicsValue.instance));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StudyTheClassics(final StudyTheClassics card) {
        super(card);
    }

    @Override
    public StudyTheClassics copy() {
        return new StudyTheClassics(this);
    }
}

enum StudyTheClassicsValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(effect.getTargetPointer().getFirst(game, sourceAbility))
                .map(game::getPermanent)
                .map(permanent -> permanent.getCounters(game).getCount(CounterType.P1P1))
                .orElse(0);
    }

    @Override
    public StudyTheClassicsValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "+1/+1 counters on that creature";
    }

    @Override
    public String toString() {
        return "1";
    }
}
