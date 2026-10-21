package mage.cards.b;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class BeastEruditeAerialist extends CardImpl {

    public BeastEruditeAerialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as you've put one or more +1/+1 counters on Beast this turn, he has flying.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
            BeastEruditeAerialistCondition.instance, "as long as you've put " +
            "one or more +1/+1 counters on {this} this turn, he has flying"
        ));
        this.addAbility(ability.addHint(BeastEruditeAerialistCondition.getHint()), new BeastEruditeAerialistWatcher());

        // Whenever Beast deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));
    }

    private BeastEruditeAerialist(final BeastEruditeAerialist card) {
        super(card);
    }

    @Override
    public BeastEruditeAerialist copy() {
        return new BeastEruditeAerialist(this);
    }
}

enum BeastEruditeAerialistCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
        instance, "You've put one or more +1/+1 counters on {this} this turn"
    );

    @Override
    public boolean apply(Game game, Ability source) {
        return BeastEruditeAerialistWatcher.checkPermanent(source, game);
    }

    public static Hint getHint() {
        return hint;
    }
}

class BeastEruditeAerialistWatcher extends Watcher {

    // player -> permanents that player put +1/+1 counters on this turn
    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();

    BeastEruditeAerialistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER_ADDED
                || !event.getData().equals(CounterType.P1P1.getName())) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            map.computeIfAbsent(event.getPlayerId(), x -> new HashSet<>())
                .add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPermanent(Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && game.getState()
            .getWatcher(BeastEruditeAerialistWatcher.class)
            .map
            .getOrDefault(source.getControllerId(), Collections.emptySet())
            .contains(new MageObjectReference(permanent, game));
    }
}
