package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WolverineBestThereIs extends CardImpl {

    public WolverineBestThereIs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Unrivaled Lethality -- Double all damage Wolverine would deal.
        this.addAbility(new SimpleStaticAbility(new WolverineBestThereIsEffect()).withFlavorWord("Unrivaled Lethality"));

        // At the beginning of each end step, if Wolverine dealt damage to another creature this turn, put a +1/+1 counter on him.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on him"),
                false, WolverineBestThereIsCondition.instance
        ), new WolverineBestThereIsWatcher());

        // {1}{G}: Regenerate Wolverine.
        this.addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{G}")));
    }

    private WolverineBestThereIs(final WolverineBestThereIs card) {
        super(card);
    }

    @Override
    public WolverineBestThereIs copy() {
        return new WolverineBestThereIs(this);
    }
}

class WolverineBestThereIsEffect extends ReplacementEffectImpl {

    WolverineBestThereIsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "double all damage {this} would deal";
    }

    private WolverineBestThereIsEffect(final WolverineBestThereIsEffect effect) {
        super(effect);
    }

    @Override
    public WolverineBestThereIsEffect copy() {
        return new WolverineBestThereIsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}

enum WolverineBestThereIsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return WolverineBestThereIsWatcher.check(source, game);
    }

    @Override
    public String toString() {
        return "{this} dealt damage to another creature this turn";
    }
}

class WolverineBestThereIsWatcher extends Watcher {

    private final Set<MageObjectReference> set = new HashSet<>();

    WolverineBestThereIsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT
                || event.getTargetId().equals(event.getSourceId())) {
            return;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            set.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean check(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(WolverineBestThereIsWatcher.class)
                .set
                .stream()
                .anyMatch(mor -> mor.refersTo(source, game));
    }
}
