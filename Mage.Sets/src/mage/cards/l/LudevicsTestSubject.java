package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LudevicsTestSubject extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.HATCHLING, 5);

    public LudevicsTestSubject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.LIZARD, SubType.EGG);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.l.LudevicsAbomination.class;

        this.addAbility(DefenderAbility.getInstance());

        // {1}{U}: Put a hatchling counter on Ludevic's Test Subject. Then if there are five or more hatchling counters on it, remove all of them and transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.HATCHLING.createInstance()), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.HATCHLING), condition,
                "Then if there are five or more hatchling counters on it, remove all of them and transform it"
        ).addEffect(new TransformSourceEffect()));
        this.addAbility(ability);
    }

    private LudevicsTestSubject(final LudevicsTestSubject card) {
        super(card);
    }

    @Override
    public LudevicsTestSubject copy() {
        return new LudevicsTestSubject(this);
    }
}
