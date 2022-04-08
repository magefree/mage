
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.common.UntapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public final class Crackleburr extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped red creatures you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("tapped blue creature you control");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(TappedPredicate.UNTAPPED);
        
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(TappedPredicate.TAPPED);
    }

    public Crackleburr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U/R}{U/R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {UR}{UR}, {tap}, Tap two untapped red creatures you control: Crackleburr deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new ManaCostsImpl("{U/R}{U/R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, true)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        
        // {UR}{UR}, {untap}, Untap two tapped blue creatures you control: Return target creature to its owner's hand.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl("{U/R}{U/R}"));
        ability2.addCost(new UntapSourceCost());
        ability2.addCost(new UntapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter2, true)));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
        
    }

    private Crackleburr(final Crackleburr card) {
        super(card);
    }

    @Override
    public Crackleburr copy() {
        return new Crackleburr(this);
    }
}
