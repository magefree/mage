package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureConvokingSource;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VeneratedLoxodon extends CardImpl {

    public VeneratedLoxodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Venerated Loxodon enters the battlefield, put a +1/+1 counter on each creature that convoked it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterCreatureConvokingSource()), false
        ));
    }

    private VeneratedLoxodon(final VeneratedLoxodon card) {
        super(card);
    }

    @Override
    public VeneratedLoxodon copy() {
        return new VeneratedLoxodon(this);
    }
}
