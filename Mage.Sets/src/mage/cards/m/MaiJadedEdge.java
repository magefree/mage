package mage.cards.m;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.ProwessAbility;
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
public final class MaiJadedEdge extends CardImpl {

    public MaiJadedEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Exhaust -- {3}: Put a double strike counter on Mai.
        this.addAbility(new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.DOUBLE_STRIKE.createInstance()), new GenericManaCost(3)
        ));
    }

    private MaiJadedEdge(final MaiJadedEdge card) {
        super(card);
    }

    @Override
    public MaiJadedEdge copy() {
        return new MaiJadedEdge(this);
    }
}
