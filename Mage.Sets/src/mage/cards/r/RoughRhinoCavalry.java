package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoughRhinoCavalry extends CardImpl {

    public RoughRhinoCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Firebending 2
        this.addAbility(new FirebendingAbility(2));

        // Exhaust -- {8}: Put two +1/+1 counters on this creature. It gains trample until end of turn.
        Ability ability = new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new GenericManaCost(8)
        );
        ability.addEffect(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains trample until end of turn"));
        this.addAbility(ability);
    }

    private RoughRhinoCavalry(final RoughRhinoCavalry card) {
        super(card);
    }

    @Override
    public RoughRhinoCavalry copy() {
        return new RoughRhinoCavalry(this);
    }
}
