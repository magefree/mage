
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Quercitron
 */
public final class Hecatomb extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("an untapped Swamp you control");
    
    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
        filter.add(Predicates.not(new TappedPredicate()));
    }
    
    public Hecatomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");


        // When Hecatomb enters the battlefield, sacrifice Hecatomb unless you sacrifice four creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledCreaturePermanent(4))), false));
        
        // Tap an untapped Swamp you control: Hecatomb deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public Hecatomb(final Hecatomb card) {
        super(card);
    }

    @Override
    public Hecatomb copy() {
        return new Hecatomb(this);
    }
}
