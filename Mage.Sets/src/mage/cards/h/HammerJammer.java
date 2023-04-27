
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author spjspj & L_J
 */
public final class HammerJammer extends CardImpl {

    public HammerJammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Hammer Jammer enters the battlefield, roll a six-sided die. Hammer Jammer enters the battlefield with a number of +1/+1 counters on it equal to the result.
        this.addAbility(new EntersBattlefieldAbility(new HammerJammerEntersEffect(CounterType.P1P1.createInstance())));

        // Whenever you roll a die, remove all +1/+1 counters from Hammer Jammer, then put a number of +1/+1 counters on it equal to the result.
        this.addAbility(new HammerJammerTriggeredAbility());

    }

    private HammerJammer(final HammerJammer card) {
        super(card);
    }

    @Override
    public HammerJammer copy() {
        return new HammerJammer(this);
    }
}

class HammerJammerEntersEffect extends EntersBattlefieldWithXCountersEffect {

    public HammerJammerEntersEffect(Counter counter) {
        super(counter);
    }

    public HammerJammerEntersEffect(EntersBattlefieldWithXCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects"); // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game, appliedEffects);
            return super.apply(game, source);
        }
        return false;
    }

    @Override
    public HammerJammerEntersEffect copy() {
        return new HammerJammerEntersEffect(this);
    }
}

class HammerJammerTriggeredAbility extends TriggeredAbilityImpl {

    public HammerJammerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HammerJammerEffect(), false);
        setTriggerPhrase("Whenever you roll a die, ");
    }

    public HammerJammerTriggeredAbility(final HammerJammerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HammerJammerTriggeredAbility copy() {
        return new HammerJammerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        // silver border card must look for "result" instead "natural result"
        // planar die will trigger it with 0 amount
        if (this.isControlledBy(drEvent.getPlayerId())) {
            this.getEffects().setValue("rolled", drEvent.getResult());
            return true;
        }
        return false;
    }
}

class HammerJammerEffect extends OneShotEffect {

    public HammerJammerEffect() {
        super(Outcome.Benefit);
        this.staticText = "remove all +1/+1 counters from {this}, then put a number of +1/+1 counters on it equal to the result";
    }

    public HammerJammerEffect(final HammerJammerEffect effect) {
        super(effect);
    }

    @Override
    public HammerJammerEffect copy() {
        return new HammerJammerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            Integer amount = (Integer) getValue("rolled");
            if (amount != null) {
                permanent.removeCounters(CounterType.P1P1.createInstance(permanent.getCounters(game).getCount(CounterType.P1P1)), source, game);
                if (amount > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
                }
                return true;
            }
        }
        return false;

    }
}
