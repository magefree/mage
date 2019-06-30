package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HexproofAbility;
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
public final class BarkhideTroll extends CardImpl {

    public BarkhideTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Barkhide Troll enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                "with a +1/+1 counter on it"
        ));

        // {1}, Remove a +1/+1 counter from Barkhide Troll: Barkhide Troll gains hexproof until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private BarkhideTroll(final BarkhideTroll card) {
        super(card);
    }

    @Override
    public BarkhideTroll copy() {
        return new BarkhideTroll(this);
    }
}
