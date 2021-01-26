package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvigoratingSurge extends CardImpl {

    public InvigoratingSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on that creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new InvigoratingSurgeEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private InvigoratingSurge(final InvigoratingSurge card) {
        super(card);
    }

    @Override
    public InvigoratingSurge copy() {
        return new InvigoratingSurge(this);
    }
}

class InvigoratingSurgeEffect extends OneShotEffect {

    InvigoratingSurgeEffect() {
        super(Outcome.Benefit);
        staticText = ", then double the number of +1/+1 counters on that creature";
    }

    private InvigoratingSurgeEffect(final InvigoratingSurgeEffect effect) {
        super(effect);
    }

    @Override
    public InvigoratingSurgeEffect copy() {
        return new InvigoratingSurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int counterCount = permanent.getCounters(game).getCount(CounterType.P1P1);
        return counterCount > 0 && permanent.addCounters(CounterType.P1P1.createInstance(counterCount), source.getControllerId(), source, game);
    }
}