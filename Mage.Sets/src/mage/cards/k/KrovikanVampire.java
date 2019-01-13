package mage.cards.k;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class KrovikanVampire extends CardImpl {

    public KrovikanVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of each end step, if a creature dealt damage by Krovikan Vampire this turn died, put that card onto the battlefield under your control. Sacrifice it when you lose control of Krovikan Vampire.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new KrovikanVampireEffect(),
                TargetController.ANY,
                KrovikanVampireInterveningIfCondition.instance,
                false);
        ability.addWatcher(new KrovikanVampireCreaturesDamagedWatcher());
        ability.addWatcher(new KrovikanVampireCreaturesDiedWatcher());
        this.addAbility(ability);
    }

    public KrovikanVampire(final KrovikanVampire card) {
        super(card);
    }

    @Override
    public KrovikanVampire copy() {
        return new KrovikanVampire(this);
    }
}

class KrovikanVampireEffect extends OneShotEffect {

    Set<UUID> creaturesAffected = new HashSet<>();

    KrovikanVampireEffect() {
        super(Outcome.Neutral);
        staticText = "put that card onto the battlefield under your control. Sacrifice it when you lose control of {this}";
    }

    KrovikanVampireEffect(KrovikanVampireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent krovikanVampire = game.getPermanent(source.getSourceId());
        creaturesAffected = (Set<UUID>) game.getState().getValue(source.getSourceId() + "creatureToGainControl");
        if (creaturesAffected != null
                && controller != null
                && krovikanVampire != null) {
            for (UUID creatureId : creaturesAffected) {
                controller.moveCards(game.getCard(creatureId), Zone.BATTLEFIELD, source, game, false, false, false, null);
                OneShotEffect effect = new SacrificeTargetEffect();
                effect.setText("Sacrifice this if Krovikan Vampire leaves the battlefield or its current controller loses control of it.");
                effect.setTargetPointer(new FixedTarget(creatureId));
                KrovikanVampireDelayedTriggeredAbility dTA = new KrovikanVampireDelayedTriggeredAbility(effect, krovikanVampire.getId());
                game.addDelayedTriggeredAbility(dTA, source);
            }
            creaturesAffected.clear();
            return true;
        }
        return false;
    }

    @Override
    public KrovikanVampireEffect copy() {
        return new KrovikanVampireEffect(this);
    }
}

enum KrovikanVampireInterveningIfCondition implements Condition {

    instance;
    Set<UUID> creaturesAffected = new HashSet<>();

    @Override
    public boolean apply(Game game, Ability source) {
        KrovikanVampireCreaturesDiedWatcher watcherDied = game.getState().getWatcher(KrovikanVampireCreaturesDiedWatcher.class);
        KrovikanVampireCreaturesDamagedWatcher watcherDamaged = game.getState().getWatcher(KrovikanVampireCreaturesDamagedWatcher.class);
        if (watcherDied != null) {
            Set<UUID> creaturesThatDiedThisTurn = watcherDied.diedThisTurn;
            for (UUID mor : creaturesThatDiedThisTurn) {
                if (watcherDamaged != null) {
                    for (UUID mor2 : watcherDamaged.getDamagedBySource()) {
                        if (mor2 != null
                                && mor == mor2) {
                            creaturesAffected.add(mor);
                        }
                    }
                }
            }
            if (creaturesAffected != null
                    && creaturesAffected.size() > 0) {
                game.getState().setValue(source.getSourceId() + "creatureToGainControl", creaturesAffected);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if a creature dealt damage by Krovikan Vampire this turn died";
    }
}

class KrovikanVampireCreaturesDamagedWatcher extends Watcher {

    public final Set<UUID> damagedBySource = new HashSet<>();

    public KrovikanVampireCreaturesDamagedWatcher() {
        super(KrovikanVampireCreaturesDamagedWatcher.class, WatcherScope.GAME);
    }

    public KrovikanVampireCreaturesDamagedWatcher(final KrovikanVampireCreaturesDamagedWatcher watcher) {
        super(watcher);
        this.damagedBySource.addAll(watcher.damagedBySource);
    }

    @Override
    public KrovikanVampireCreaturesDamagedWatcher copy() {
        return new KrovikanVampireCreaturesDamagedWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE
                && sourceId.equals(event.getSourceId())) {
            damagedBySource.add(event.getTargetId());
        }
    }

    public Set<UUID> getDamagedBySource() {
        return this.damagedBySource;
    }

    @Override
    public void reset() {
        damagedBySource.clear();
    }
}

class KrovikanVampireCreaturesDiedWatcher extends Watcher {

    public final Set<UUID> diedThisTurn = new HashSet<>();

    public KrovikanVampireCreaturesDiedWatcher() {
        super(KrovikanVampireCreaturesDiedWatcher.class, WatcherScope.GAME);
    }

    public KrovikanVampireCreaturesDiedWatcher(final KrovikanVampireCreaturesDiedWatcher watcher) {
        super(watcher);
        this.diedThisTurn.addAll(watcher.diedThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()
                    && zEvent.getTarget() != null
                    && zEvent.getTarget().isCreature()) {
                diedThisTurn.add(zEvent.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        diedThisTurn.clear();
    }

    public Set<UUID> getDiedThisTurn() {
        return this.diedThisTurn;
    }

    @Override
    public KrovikanVampireCreaturesDiedWatcher copy() {
        return new KrovikanVampireCreaturesDiedWatcher(this);
    }
}

class KrovikanVampireDelayedTriggeredAbility extends DelayedTriggeredAbility {

    UUID krovikanVampire;

    KrovikanVampireDelayedTriggeredAbility(Effect effect, UUID krovikanVampire) {
        super(effect, Duration.EndOfGame, true);
        this.krovikanVampire = krovikanVampire;
    }

    KrovikanVampireDelayedTriggeredAbility(KrovikanVampireDelayedTriggeredAbility ability) {
        super(ability);
        this.krovikanVampire = ability.krovikanVampire;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.LOST_CONTROL
                || event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.LOST_CONTROL
                && event.getSourceId().equals(krovikanVampire)) {
            return true;
        }
        if (event.getType() == EventType.ZONE_CHANGE
                && event.getTargetId().equals(krovikanVampire)) {
            return true;
        }
        return false;
    }

    @Override
    public KrovikanVampireDelayedTriggeredAbility copy() {
        return new KrovikanVampireDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Control of Krovikan Vampire lost";
    }
}
