package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaesterSeymour extends CardImpl {

    public MaesterSeymour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, put a number of +1/+1 counters equal to Maester Seymour's power on another target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(), SourcePermanentPowerValue.NOT_NEGATIVE
        ).setText("put a number of +1/+1 counters equal to {this}'s power on another target creature you control"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // {3}{G}{G}: Monstrosity X, where X is the number of counters among creatures you control.
        this.addAbility(new MonstrosityAbility(
                "{3}{G}{G}", MaesterSeymourValue.instance, null, ""
        ).addHint(MaesterSeymourValue.getHint()));
    }

    private MaesterSeymour(final MaesterSeymour card) {
        super(card);
    }

    @Override
    public MaesterSeymour copy() {
        return new MaesterSeymour(this);
    }
}

enum MaesterSeymourValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint(
            "The number of counters among creatures you control", instance
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .map(permanent -> permanent.getCounters(game))
                .mapToInt(Counters::getTotalCount)
                .sum();
    }

    @Override
    public MaesterSeymourValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of counters among creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
