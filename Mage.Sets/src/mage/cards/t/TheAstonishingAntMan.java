package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.InsectToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheAstonishingAntMan extends CardImpl {

    public TheAstonishingAntMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you draw a card, put a +1/+1 counter on The Astonishing Ant-Man.
        this.addAbility(new DrawCardControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));

        // {2}{G}, {T}, Remove any number of +1/+1 counters from The Astonishing Ant-Man: Create that many 1/1 green Insect creature tokens.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new InsectToken(), GetXValue.instance)
            .setText("create that many 1/1 green Insect creature tokens"), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(
            CounterType.P1P1,
            "Remove any number of +1/+1 counters from {this}"
        ));
        this.addAbility(ability);
    }

    private TheAstonishingAntMan(final TheAstonishingAntMan card) {
        super(card);
    }

    @Override
    public TheAstonishingAntMan copy() {
        return new TheAstonishingAntMan(this);
    }
}
