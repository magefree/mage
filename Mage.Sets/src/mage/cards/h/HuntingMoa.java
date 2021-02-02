
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class HuntingMoa extends CardImpl {

    public HuntingMoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(new EchoAbility("{2}{G}"));
        // When Hunting Moa enters the battlefield or dies, put a +1/+1 counter on target creature.
        Ability enterAbility = new EntersBattlefieldOrDiesSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        enterAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(enterAbility);
    }

    private HuntingMoa(final HuntingMoa card) {
        super(card);
    }

    @Override
    public HuntingMoa copy() {
        return new HuntingMoa(this);
    }
}
