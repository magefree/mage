
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author cbt33
 */
public final class MagmaVein extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature without flying");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("a land");
    
    static {
        filter1.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(CardType.LAND.getPredicate());
    }
    
    public MagmaVein(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");


        // {R}, Sacrifice a land: Magma Vein deals 1 damage to each creature without flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(1, filter1), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
        
    }

    private MagmaVein(final MagmaVein card) {
        super(card);
    }

    @Override
    public MagmaVein copy() {
        return new MagmaVein(this);
    }
}
