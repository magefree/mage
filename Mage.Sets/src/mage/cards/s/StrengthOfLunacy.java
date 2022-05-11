
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.abilities.keyword.ProtectionAbility;
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
 * @author LoneFox
 */
public final class StrengthOfLunacy extends CardImpl {

    public StrengthOfLunacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature gets +2/+1 and has protection from white.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 1, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(ProtectionAbility.from(ObjectColor.WHITE), AttachmentType.AURA);
        effect.setText("and has protection from white");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Madness {B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{B}")));
    }

    private StrengthOfLunacy(final StrengthOfLunacy card) {
        super(card);
    }

    @Override
    public StrengthOfLunacy copy() {
        return new StrengthOfLunacy(this);
    }
}
