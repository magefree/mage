package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class HerculesPrinceOfPower extends CardImpl {

    public HerculesPrinceOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Power-up -- {4}{G}: Put a +1/+1 counter on Hercules. He gains vigilance, indestructible, and haste until end of turn.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on {this}"),
            new ManaCostsImpl<>("{4}{G}")
        );
        ability.addEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn)
            .setText("He gains vigilance"));
        ability.addEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
            .setText(", indestructible"));
        ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
            .setText(", and haste until end of turn"));
        this.addAbility(ability);
    }

    private HerculesPrinceOfPower(final HerculesPrinceOfPower card) {
        super(card);
    }

    @Override
    public HerculesPrinceOfPower copy() {
        return new HerculesPrinceOfPower(this);
    }
}
