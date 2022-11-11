
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
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
 * @author LevelX2
 */
public final class CastIntoDarkness extends CardImpl {

    public CastIntoDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets -2/-0 and can't block.
        Effect effect = new BoostEnchantedEffect(-2,0, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets -2/-0");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new CantBlockAttachedEffect(AttachmentType.AURA);
        effect.setText("and can't block");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CastIntoDarkness(final CastIntoDarkness card) {
        super(card);
    }

    @Override
    public CastIntoDarkness copy() {
        return new CastIntoDarkness(this);
    }
}
