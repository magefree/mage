package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author jbureau88
 */
public final class OptimusPrimeAutobotLeader extends CardImpl {
    public OptimusPrimeAutobotLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);
        this.color.setBlack(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        this.getSpellAbility().addWatcher(new OptimusPrimeAutobotLeaderWatcher());

        // Whenever you attack, bolster 2.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new BolsterEffect(2), 1));

        // The chosen creature gains trample until end of turn.
        this.addAbility(new AutobotLeaderAddTrampleTriggeredAbility());

        // When that creature deals combat damage to a player this turn, convert Optimus Prime.
        this.addAbility(new AutobotLeaderConvertTriggeredAbility());
    }

    private OptimusPrimeAutobotLeader(final OptimusPrimeAutobotLeader card) {
        super(card);
    }

    @Override
    public OptimusPrimeAutobotLeader copy() {
        return new OptimusPrimeAutobotLeader(this);
    }
}

class AutobotLeaderAddTrampleTriggeredAbility extends TriggeredAbilityImpl {

    private final Set<UUID> triggeringCreatures = new HashSet<>();

    public AutobotLeaderAddTrampleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        this.optional = false;
    }

    public AutobotLeaderAddTrampleTriggeredAbility(final AutobotLeaderAddTrampleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AutobotLeaderAddTrampleTriggeredAbility copy() {
        return new AutobotLeaderAddTrampleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED) {
            Permanent creature = game.getPermanent(event.getTargetId());
            if (creature != null && creature.isControlledBy(getControllerId()) && !triggeringCreatures.contains(creature.getId())) {
                OptimusPrimeAutobotLeaderWatcher watcher = game.getState().getWatcher(OptimusPrimeAutobotLeaderWatcher.class);
                if (watcher != null && watcher.getModifiedCreatures().contains(creature.getId())) {
                    getEffects().setTargetPointer(new FixedTargets(creature.getId()));
                    triggeringCreatures.add(creature.getId());
                    return true;
                }
            }
        }

        if (event.getType() == GameEvent.EventType.END_COMBAT_STEP) {
            triggeringCreatures.clear();
        }

        return false;
    }

    @Override
    public String getRule() {
        return "The chosen creature gains trample until end of turn.";
    }
}

class AutobotLeaderConvertTriggeredAbility extends TriggeredAbilityImpl {

    public AutobotLeaderConvertTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AutobotLeaderConvertEffect());
        this.optional = false;
    }

    public AutobotLeaderConvertTriggeredAbility(final AutobotLeaderConvertTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AutobotLeaderConvertTriggeredAbility copy() {
        return new AutobotLeaderConvertTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(getControllerId())) {
                OptimusPrimeAutobotLeaderWatcher watcher = game.getState().getWatcher(OptimusPrimeAutobotLeaderWatcher.class);
                if (watcher != null) {
                    return watcher.getModifiedCreatures().contains(creature.getId());
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When that creature deals combat damage to a player this turn, convert Optimus Prime.";
    }
}

class AutobotLeaderConvertEffect extends OneShotEffect {
    AutobotLeaderConvertEffect() {
        super(Outcome.Transform);
        this.staticText = "";
    }

    private AutobotLeaderConvertEffect(final AutobotLeaderConvertEffect effect) {
        super(effect);
    }

    @Override
    public AutobotLeaderConvertEffect copy() {
        return new AutobotLeaderConvertEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        permanent.transform(source, game);
        return true;
    }
}

class OptimusPrimeAutobotLeaderWatcher extends Watcher {

    private final Set<UUID> modifiedCreatures = new HashSet<>();

    OptimusPrimeAutobotLeaderWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP) {
            reset();
            modifiedCreatures.clear();
        }

        if (event.getType() != GameEvent.EventType.COUNTER_ADDED) {
            return;
        }

        Card source = game.getCard(event.getSourceId());
        Card targetCreature = game.getCard(event.getTargetId());

        if (targetCreature == null || source.getSecondCardFace() == null) {
            return;
        }

        if (targetCreature.isCreature(game) && source.getName().contains("Optimus Prime")) {
            Permanent permanent = game.getPermanent(source.getId());
            if (permanent.isTransformed() && permanent.isControlledBy(getControllerId())) {
                modifiedCreatures.add(targetCreature.getId());
            }
        }
    }

    public List<UUID> getModifiedCreatures() {
        return new ArrayList<>(modifiedCreatures);
    }
}
