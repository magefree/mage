package mage.cards.b;

import java.util.*;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DauntAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author muz
 */
public final class BristlebaneOutrider extends CardImpl {

    public BristlebaneOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // This creature can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // As long as another creature entered the battlefield under your control this turn, this creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
            BristlebaneOutriderCondition.instance, "as long as another creature entered the battlefield under your control this turn, this creature gets +2/+0."
        )), new BristlebaneOutriderWatcher());

    }

    private BristlebaneOutrider(final BristlebaneOutrider card) {
        super(card);
    }

    @Override
    public BristlebaneOutrider copy() {
        return new BristlebaneOutrider(this);
    }
}

enum BristlebaneOutriderCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return BristlebaneOutriderWatcher.checkPlayer(source, game);
    }
}

class BristlebaneOutriderWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> creatureMap = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = Collections.unmodifiableSet(new HashSet<>());

    BristlebaneOutriderWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null) {
            return;
        }
        if (permanent.isCreature(game)) {
            creatureMap
                    .computeIfAbsent(permanent.getControllerId(), x -> new HashSet<>())
                    .add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatureMap.clear();
    }

    static boolean checkPlayer(Ability source, Game game) {
        BristlebaneOutriderWatcher watcher = game.getState().getWatcher(BristlebaneOutriderWatcher.class);
        return watcher
                .creatureMap
                .getOrDefault(source.getControllerId(), emptySet)
                .stream()
                .anyMatch(mor -> !mor.refersTo(source.getSourceObject(game), game));
    }
}
