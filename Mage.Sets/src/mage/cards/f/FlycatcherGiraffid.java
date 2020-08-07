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
public final class FlycatcherGiraffid extends CardImpl {

    public FlycatcherGiraffid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ANTELOPE);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flycatcher Giraffid enters the battlefield with your choice of a vigilance counter or a reach counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCounterChoiceSourceEffect(CounterType.VIGILANCE, CounterType.REACH)
        ));
    }

    private FlycatcherGiraffid(final FlycatcherGiraffid card) {
        super(card);
    }

    @Override
    public FlycatcherGiraffid copy() {
        return new FlycatcherGiraffid(this);
    }
}
