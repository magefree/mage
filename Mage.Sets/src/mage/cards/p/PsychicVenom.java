
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author KholdFuzion

 */
public final class PsychicVenom extends CardImpl {

    public PsychicVenom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Whenever enchanted land becomes tapped, Psychic Venom deals 2 damage to that land's controller.
        Effect effect = new DamageAttachedControllerEffect(2);
        effect.setText("{this} deals 2 damage to that land's controller");
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(effect, "enchanted land"));
    }

    private PsychicVenom(final PsychicVenom card) {
        super(card);
    }

    @Override
    public PsychicVenom copy() {
        return new PsychicVenom(this);
    }
}
