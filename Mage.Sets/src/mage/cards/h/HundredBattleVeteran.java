package mage.cards.h;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 *
 * @author Jmlundeen
 */
public final class HundredBattleVeteran extends CardImpl {

    private static final Hint hint = new ValueHint("different kinds of counters among creatures you control", CounterTypeCount.instance);

    public HundredBattleVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // As long as there are three or more different kinds of counters among creatures you control, this creature gets +2/+4.
        Effect effect = new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 4, Duration.WhileOnBattlefield),
                HundredBattleVeteranCondition.instance,
                "As long as there are three or more different kinds of counters among creatures you control, this creature gets +2/+4"
        );
        this.addAbility(new SimpleStaticAbility(effect).addHint(hint));

        // You may cast this card from your graveyard. If you do, it enters with a finality counter on it.
        AbilityImpl ability = new MayCastFromGraveyardSourceAbility();
        ability.appendToRule(" If you do, it enters with a finality counter on it.");
        Effect effect1 = new AddCountersSourceEffect(CounterType.FINALITY.createInstance(), StaticValue.get(1));
        ability.addSubAbility(new EntersBattlefieldAbility(effect1, CastFromGraveyardSourceCondition.instance, "", "")
                .setRuleVisible(false));
        this.addAbility(ability);

    }

    private HundredBattleVeteran(final HundredBattleVeteran card) {
        super(card);
    }

    @Override
    public HundredBattleVeteran copy() {
        return new HundredBattleVeteran(this);
    }
}

enum CounterTypeCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, sourceAbility.getControllerId(), game)
                .stream()
                .filter(permanent -> !permanent.getCounters(game).isEmpty())
                .mapToInt(permanent -> permanent.getCounters(game).size())
                .sum();
    }

    @Override
    public CounterTypeCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "different kinds of counters among creatures you control";
    }
}

enum HundredBattleVeteranCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        int sum = CounterTypeCount.instance.calculate(game, source, null);
        return sum >= 3;
    }
}


