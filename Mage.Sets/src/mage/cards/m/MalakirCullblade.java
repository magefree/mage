
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public final class MalakirCullblade extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature an opponent controls");
    
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public MalakirCullblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a creature an opponent controls dies, put a +1/+1 counter on Malakir Cullblade.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter));
    }

    public MalakirCullblade(final MalakirCullblade card) {
        super(card);
    }

    @Override
    public MalakirCullblade copy() {
        return new MalakirCullblade(this);
    }
}
