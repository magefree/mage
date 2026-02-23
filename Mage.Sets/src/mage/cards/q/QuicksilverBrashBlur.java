package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LeylineAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuicksilverBrashBlur extends CardImpl {

    public QuicksilverBrashBlur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If Quicksilver, Brash Blur is in your opening hand, you may begin the game with him on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Power-up -- {4}{R}: Put a +1/+1 counter and a double strike counter on Quicksilver.
        Ability ability = new PowerUpAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter"),
                new ManaCostsImpl<>("{4}{R}")
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.DOUBLE_STRIKE.createInstance())
                .setText("and a double strike counter on {this}"));
        this.addAbility(ability);
    }

    private QuicksilverBrashBlur(final QuicksilverBrashBlur card) {
        super(card);
    }

    @Override
    public QuicksilverBrashBlur copy() {
        return new QuicksilverBrashBlur(this);
    }
}
