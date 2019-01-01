
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SaheelisArtistry extends CardImpl {

    public SaheelisArtistry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}{U}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // • Create a token that's a copy of target artifact.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setText("Create a token that's a copy of target artifact");
        this.getSpellAbility().addEffect(effect);
        // • Create a token that's a copy of target creature, except that it's an artifact in addition to its other types.
        Mode mode1 = new Mode();
        mode1.addTarget(new TargetCreaturePermanent());
        effect = new CreateTokenCopyTargetEffect();
        effect.setBecomesArtifact(true);
        effect.setText("Create a token that's a copy of target creature, except that it's an artifact in addition to its other types");
        mode1.addEffect(effect);
        this.getSpellAbility().addMode(mode1);
    }

    public SaheelisArtistry(final SaheelisArtistry card) {
        super(card);
    }

    @Override
    public SaheelisArtistry copy() {
        return new SaheelisArtistry(this);
    }
}
