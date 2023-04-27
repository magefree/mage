package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerrieThePulverizer extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Different kinds of counters among permanents you control", PerrieThePulverizerValue.instance
    );

    public PerrieThePulverizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Perrie enters the battlefield, put a shield counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.SHIELD.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever Perrie attacks, target creature you control gains trample and gets +X/+X, where X is the number of different kinds of counters among permanents you control.
        ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("target creature you control gains trample"));
        ability.addEffect(new BoostTargetEffect(
                PerrieThePulverizerValue.instance, PerrieThePulverizerValue.instance, Duration.EndOfTurn
        ).setText("and gets +X/+X until end of turn, where X is the number of different kinds of counters among permanents you control"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private PerrieThePulverizer(final PerrieThePulverizer card) {
        super(card);
    }

    @Override
    public PerrieThePulverizer copy() {
        return new PerrieThePulverizer(this);
    }
}

enum PerrieThePulverizerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .map(p -> p.getCounters(game))
                .map(HashMap::keySet)
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public PerrieThePulverizerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
