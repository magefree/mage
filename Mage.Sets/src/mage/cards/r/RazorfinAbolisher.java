
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class RazorfinAbolisher extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a counter on it");
    
    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public RazorfinAbolisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{U}, {tap}: Return target creature with a counter on it to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
        
    }

    private RazorfinAbolisher(final RazorfinAbolisher card) {
        super(card);
    }

    @Override
    public RazorfinAbolisher copy() {
        return new RazorfinAbolisher(this);
    }
}
