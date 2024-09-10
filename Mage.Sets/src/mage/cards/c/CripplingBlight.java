
package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class CripplingBlight extends CardImpl {

    public CripplingBlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets -1/-1 and can't block.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(-1, -1));
        ability.addEffect(new CantBlockAttachedEffect(AttachmentType.AURA).setText("and can't block"));
        this.addAbility(ability);
    }

    private CripplingBlight(final CripplingBlight card) {
        super(card);
    }

    @Override
    public CripplingBlight copy() {
        return new CripplingBlight(this);
    }
}
