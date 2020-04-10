package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdaptiveShimmerer extends CardImpl {

    public AdaptiveShimmerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Adaptive Shimmerer enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(3), true
        ), "with three +1/+1 counters on it"));
    }

    private AdaptiveShimmerer(final AdaptiveShimmerer card) {
        super(card);
    }

    @Override
    public AdaptiveShimmerer copy() {
        return new AdaptiveShimmerer(this);
    }
}
