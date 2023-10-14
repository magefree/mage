
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class DelayingShield extends CardImpl {

    public DelayingShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // If damage would be dealt to you, put that many delay counters on Delaying Shield instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DelayingShieldReplacementEffect()));

        // At the beginning of your upkeep, remove all delay counters from Delaying Shield. For each delay counter removed this way, you lose 1 life unless you pay {1}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DelayingShieldUpkeepEffect(), TargetController.YOU, false));
    }

    private DelayingShield(final DelayingShield card) {
        super(card);
    }

    @Override
    public DelayingShield copy() {
        return new DelayingShield(this);
    }
}

class DelayingShieldReplacementEffect extends ReplacementEffectImpl {

    DelayingShieldReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "If damage would be dealt to you, put that many delay counters on {this} instead";
    }

    private DelayingShieldReplacementEffect(final DelayingShieldReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        new AddCountersSourceEffect(CounterType.DELAY.createInstance(damageEvent.getAmount()), true).apply(game, source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getControllerId());
    }

    @Override
    public DelayingShieldReplacementEffect copy() {
        return new DelayingShieldReplacementEffect(this);
    }
}

class DelayingShieldUpkeepEffect extends OneShotEffect {

    DelayingShieldUpkeepEffect() {
        super(Outcome.Benefit);
        this.staticText = "remove all delay counters from {this}. For each delay counter removed this way, you lose 1 life unless you pay {1}{W}";
    }

    private DelayingShieldUpkeepEffect(final DelayingShieldUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public DelayingShieldUpkeepEffect copy() {
        return new DelayingShieldUpkeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int numCounters = permanent.getCounters(game).getCount(CounterType.DELAY);
            permanent.removeCounters(CounterType.DELAY.createInstance(numCounters), source, game);
            for (int i = numCounters; i > 0; i--) {
                if (controller.chooseUse(Outcome.Benefit, "Pay {1}{W}? (" + i + " counters left to pay)", source, game)) {
                    Cost cost = new ManaCostsImpl<>("{1}{W}");
                    if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                        continue;
                    }
                }
                new LoseLifeSourceControllerEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
