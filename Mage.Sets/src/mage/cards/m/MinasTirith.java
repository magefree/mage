package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinasTirith extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);
    private static final Hint hint = new ConditionHint(condition, "You control a legendary creature");

    public MinasTirith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // Minas Tirith enters the battlefield tapped unless you control a legendary creature.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new TapSourceEffect(), condition, ""),
                "tapped unless you control a legendary creature"
        ).addHint(hint));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {1}{W}, {T}: Draw a card. Activate only if you attacked with two or more creatures this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("{1}{W}"), MinasTirithCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(MinasTirithCondition.getHint()), new MinasTirithWatcher());
    }

    private MinasTirith(final MinasTirith card) {
        super(card);
    }

    @Override
    public MinasTirith copy() {
        return new MinasTirith(this);
    }
}

enum MinasTirithCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, CardUtil.getTextWithFirstCharUpperCase(instance.toString())
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return MinasTirithWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you attacked with two or more creatures this turn";
    }
}

class MinasTirithWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    MinasTirithWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            map.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(MinasTirithWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0) >= 2;
    }
}
