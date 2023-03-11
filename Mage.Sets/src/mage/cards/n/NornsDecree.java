package mage.cards.n;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.*;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author @stwalsh4118
 */
public final class NornsDecree extends CardImpl {

    public NornsDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        

        // Whenever one or more creatures an opponent controls deal combat damage to you, that player gets a poison counter.
        this.addAbility(new NornsDecreeTriggeredAbility());

        // Whenever a player attacks, if one or more players being attacked are poisoned, the attacking player draws a card.
        this.addAbility(new NornsDecreeTriggeredAbility2());
    }

    private NornsDecree(final NornsDecree card) {
        super(card);
    }

    @Override
    public NornsDecree copy() {
        return new NornsDecree(this);
    }
}

class NornsDecreeTriggeredAbility extends TriggeredAbilityImpl {

    public NornsDecreeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new NornsDecreeEffect(), false);
    }

    public NornsDecreeTriggeredAbility(final NornsDecreeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NornsDecreeTriggeredAbility copy() {
        return new NornsDecreeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent batchEvent = (DamagedPlayerBatchEvent) event;
        System.out.println(batchEvent);
        for (DamagedEvent damagedPlayerEvent : batchEvent.getEvents()) {
            UUID source = game.getPermanent(damagedPlayerEvent.getSourceId()).getControllerId();
            UUID target = damagedPlayerEvent.getTargetId();
            System.out.println(source);
            System.out.println(target);
            if (damagedPlayerEvent.isCombatDamage() && game.getOpponents(this.getControllerId()).contains(source) && target.equals(this.getControllerId())) {
                getEffects().get(0).setTargetPointer(new FixedTarget(source));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures an opponent controls deal combat damage to you, that player gets a poison counter.";
    }
}

class NornsDecreeEffect extends OneShotEffect {

    public NornsDecreeEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player gets a poison counter";
    }

    public NornsDecreeEffect(final NornsDecreeEffect effect) {
        super(effect);
    }

    @Override
    public NornsDecreeEffect copy() {
        return new NornsDecreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.addCounters(CounterType.POISON.createInstance(), player.getId(), source, game);
            return true;
        }
        return true;
    }
}

class NornsDecreeTriggeredAbility2 extends TriggeredAbilityImpl {

    public NornsDecreeTriggeredAbility2() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public NornsDecreeTriggeredAbility2(final NornsDecreeTriggeredAbility2 ability) {
        super(ability);
    }

    @Override
    public NornsDecreeTriggeredAbility2 copy() {
        return new NornsDecreeTriggeredAbility2(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (UUID playerId : game.getOpponents(this.getControllerId())) {
            if (game.getPlayer(playerId).getCounters().getCount(CounterType.POISON) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks, if one or more players being attacked are poisoned, the attacking player draws a card.";
    }
}
