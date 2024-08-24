package mage.cards.m;

import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakeshiftBattalion extends CardImpl {

    public MakeshiftBattalion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Makeshift Battalion and at least two other creatures attack, put a +1/+1 counter on Makeshift Battalion.
        this.addAbility(new BattalionAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private MakeshiftBattalion(final MakeshiftBattalion card) {
        super(card);
    }

    @Override
    public MakeshiftBattalion copy() {
        return new MakeshiftBattalion(this);
    }
}
