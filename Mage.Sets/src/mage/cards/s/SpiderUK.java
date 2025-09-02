package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.WebSlingingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderUK extends CardImpl {

    public SpiderUK(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Web-slinging {2}{W}
        this.addAbility(new WebSlingingAbility(this, "{2}{W}"));

        // At the beginning of your end step, if two or more creatures entered the battlefield under your control this turn, you draw a card and gain 2 life.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(1, true)
        ).withInterveningIf(SpiderUKCondition.instance);
        ability.addEffect(new GainLifeEffect(2).setText("and gain 2 life"));
        this.addAbility(ability, new SpiderUKWatcher());
    }

    private SpiderUK(final SpiderUK card) {
        super(card);
    }

    @Override
    public SpiderUK copy() {
        return new SpiderUK(this);
    }
}

enum SpiderUKCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return SpiderUKWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "two or more creatures entered the battlefield under your control this turn";
    }
}

class SpiderUKWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    SpiderUKWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Optional.of(event)
                .map(EntersTheBattlefieldEvent.class::cast)
                .map(EntersTheBattlefieldEvent::getTarget)
                .map(Controllable::getControllerId)
                .ifPresent(uuid -> map.compute(uuid, CardUtil::setOrIncrementValue));
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpiderUKWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0) >= 2;
    }
}
