package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SourceDealsDamageToYouTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class RetaliatorGriffin extends CardImpl {

    public RetaliatorGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever a source an opponent controls deals damage to you, you may put that many +1/+1 counters on Retaliator Griffin.
        this.addAbility(new SourceDealsDamageToYouTriggeredAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), SavedDamageValue.MANY, true
        ), true));
    }

    private RetaliatorGriffin(final RetaliatorGriffin card) {
        super(card);
    }

    @Override
    public RetaliatorGriffin copy() {
        return new RetaliatorGriffin(this);
    }
}
