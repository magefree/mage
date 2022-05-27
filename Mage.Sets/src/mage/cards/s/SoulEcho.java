
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
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
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public final class SoulEcho extends CardImpl {

    public SoulEcho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{X}{W}{W}");

        // Soul Echo enters the battlefield with X echo counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.ECHO.createInstance())));

        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // At the beginning of your upkeep, sacrifice Soul Echo if there are no echo counters on it. 
        // Otherwise, target opponent may choose that for each 1 damage that would be dealt to you until your next upkeep, you remove an echo counter from Soul Echo instead.
        Effect effect = new ConditionalOneShotEffect(new SacrificeSourceEffect(), new SoulEchoOpponentsChoiceEffect(), new SourceHasCounterCondition(CounterType.ECHO, 0, 0), "sacrifice {this} if there are no echo counters on it. Otherwise, target opponent may choose that for each 1 damage that would be dealt to you until your next upkeep, you remove an echo counter from {this} instead");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SoulEcho(final SoulEcho card) {
        super(card);
    }

    @Override
    public SoulEcho copy() {
        return new SoulEcho(this);
    }
}

class SoulEchoOpponentsChoiceEffect extends OneShotEffect {

    public SoulEchoOpponentsChoiceEffect() {
        super(Outcome.PreventDamage);
        staticText = "target opponent may choose that for each 1 damage that would be dealt to you until your next upkeep, you remove an echo counter from {this} instead";
    }

    public SoulEchoOpponentsChoiceEffect(final SoulEchoOpponentsChoiceEffect effect) {
        super(effect);
    }

    @Override
    public SoulEchoOpponentsChoiceEffect copy() {
        return new SoulEchoOpponentsChoiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null && opponent != null && permanent != null) {
            if (opponent.chooseUse(outcome, "Have all damage dealt to " + controller.getLogName() + " be decremented from echo counters on " + permanent.getLogName() + " until " + controller.getLogName() + "'s next upkeep instead?", source, game)) {
                game.informPlayers("Until " + controller.getLogName() + "'s next upkeep, for each 1 damage that would be dealt to " + controller.getLogName() + ", an echo counter from " + permanent.getLogName() + " is removed instead");
                game.addEffect(new SoulEchoReplacementEffect(), source);
            }
            return true;
        }
        return false;
    }
}

class SoulEchoReplacementEffect extends ReplacementEffectImpl {
    
    private boolean sameStep = true;

    SoulEchoReplacementEffect() {
        super(Duration.Custom, Outcome.PreventDamage);
    }

    SoulEchoReplacementEffect(final SoulEchoReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UPKEEP) {
            if (!sameStep && game.isActivePlayer(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                return true;
            }
        } else {
            sameStep = false;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        int damage = damageEvent.getAmount();
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            permanent.removeCounters(CounterType.ECHO.createInstance(damage), source, game);
            game.informPlayers(controller.getLogName() + ": " + damage + " damage replaced with " + permanent.getLogName());
        }
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
    public SoulEchoReplacementEffect copy() {
        return new SoulEchoReplacementEffect(this);
    }
}
