
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AfiyaGrove extends CardImpl {

    public AfiyaGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Afiya Grove enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));

        // At the beginning of your upkeep, move a +1/+1 counter from Afiya Grove onto target creature.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new MoveCounterToTargetFromSourceEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // When Afiya Grove has no +1/+1 counters on it, sacrifice it.
        this.addAbility(new AfiyaGroveNoCountersAbility());
    }

    private AfiyaGrove(final AfiyaGrove card) {
        super(card);
    }

    @Override
    public AfiyaGrove copy() {
        return new AfiyaGrove(this);
    }
}

class MoveCounterToTargetFromSourceEffect extends OneShotEffect {

    public MoveCounterToTargetFromSourceEffect() {
        super(Outcome.Benefit);
        this.staticText = "move a +1/+1 counter from {this} onto target creature";
    }

    public MoveCounterToTargetFromSourceEffect(final MoveCounterToTargetFromSourceEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterToTargetFromSourceEffect copy() {
        return new MoveCounterToTargetFromSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Permanent toPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (toPermanent != null && sourceObject.getCounters(game).getCount(CounterType.P1P1) > 0) {
                sourceObject.removeCounters(CounterType.P1P1.createInstance(), source, game);
                toPermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                game.informPlayers("Moved a +1/+1 counter from " + sourceObject.getLogName() + " to " + toPermanent.getLogName());
            }
            return true;
        }
        return false;
    }
}

class AfiyaGroveNoCountersAbility extends StateTriggeredAbility {

    public AfiyaGroveNoCountersAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public AfiyaGroveNoCountersAbility(final AfiyaGroveNoCountersAbility ability) {
        super(ability);
    }

    @Override
    public AfiyaGroveNoCountersAbility copy() {
        return new AfiyaGroveNoCountersAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} has no +1/+1 counters on it, sacrifice it.";
    }

}
