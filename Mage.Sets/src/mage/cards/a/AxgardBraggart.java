package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AxgardBraggart extends CardImpl {

    public AxgardBraggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Boast — {1}{W}: Untap Axgard Braggart. Put a +1/+1 counter on it.
        Ability ability = new BoastAbility(new UntapSourceEffect(), "{1}{W}");
        ability.addEffect(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on it"));
        this.addAbility(ability);
    }

    private AxgardBraggart(final AxgardBraggart card) {
        super(card);
    }

    @Override
    public AxgardBraggart copy() {
        return new AxgardBraggart(this);
    }
}
