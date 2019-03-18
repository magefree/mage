
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ArcticMerfolk extends CardImpl {

    public ArcticMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker—Return a creature you control to its owner's hand. (You may return a creature you control to its owner's hand in addition to any other costs as you cast this spell.)
        this.addAbility(new KickerAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledCreaturePermanent(1,1,new FilterControlledCreaturePermanent("a creature"),true))));

        // If Arctic Merfolk was kicked, it enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                KickedCondition.instance,"If Arctic Merfolk was kicked, it enters the battlefield with a +1/+1 counter on it.",""));
    }

    public ArcticMerfolk(final ArcticMerfolk card) {
        super(card);
    }

    @Override
    public ArcticMerfolk copy() {
        return new ArcticMerfolk(this);
    }
}
