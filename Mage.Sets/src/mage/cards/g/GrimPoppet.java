
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GrimPoppet extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
    
    static {
        filter.add(AnotherPredicate.instance);
    }
    
    public GrimPoppet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Grim Poppet enters the battlefield with three -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(3)), "with three -1/-1 counters on it"));

        // Remove a -1/-1 counter from Grim Poppet: Put a -1/-1 counter on another target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.M1M1.createInstance()), new RemoveCountersSourceCost(CounterType.M1M1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        
    }
    
    private GrimPoppet(final GrimPoppet card) {
        super(card);
    }
    
    @Override
    public GrimPoppet copy() {
        return new GrimPoppet(this);
    }
}
