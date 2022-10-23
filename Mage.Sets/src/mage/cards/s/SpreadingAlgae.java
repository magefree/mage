
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Plopman
 */
public final class SpreadingAlgae extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Swamp");

    static{
        filter.add(SubType.SWAMP.getPredicate());
    }

    public SpreadingAlgae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");
        this.subtype.add(SubType.AURA);

        // Enchant Swamp
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // When enchanted land becomes tapped, destroy it.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new DestroyAttachedToEffect("it"), "enchanted land"));

        // When Spreading Algae is put into a graveyard from the battlefield, return Spreading Algae to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));

    }

    private SpreadingAlgae(final SpreadingAlgae card) {
        super(card);
    }

    @Override
    public SpreadingAlgae copy() {
        return new SpreadingAlgae(this);
    }
}
