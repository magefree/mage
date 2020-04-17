package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCounterChoiceSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HelicaGlider extends CardImpl {

    public HelicaGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.SQUIRREL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Helica Glider enters the battlfield with your choice of a flying counter or a first strike counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCounterChoiceSourceEffect(CounterType.FLYING, CounterType.FIRST_STRIKE)
        ));
    }

    private HelicaGlider(final HelicaGlider card) {
        super(card);
    }

    @Override
    public HelicaGlider copy() {
        return new HelicaGlider(this);
    }
}
