package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.ElephantToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StampedingScurryfoot extends CardImpl {

    public StampedingScurryfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.MOUSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Exhaust -- {3}{G}: Put a +1/+1 counter on this creature. Create a 3/3 green Elephant creature token.
        Ability ability = new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{3}{G}")
        );
        ability.addEffect(new CreateTokenEffect(new ElephantToken()));
        this.addAbility(ability);
    }

    private StampedingScurryfoot(final StampedingScurryfoot card) {
        super(card);
    }

    @Override
    public StampedingScurryfoot copy() {
        return new StampedingScurryfoot(this);
    }
}
