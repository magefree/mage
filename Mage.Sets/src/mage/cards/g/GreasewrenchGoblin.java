package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreasewrenchGoblin extends CardImpl {

    public GreasewrenchGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Exhaust -- {2}{R}: Discard up to two cards, then draw that many cards. Put a +1/+1 counter on this creature.
        Ability ability = new ExhaustAbility(new DiscardAndDrawThatManyEffect(2), new ManaCostsImpl<>("{2}{R}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private GreasewrenchGoblin(final GreasewrenchGoblin card) {
        super(card);
    }

    @Override
    public GreasewrenchGoblin copy() {
        return new GreasewrenchGoblin(this);
    }
}
