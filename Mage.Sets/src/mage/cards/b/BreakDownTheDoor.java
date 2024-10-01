package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreakDownTheDoor extends CardImpl {

    public BreakDownTheDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose one --
        // * Exile target artifact.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Exile target enchantment.
        this.getSpellAbility().addMode(new Mode(new ExileTargetEffect()).addTarget(new TargetEnchantmentPermanent()));

        // * Manifest dread.
        this.getSpellAbility().addMode(new Mode(new ManifestDreadEffect()));
    }

    private BreakDownTheDoor(final BreakDownTheDoor card) {
        super(card);
    }

    @Override
    public BreakDownTheDoor copy() {
        return new BreakDownTheDoor(this);
    }
}
// Heeeeeere's Johnny!
