package mage.cards.d;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class DeepwayNavigator extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.MERFOLK, "each other Merfolk you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DeepwayNavigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this creature enters, untap each other Merfolk you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapAllEffect(filter)));

        // As long as you attacked with three or more Merfolk this turn, Merfolk you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(
                        1, 0, Duration.WhileOnBattlefield, filter, false
                ), DeepwayNavigatorCondition.instance, "as long as you attacked " +
                "with three or more Merfolk this turn, Merfolk you control get +1/+0"
        )).addHint(DeepwayNavigatorValue.getHint()), new DeepwayNavigatorWatcher());
    }

    private DeepwayNavigator(final DeepwayNavigator card) {
        super(card);
    }

    @Override
    public DeepwayNavigator copy() {
        return new DeepwayNavigator(this);
    }
}

enum DeepwayNavigatorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DeepwayNavigatorValue.instance.calculate(game, source, null) >= 3;
    }
}

enum DeepwayNavigatorValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Merfolk you attacked with this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DeepwayNavigatorWatcher.getCount(game, sourceAbility);
    }

    @Override
    public DeepwayNavigatorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class DeepwayNavigatorWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();

    DeepwayNavigatorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.hasSubtype(SubType.MERFOLK, game)) {
            map.computeIfAbsent(permanent.getControllerId(), x -> new HashSet<>())
                    .add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getCount(Game game, Ability source) {
        return game.getState()
                .getWatcher(DeepwayNavigatorWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), Collections.emptySet())
                .size();
    }
}
