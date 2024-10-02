package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.ValiantTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeedglaiveMentor extends CardImpl {

    public SeedglaiveMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Valiant -- Whenever Seedglaive Mentor becomes the target of a spell or ability you control for the first time each turn, put a +1/+1 counter on it.
        this.addAbility(new ValiantTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it")));
    }

    private SeedglaiveMentor(final SeedglaiveMentor card) {
        super(card);
    }

    @Override
    public SeedglaiveMentor copy() {
        return new SeedglaiveMentor(this);
    }
}
