package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeedshipAgrarian extends CardImpl {

    public SeedshipAgrarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever this creature becomes tapped, create a Lander token.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new CreateTokenEffect(new LanderToken())));

        // Landfall -- Whenever a land you control enters, put a +1/+1 counter on this creature.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private SeedshipAgrarian(final SeedshipAgrarian card) {
        super(card);
    }

    @Override
    public SeedshipAgrarian copy() {
        return new SeedshipAgrarian(this);
    }
}
