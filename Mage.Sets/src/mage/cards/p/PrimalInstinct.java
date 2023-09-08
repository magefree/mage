
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class PrimalInstinct extends CardImpl {

    public PrimalInstinct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{G}");

        // Put a +1/+1 counter on target creature, then double the number of +1/+1 counters on that creature.
        this.getSpellAbility().addEffect(new PrimalInstictEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PrimalInstinct(final PrimalInstinct card) {
        super(card);
    }

    @Override
    public PrimalInstinct copy() {
        return new PrimalInstinct(this);
    }
}

class PrimalInstictEffect extends OneShotEffect {

    public PrimalInstictEffect() {
        super(Outcome.BoostCreature);
        staticText = "Put a +1/+1 counter on target creature, then double the number of +1/+1 counters on that creature.";
    }

    private PrimalInstictEffect(final PrimalInstictEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (target != null) {
                target.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                int addCounterCount = target.getCounters(game).getCount(CounterType.P1P1);
                game.informPlayers("Counters " + addCounterCount);
                target.addCounters(CounterType.P1P1.createInstance(addCounterCount), source.getControllerId(), source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public PrimalInstictEffect copy() {
        return new PrimalInstictEffect(this);
    }

}
