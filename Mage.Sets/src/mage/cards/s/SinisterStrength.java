
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.SetCardColorAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
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
public final class SinisterStrength extends CardImpl {

    public SinisterStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature gets +3/+1 and is black.
        Effect effect = new BoostEnchantedEffect(3, 1);
        effect.setText("Enchanted creature gets +3/+1");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new SetCardColorAttachedEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText("and is black");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private SinisterStrength(final SinisterStrength card) {
        super(card);
    }

    @Override
    public SinisterStrength copy() {
        return new SinisterStrength(this);
    }
}
