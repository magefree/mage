package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlowstoneRecluse extends CardImpl {

    public GlowstoneRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Mutate {3}{G}
        this.addAbility(new MutateAbility(this, "{3}{G}"));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever this creature mutates, put two +1/+1 counters on it.
        this.addAbility(new MutatesSourceTriggeredAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(2)
                ).setText("put two +1/+1 counters on it")
        ));
    }

    private GlowstoneRecluse(final GlowstoneRecluse card) {
        super(card);
    }

    @Override
    public GlowstoneRecluse copy() {
        return new GlowstoneRecluse(this);
    }
}
