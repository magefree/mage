package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BoldBiochemist extends CardImpl {

    public BoldBiochemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Power-up -- {5}{U}:Put a +1/+1 counter on this creature and draw two cards.
        Ability ability = new PowerUpAbility(new AddCountersSourceEffect(
            CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{5}{U}")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private BoldBiochemist(final BoldBiochemist card) {
        super(card);
    }

    @Override
    public BoldBiochemist copy() {
        return new BoldBiochemist(this);
    }
}
