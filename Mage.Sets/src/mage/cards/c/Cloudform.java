
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesAuraAttachToManifestSourceEffect;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class Cloudform extends CardImpl {

    public Cloudform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");

        // When Cloudform enters the battlefield, it becomes an Aura with enchant creature. 
        // Manifest the top card of your library and attach Cloudform to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesAuraAttachToManifestSourceEffect()));
        
        // Enchanted creature has flying and hexproof.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new GainAbilityAttachedEffect(FlyingAbility.getInstance(), 
                        AttachmentType.AURA, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(
                HexproofAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("and hexproof");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Cloudform(final Cloudform card) {
        super(card);
    }

    @Override
    public Cloudform copy() {
        return new Cloudform(this);
    }
}
