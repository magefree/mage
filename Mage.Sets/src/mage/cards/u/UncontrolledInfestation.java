
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class UncontrolledInfestation extends CardImpl {
    public UncontrolledInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant nonbasic land
        TargetPermanent auraTarget = new TargetNonBasicLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // When enchanted land becomes tapped, destroy it.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new DestroyAttachedToEffect("it"), "enchanted land"));
    }

    private UncontrolledInfestation(final UncontrolledInfestation card) {
        super(card);
    }

    @Override
    public UncontrolledInfestation copy() {
        return new UncontrolledInfestation(this);
    }
}
