
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class GodtrackerOfJund extends CardImpl {
    
    final private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }
    
    private static final String rule = "Whenever a creature with power 5 or greater enters the battlefield under your control, you may put a +1/+1 counter on {this}.";

    public GodtrackerOfJund(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);



        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature with power 5 or greater enters the battlefield under your control, you may put a +1/+1 counter on Godtracker of Jund.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, true, rule, true));
    }

    private GodtrackerOfJund(final GodtrackerOfJund card) {
        super(card);
    }

    @Override
    public GodtrackerOfJund copy() {
        return new GodtrackerOfJund(this);
    }
}
