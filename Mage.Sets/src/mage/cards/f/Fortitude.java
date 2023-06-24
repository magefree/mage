
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.RegenerateAttachedEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author hanasu
 */
public final class Fortitude extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public Fortitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Sacrifice a Forest: Regenerate enchanted creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateAttachedEffect(AttachmentType.AURA), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
                
        // When Fortitude is put into a graveyard from the battlefield, return Fortitude to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    private Fortitude(final Fortitude card) {
        super(card);
    }

    @Override
    public Fortitude copy() {
        return new Fortitude(this);
    }
}
