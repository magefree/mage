package mage.cards.t;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class TurfWar extends CardImpl {

    public TurfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // When Turf War enters the battlefield, for each player, put a contested counter on target land that player controls.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TurfWarCounterEffect())
                .setTargetAdjuster(TurfWarAdjuster.instance));

        // Whenever a creature deals combat damage to a player, if that player controls one or more lands with contested counters on them, that creature's controller gains control of one of those lands of their choice and untaps it.
        this.addAbility(new TurfWarTriggeredAbility());
    }

    private TurfWar(final TurfWar card) {
        super(card);
    }

    @Override
    public TurfWar copy() {
        return new TurfWar(this);
    }
}

class TurfWarCounterEffect extends OneShotEffect {

    public TurfWarCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each player, put a contested counter on target land that player controls";
    }

    private TurfWarCounterEffect(final TurfWarCounterEffect effect) {
        super(effect);
    }

    @Override
    public TurfWarCounterEffect copy() {
        return new TurfWarCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        boolean success = false;
        for (Target target : source.getTargets()) {
            for (UUID uuid : target.getTargets()) {
                Permanent permanent = game.getPermanent(uuid);
                if (permanent != null && permanent.addCounters(CounterType.CONTESTED.createInstance(), source, game)) {
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName()
                            + " puts a contested counter on " + permanent.getLogName());
                    success = true;
                }
            }
        }
        return success;
    }
}

enum TurfWarAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterLandPermanent filter = new FilterLandPermanent("land controlled by " + player.getName());
            filter.add(new ControllerIdPredicate(playerId));
            ability.addTarget(new TargetLandPermanent(filter));
        }
    }
}

class TurfWarTriggeredAbility extends TriggeredAbilityImpl {

    public TurfWarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TurfWarControlEffect());
        setTriggerPhrase("Whenever a creature deals combat damage to a player, if that player controls one or more lands with contested counters on them, ");
    }

    private TurfWarTriggeredAbility(final TurfWarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TurfWarTriggeredAbility copy() {
        return new TurfWarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (!damageEvent.isCombatDamage()) {
            return false;
        }
        UUID creatureId = damageEvent.getSourceId();
        Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
        if (creature == null) {
            return false;
        }
        UUID playerId = damageEvent.getPlayerId();
        PlayerList inRange = game.getState().getPlayersInRange(controllerId, game);
        if (!inRange.contains(playerId) || !inRange.contains(creature.getControllerId())) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LAND, playerId, game)) {
            if (permanent.getCounters(game).getCount(CounterType.CONTESTED) > 0) {
                this.getEffects().setValue("creature", creatureId);
                this.getEffects().setValue("player", playerId);
                return true;
            }
        }
        return false;
    }
}

class TurfWarControlEffect extends OneShotEffect {

    public TurfWarControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "that creature's controller gains control of one of those lands of their choice and untaps it";
    }

    private TurfWarControlEffect(final TurfWarControlEffect effect) {
        super(effect);
    }

    @Override
    public TurfWarControlEffect copy() {
        return new TurfWarControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanentOrLKIBattlefield(getUUIDValue("creature"));
        if (creature == null) {
            return false;
        }
        Player creatureController = game.getPlayer(creature.getControllerId());
        if (creatureController == null) {
            return false;
        }
        Player damagedPlayer = game.getPlayer(getUUIDValue("player"));
        if (damagedPlayer == null) {
            return false;
        }
        FilterLandPermanent filter = new FilterLandPermanent("land with a contested counter controlled by " + damagedPlayer.getName());
        filter.add(new ControllerIdPredicate(damagedPlayer.getId()));
        filter.add(TurfWarPredicate.instance);
        TargetLandPermanent target = new TargetLandPermanent(1, 1, filter, true);
        if (!target.canChoose(creatureController.getId(), source, game)) {
            return false;
        }
        creatureController.chooseTarget(Outcome.GainControl, target, source, game);
        Permanent land = game.getPermanent(target.getFirstTarget());
        if (land == null) {
            return false;
        }
        GainControlTargetEffect effect = new GainControlTargetEffect(Duration.Custom, creatureController.getId());
        effect.setTargetPointer(new FixedTarget(land, game));
        game.addEffect(effect, source);
        land.untap(game);
        return true;
    }

    private UUID getUUIDValue(String key) {
        Object value = this.getValue(key);
        if (!(value instanceof UUID)) {
            return null;
        }
        return (UUID) value;
    }
}

enum TurfWarPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getCounters(game).getCount(CounterType.CONTESTED) > 0;
    }
}
