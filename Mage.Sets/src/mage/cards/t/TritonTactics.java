package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TritonTactics extends CardImpl {

    public TritonTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Up to two target creatures each get +0/+3 until end of turn. Untap those creatures. At this turn's next end of combat, tap each creature that was blocked by one of those creatures this turn and it doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                0, 3, Duration.EndOfTurn
        ).setText("Up to two target creatures each get +0/+3 until end of turn"));
        this.getSpellAbility().addEffect(new TritonTacticsUntapEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addWatcher(new TritonTacticsWatcher());
    }

    private TritonTactics(final TritonTactics card) {
        super(card);
    }

    @Override
    public TritonTactics copy() {
        return new TritonTactics(this);
    }
}

class TritonTacticsUntapEffect extends OneShotEffect {

    public TritonTacticsUntapEffect() {
        super(Outcome.Untap);
        staticText = "untap those creatures. At this turn's next end of combat, tap each creature that was blocked " +
                "by one of those creatures this turn and it doesn't untap during its controller's next untap step";
    }

    public TritonTacticsUntapEffect(final TritonTacticsUntapEffect effect) {
        super(effect);
    }

    @Override
    public TritonTacticsUntapEffect copy() {
        return new TritonTacticsUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            permanent.untap(game);
        }
        game.addDelayedTriggeredAbility(new TritonTacticsDelayedTriggeredAbility(permanents, game), source);
        return true;
    }
}

class TritonTacticsDelayedTriggeredAbility extends DelayedTriggeredAbility {

    TritonTacticsDelayedTriggeredAbility(List<Permanent> permanents, Game game) {
        super(TritonTacticsWatcher.makeEffect(permanents, game), Duration.EndOfTurn, true, false);
    }

    private TritonTacticsDelayedTriggeredAbility(final TritonTacticsDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TritonTacticsDelayedTriggeredAbility copy() {
        return new TritonTacticsDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "At this turn's next end of combat, tap each creature that was blocked " +
                "by one of those creatures this turn and it doesn't untap during its controller's next untap step.";
    }
}

class TritonTacticsTapEffect extends OneShotEffect {

    TritonTacticsTapEffect() {
        super(Outcome.Tap);
    }

    private TritonTacticsTapEffect(final TritonTacticsTapEffect effect) {
        super(effect);
    }

    @Override
    public TritonTacticsTapEffect copy() {
        return new TritonTacticsTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        permanents.stream().forEach(permanent -> permanent.tap(source, game));
        game.addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}

class TritonTacticsWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> map = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = Collections.unmodifiableSet(new HashSet<>());

    TritonTacticsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            map.computeIfAbsent(
                    new MageObjectReference(event.getSourceId(), game), x -> new HashSet<>()
            ).add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static Effect makeEffect(List<Permanent> targets, Game game) {
        TritonTacticsWatcher watcher = game.getState().getWatcher(TritonTacticsWatcher.class);
        List<Permanent> permanents = targets
                .stream()
                .map(permanent -> new MageObjectReference(permanent, game))
                .map(mor -> watcher.map.getOrDefault(mor, emptySet))
                .flatMap(Collection::stream)
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new TritonTacticsTapEffect().setTargetPointer(new FixedTargets(permanents, game));
    }
}
