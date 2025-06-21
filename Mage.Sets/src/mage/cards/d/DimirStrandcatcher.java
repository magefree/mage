package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DimirStrandcatcher extends CardImpl {

    public DimirStrandcatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}{U/B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you attack, surveil X, where X is the number of opponents being attacked.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DimirStrandcatcherEffect(), 1));

        // At the beginning of each end step, if three or more cards were put into your graveyard from anywhere other than the battlefield this turn, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.EACH_PLAYER, new DrawCardSourceControllerEffect(1),
                false, DimirStrandcatcherCondition.instance
        ).addHint(DimirStrandcatcherValue.getHint()), new DimirStrandcatcherWatcher());
    }

    private DimirStrandcatcher(final DimirStrandcatcher card) {
        super(card);
    }

    @Override
    public DimirStrandcatcher copy() {
        return new DimirStrandcatcher(this);
    }
}

class DimirStrandcatcherEffect extends OneShotEffect {

    DimirStrandcatcherEffect() {
        super(Outcome.Benefit);
        staticText = "surveil X, where X is the number of opponents being attacked";
    }

    private DimirStrandcatcherEffect(final DimirStrandcatcherEffect effect) {
        super(effect);
    }

    @Override
    public DimirStrandcatcherEffect copy() {
        return new DimirStrandcatcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = game
                .getOpponents(source.getControllerId())
                .stream()
                .filter(game.getCombat().getDefenders()::contains)
                .mapToInt(x -> 1)
                .sum();
        return player.surveil(count, source, game);
    }
}

enum DimirStrandcatcherCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DimirStrandcatcherValue.instance.calculate(game, source, null) >= 3;
    }

    @Override
    public String toString() {
        return "three or more cards were put into your graveyard from anywhere other than the battlefield this turn";
    }
}

enum DimirStrandcatcherValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint(
            "Cards put into your graveyard from anywhere other than the battlefield this turn", instance
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DimirStrandcatcherWatcher.getValue(game, sourceAbility);
    }

    @Override
    public DimirStrandcatcherValue copy() {
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

class DimirStrandcatcherWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    DimirStrandcatcherWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (Zone.GRAVEYARD.match(zEvent.getToZone()) && !Zone.BATTLEFIELD.match(zEvent.getFromZone())) {
            map.compute(game.getOwnerId(zEvent.getTargetId()), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getValue(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(DimirStrandcatcherWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0);
    }
}
