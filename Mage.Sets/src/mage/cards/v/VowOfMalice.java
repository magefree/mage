
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackControllerAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VowOfMalice extends CardImpl {

    public VowOfMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature gets +2/+2, has intimidate, and can't attack you or a planeswalker you control.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2,2,Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(IntimidateAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText(", has intimidate");
        ability.addEffect(effect);
        effect = new CantAttackControllerAttachedEffect(AttachmentType.AURA, true);
        effect.setText(", and can't attack you or planeswalkers you control");
        ability.addEffect(effect);
        this.addAbility(ability);           
    }

    private VowOfMalice(final VowOfMalice card) {
        super(card);
    }

    @Override
    public VowOfMalice copy() {
        return new VowOfMalice(this);
    }
}
