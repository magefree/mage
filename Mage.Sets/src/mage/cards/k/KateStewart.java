package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KateStewart extends CardImpl {

    public KateStewart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you put one or more time counters on a permanent you control, create a 1/1 white Soldier creature token.
        this.addAbility(new KateStewartTriggeredAbility());

        // Whenever Kate Stewart attacks, you may pay {8}. If you do, attacking creatures get +X/+X until end of turn, where X is the number of time counters among permanents you control.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new BoostAllEffect(
                KateStewartValue.instance, KateStewartValue.instance, Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES, false
        ), new GenericManaCost(8))).addHint(KateStewartValue.getHint()));
    }

    private KateStewart(final KateStewart card) {
        super(card);
    }

    @Override
    public KateStewart copy() {
        return new KateStewart(this);
    }
}

class KateStewartTriggeredAbility extends TriggeredAbilityImpl {

    KateStewartTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SoldierToken()));
        setTriggerPhrase("Whenever you put one or more time counters on a permanent you control, ");
    }

    private KateStewartTriggeredAbility(final KateStewartTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KateStewartTriggeredAbility copy() {
        return new KateStewartTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return CounterType.TIME.getName().equals(event.getData())
                && this.isControlledBy(event.getPlayerId())
                && this.isControlledBy(game.getControllerId(event.getTargetId()));
    }
}

enum KateStewartValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Time counters among permanents you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .map(permanent -> permanent.getCounters(game))
                .mapToInt(c -> c.getCount(CounterType.TIME))
                .sum();
    }

    @Override
    public KateStewartValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of time counters among permanents you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
