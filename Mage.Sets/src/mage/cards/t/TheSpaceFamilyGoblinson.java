package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
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
public final class TheSpaceFamilyGoblinson extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Dice you've rolled this turn", TheSpaceFamilyGoblinsonValue.instance
    );

    public TheSpaceFamilyGoblinson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.GUEST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // The Space Family Goblinson has trample as long as you've rolled three or more dice this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn),
                TheSpaceFamilyGoblinsonCondition.instance, "{this} has trample " +
                "as long as you've rolled three or more dice this turn"
        )).addHint(hint), new TheSpaceFamilyGoblinsonWatcher());

        // Whenever you roll a die, put a +1/+1 counter on The Space Family Goblinson.
        this.addAbility(new TheSpaceFamilyGoblinsonTriggeredAbility());
    }

    private TheSpaceFamilyGoblinson(final TheSpaceFamilyGoblinson card) {
        super(card);
    }

    @Override
    public TheSpaceFamilyGoblinson copy() {
        return new TheSpaceFamilyGoblinson(this);
    }
}

enum TheSpaceFamilyGoblinsonCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return TheSpaceFamilyGoblinsonWatcher.getCount(source.getControllerId(), game) >= 3;
    }
}

enum TheSpaceFamilyGoblinsonValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return TheSpaceFamilyGoblinsonWatcher.getCount(sourceAbility.getControllerId(), game);
    }

    @Override
    public TheSpaceFamilyGoblinsonValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class TheSpaceFamilyGoblinsonWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    TheSpaceFamilyGoblinsonWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DIE_ROLLED) {
            map.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(TheSpaceFamilyGoblinsonWatcher.class)
                .map
                .getOrDefault(playerId, 0);
    }
}

class TheSpaceFamilyGoblinsonTriggeredAbility extends TriggeredAbilityImpl {

    TheSpaceFamilyGoblinsonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    private TheSpaceFamilyGoblinsonTriggeredAbility(final TheSpaceFamilyGoblinsonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheSpaceFamilyGoblinsonTriggeredAbility copy() {
        return new TheSpaceFamilyGoblinsonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }

    @Override
    public String getRule() {
        return "Whenever you roll a die, put a +1/+1 counter on {this}";
    }
}
