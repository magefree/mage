package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DinosaurBeastToken;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class QuartzwoodCrasher extends CardImpl {

    public QuartzwoodCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever one or more creatures you control with trample deal combat damage to a player, create an X/X green Dinosaur Beast creature token with trample, where X is the amount of damage those creatures dealt to that player.
        this.addAbility(new QuartzwoodCrasherTriggeredAbility());
    }

    private QuartzwoodCrasher(final QuartzwoodCrasher card) {
        super(card);
    }

    @Override
    public QuartzwoodCrasher copy() {
        return new QuartzwoodCrasher(this);
    }
}

class QuartzwoodCrasherTriggeredAbility extends TriggeredAbilityImpl {

    private final Set<UUID> damagedPlayerIds = new HashSet<>();

    QuartzwoodCrasherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new QuartzwoodCrasherEffect(), false);
        this.addWatcher(new QuartzwoodCrasherWatcher());
    }

    private QuartzwoodCrasherTriggeredAbility(final QuartzwoodCrasherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuartzwoodCrasherTriggeredAbility copy() {
        return new QuartzwoodCrasherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            damagedPlayerIds.clear();
            return false;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(controllerId)
                    && creature.hasAbility(TrampleAbility.getInstance(), game)
                    && !damagedPlayerIds.contains(event.getTargetId())) {
                damagedPlayerIds.add(event.getTargetId());
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control with trample deal combat damage to a player, " +
                "create an X/X green Dinosaur Beast creature token with trample, " +
                "where X is the amount of damage those creatures dealt to that player.";
    }
}

class QuartzwoodCrasherEffect extends OneShotEffect {

    QuartzwoodCrasherEffect() {
        super(Outcome.Benefit);
    }

    private QuartzwoodCrasherEffect(final QuartzwoodCrasherEffect effect) {
        super(effect);
    }

    @Override
    public QuartzwoodCrasherEffect copy() {
        return new QuartzwoodCrasherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        QuartzwoodCrasherWatcher watcher = game.getState().getWatcher(QuartzwoodCrasherWatcher.class);
        return watcher != null && new DinosaurBeastToken(
                watcher.getDamage(targetPointer.getFirst(game, source), source.getControllerId())
        ).putOntoBattlefield(1, game, source, source.getControllerId());
    }
}

class QuartzwoodCrasherWatcher extends Watcher {

    private final Map<UUID, Map<UUID, Integer>> damageMap = new HashMap<>();

    QuartzwoodCrasherWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST
                || event.getType() == GameEvent.EventType.CLEANUP_STEP_POST) {
            damageMap.clear();
            return;
        }
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER
                || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return;
        }
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature == null || !creature.hasAbility(TrampleAbility.getInstance(), game)) {
            return;
        }
        damageMap
                .computeIfAbsent(event.getTargetId(), x -> new HashMap<>())
                .compute(creature.getControllerId(), (uuid, i) -> i == null ? event.getAmount() : event.getAmount() + i);
    }

    public int getDamage(UUID damagedPlayerId, UUID controllerId) {
        if (!damageMap.containsKey(damagedPlayerId)) {
            return 0;
        }
        return damageMap.get(damagedPlayerId).getOrDefault(controllerId, 0);
    }
}
