
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.util.functions.CardTypeCopyApplier;

/**
 *
 * @author KholdFuzion
 *
 */
public final class CopyArtifact extends CardImpl {

    public CopyArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // You may have Copy Artifact enter the battlefield as a copy of any artifact on the battlefield, except it's an enchantment in addition to its other types.
        Effect effect = new CopyPermanentEffect(new FilterArtifactPermanent(), new CardTypeCopyApplier(CardType.ENCHANTMENT));
        effect.setText("as a copy of any artifact on the battlefield, except it's an enchantment in addition to its other types");
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    private CopyArtifact(final CopyArtifact card) {
        super(card);
    }

    @Override
    public CopyArtifact copy() {
        return new CopyArtifact(this);
    }
}
