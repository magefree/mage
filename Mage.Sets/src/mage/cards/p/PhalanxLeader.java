
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PhalanxLeader extends CardImpl {

    public PhalanxLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Heroic</i> Whenever you cast a spell that targets Phalanx Leader, put a +1/+1 counter on each creature you control.
        this.addAbility(new HeroicAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent())));
    }

    private PhalanxLeader(final PhalanxLeader card) {
        super(card);
    }

    @Override
    public PhalanxLeader copy() {
        return new PhalanxLeader(this);
    }
}
