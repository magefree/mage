
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Quercitron
 */
public final class Hecatomb extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("an untapped Swamp you control");
    
    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    private static final FilterControlledPermanent filter2
            = new FilterControlledCreaturePermanent("creatures");
    
    public Hecatomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");

        // When Hecatomb enters the battlefield, sacrifice Hecatomb unless you sacrifice four creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(
                new SacrificeTargetCost(new TargetControlledPermanent(4, filter2))))
                .setReplaceRuleText(false));
        
        // Tap an untapped Swamp you control: Hecatomb deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Hecatomb(final Hecatomb card) {
        super(card);
    }

    @Override
    public Hecatomb copy() {
        return new Hecatomb(this);
    }
}
