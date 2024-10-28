package mage.cards.w;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class WarpArtifact extends CardImpl {

    public WarpArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of the upkeep of enchanted artifact's controller, Warp Artifact deals 1 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DamageTargetEffect(1).withTargetDescription("that player"),
                TargetController.CONTROLLER_ATTACHED_TO, false, true
        ).setTriggerPhrase("At the beginning of the upkeep of enchanted artifact's controller, "));
    }

    private WarpArtifact(final WarpArtifact card) {
        super(card);
    }

    @Override
    public WarpArtifact copy() {
        return new WarpArtifact(this);
    }
}
