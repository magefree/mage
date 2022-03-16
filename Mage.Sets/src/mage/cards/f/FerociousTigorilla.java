package mage.cards.f;

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
public final class FerociousTigorilla extends CardImpl {

    public FerociousTigorilla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.APE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Ferocious Tigorilla enters the battlefield with your choice of a trample counter or a menace counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCounterChoiceSourceEffect(CounterType.TRAMPLE, CounterType.MENACE),
                "with your choice of a trample counter or a menace counter on it. " +
                        "<i>(A creature with menace can't be blocked except by two or more creatures.)</i>"
        ));
    }

    private FerociousTigorilla(final FerociousTigorilla card) {
        super(card);
    }

    @Override
    public FerociousTigorilla copy() {
        return new FerociousTigorilla(this);
    }
}
