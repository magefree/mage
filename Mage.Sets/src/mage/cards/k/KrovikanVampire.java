package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
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
                new KrovikanVampireInterveningIfCondition(),
                false);
        ability.addWatcher(new KrovikanVampireCreaturesDamagedWatcher());
        ability.addWatcher(new KrovikanVampireCreaturesDiedWatcher());
        this.addAbility(ability);
    }

    private KrovikanVampire(final KrovikanVampire card) {
        super(card);
    }

    @Override
    public KrovikanVampire copy() {
        return new KrovikanVampire(this);
    }
}

class KrovikanVampireEffect extends OneShotEffect {

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
        Set<UUID> creaturesAffected = (Set<UUID>) game.getState().getValue(source.getSourceId() + "creatureToGainControl");
        if (creaturesAffected == null || controller == null || krovikanVampire == null) {
            return false;
        }

        creaturesAffected.stream().map((creatureId) -> {
            controller.moveCards(game.getCard(creatureId), Zone.BATTLEFIELD, source, game, false, false, false, null);
            return creatureId;
        }).map((creatureId) -> {
            OneShotEffect effect = new SacrificeTargetEffect();
            effect.setText("Sacrifice this if Krovikan Vampire leaves the battlefield or its current controller loses control of it.");
            effect.setTargetPointer(new FixedTarget(creatureId, game));
            return effect;
        }).map((effect) -> new KrovikanVampireDelayedTriggeredAbility(effect, krovikanVampire.getId())).forEachOrdered((dTA) -> {
            game.addDelayedTriggeredAbility(dTA, source);
        });
        creaturesAffected.clear();
        return true;
    }

    @Override
    public KrovikanVampireEffect copy() {
        return new KrovikanVampireEffect(this);
    }
}

class KrovikanVampireInterveningIfCondition implements Condition {

    Set<UUID> creaturesAffected = new HashSet<>();

    @Override
    public boolean apply(Game game, Ability source) {
        KrovikanVampireCreaturesDiedWatcher watcherDied = game.getState().getWatcher(KrovikanVampireCreaturesDiedWatcher.class);
        KrovikanVampireCreaturesDamagedWatcher watcherDamaged = game.getState().getWatcher(KrovikanVampireCreaturesDamagedWatcher.class);
        if (watcherDied == null) {
            return false;
        }

        Set<UUID> creaturesThatDiedThisTurn = watcherDied.getDiedThisTurn();
        creaturesThatDiedThisTurn.stream().filter((mor) -> (watcherDamaged != null)).forEachOrdered((mor) -> {
            watcherDamaged.getDamagedBySource().stream().filter((mor2) -> (mor2 != null
                    && mor == mor2)).forEachOrdered((_item) -> {
                creaturesAffected.add(mor);
            });
        });

        if (creaturesAffected == null || creaturesAffected.isEmpty()) {
            return false;
        }

        game.getState().setValue(source.getSourceId() + "creatureToGainControl", creaturesAffected);
        return true;
    }

    @Override
    public String toString() {
        return "if a creature dealt damage by Krovikan Vampire this turn died";
    }
}

class KrovikanVampireCreaturesDamagedWatcher extends Watcher {

    private final Set<UUID> damagedBySource = new HashSet<>();

    public KrovikanVampireCreaturesDamagedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT || !sourceId.equals(event.getSourceId())) {
            return;
        }

        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)) {
            return;
        }

        damagedBySource.add(event.getTargetId());
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

    private final Set<UUID> diedThisTurn = new HashSet<>();

    public KrovikanVampireCreaturesDiedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()
                    && zEvent.getTarget() != null
                    && zEvent.getTarget().isCreature(game)) {
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
        return event.getType() == GameEvent.EventType.LOST_CONTROL
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL || event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            return event.getTargetId().equals(krovikanVampire);
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
