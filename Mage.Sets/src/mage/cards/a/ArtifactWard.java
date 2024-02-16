
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public final class ArtifactWard extends CardImpl {

    public ArtifactWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature can't be blocked by artifact creatures.
        // Prevent all damage that would be dealt to enchanted creature by artifact sources.
        // Enchanted creature can't be the target of abilities from artifact sources.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(new ProtectionAbility(new FilterArtifactCard("artifacts")), AttachmentType.AURA)));
    }

    private ArtifactWard(final ArtifactWard card) {
        super(card);
    }

    @Override
    public ArtifactWard copy() {
        return new ArtifactWard(this);
    }
}
