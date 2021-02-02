
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class BlackWard extends CardImpl {

    private static final FilterCard filter = new FilterCard("black");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public BlackWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        // Enchanted creature has protection from black. This effect doesn't remove Black Ward.
        ProtectionAbility gainedAbility = new ProtectionAbility(filter);
        gainedAbility.setAuraIdNotToBeRemoved(this.getId());
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("Enchanted creature has protection from black. This effect doesn't remove {this}.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BlackWard(final BlackWard card) {
        super(card);
    }

    @Override
    public BlackWard copy() {
        return new BlackWard(this);
    }
}
