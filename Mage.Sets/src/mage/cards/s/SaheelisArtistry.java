package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SaheelisArtistry extends CardImpl {

    public SaheelisArtistry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // • Create a token that's a copy of target artifact.
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setText("Create a token that's a copy of target artifact");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetArtifactPermanent().withChooseHint("create copy of that"));
        // • Create a token that's a copy of target creature, except that it's an artifact in addition to its other types.
        Mode mode1 = new Mode();
        effect = new CreateTokenCopyTargetEffect();
        effect.setBecomesArtifact(true);
        effect.setText("Create a token that's a copy of target creature, except it's an artifact in addition to its other types");
        mode1.addEffect(effect);
        mode1.addTarget(new TargetCreaturePermanent().withChooseHint("create copy of that, artifact type"));
        this.getSpellAbility().addMode(mode1);
    }

    private SaheelisArtistry(final SaheelisArtistry card) {
        super(card);
    }

    @Override
    public SaheelisArtistry copy() {
        return new SaheelisArtistry(this);
    }
}
