
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PlagueBoiler extends CardImpl {

    public PlagueBoiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // At the beginning of your upkeep, put a plague counter on Plague Boiler.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.PLAGUE.createInstance()), TargetController.YOU, false));
        // {1}{B}{G}: Put a plague counter on Plague Boiler or remove a plague counter from it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PlagueBoilerEffect(), new ManaCostsImpl<>("{1}{B}{G}")));
        // When Plague Boiler has three or more plague counters on it, sacrifice it. If you do, destroy all nonland permanents.
        this.addAbility(new PlagueBoilerTriggeredAbility());

    }

    private PlagueBoiler(final PlagueBoiler card) {
        super(card);
    }

    @Override
    public PlagueBoiler copy() {
        return new PlagueBoiler(this);
    }
}

class PlagueBoilerEffect extends OneShotEffect {

    public PlagueBoilerEffect() {
        super(Outcome.Detriment);
        this.staticText = "Put a plague counter on {this} or remove a plague counter from it";
    }

    public PlagueBoilerEffect(final PlagueBoilerEffect effect) {
        super(effect);
    }

    @Override
    public PlagueBoilerEffect copy() {
        return new PlagueBoilerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (!sourcePermanent.getCounters(game).containsKey(CounterType.PLAGUE) || controller.chooseUse(outcome, "Put a plague counter on? (No removes one)", source, game)) {
                return new AddCountersSourceEffect(CounterType.PLAGUE.createInstance(), true).apply(game, source);
            } else {
                return new RemoveCounterSourceEffect(CounterType.PLAGUE.createInstance()).apply(game, source);
            }
        }
        return false;
    }
}

class PlagueBoilerTriggeredAbility extends TriggeredAbilityImpl {

    public PlagueBoilerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PlagueBoilerSacrificeDestroyEffect(), false);
    }

    public PlagueBoilerTriggeredAbility(final PlagueBoilerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlagueBoilerTriggeredAbility copy() {
        return new PlagueBoilerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId()) && event.getData().equals(CounterType.PLAGUE.getName())) {
            Permanent sourcePermanent = game.getPermanent(this.getSourceId());
            if (sourcePermanent != null && sourcePermanent.getCounters(game).getCount(CounterType.PLAGUE) >= 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When {this} has three or more plague counters on it, " ;
    }
}

class PlagueBoilerSacrificeDestroyEffect extends OneShotEffect {

    public PlagueBoilerSacrificeDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "sacrifice it. If you do, destroy all nonland permanents";
    }

    public PlagueBoilerSacrificeDestroyEffect(final PlagueBoilerSacrificeDestroyEffect effect) {
        super(effect);
    }

    @Override
    public PlagueBoilerSacrificeDestroyEffect copy() {
        return new PlagueBoilerSacrificeDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            if (sourcePermanent.sacrifice(source, game)) {
                return new DestroyAllEffect(new FilterNonlandPermanent()).apply(game, source);
            }
        }
        return false;
    }
}
