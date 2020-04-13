package mage.cards.w;

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
public final class WingfoldPteron extends CardImpl {

    public WingfoldPteron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Wingfold Pteron enters the battlefield with your choice of a flying counter or a hexproof counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCounterChoiceSourceEffect(CounterType.FLYING, CounterType.HEXPROOF)
        ));
    }

    private WingfoldPteron(final WingfoldPteron card) {
        super(card);
    }

    @Override
    public WingfoldPteron copy() {
        return new WingfoldPteron(this);
    }
}
