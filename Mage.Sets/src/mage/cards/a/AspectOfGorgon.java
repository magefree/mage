
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class AspectOfGorgon extends CardImpl {

    public AspectOfGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+3 and has deathtouch.
        Effect effect = new BoostEnchantedEffect(1,3, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +1/+3");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("and has deathtouch");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private AspectOfGorgon(final AspectOfGorgon card) {
        super(card);
    }

    @Override
    public AspectOfGorgon copy() {
        return new AspectOfGorgon(this);
    }
}
