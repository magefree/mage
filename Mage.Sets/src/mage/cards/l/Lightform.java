
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesAuraAttachToManifestSourceEffect;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class Lightform extends CardImpl {

    public Lightform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // When Lightform enters the battlefield, it becomes an Aura with enchant creature. Manifest the top card of your library and attach Lightform to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesAuraAttachToManifestSourceEffect()));

        // Enchanted creature has flying and lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Lightform(final Lightform card) {
        super(card);
    }

    @Override
    public Lightform copy() {
        return new Lightform(this);
    }
}


