package mage.cards.v;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Vandalize extends CardImpl {

    public Vandalize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Choose one or both - Destroy target artifact; or Destroy target land.
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Destroy target artifact
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent().withChooseHint("destroy"));
        // Destroy target land
        Mode mode1 = new Mode(new DestroyTargetEffect());
        mode1.addTarget(new TargetLandPermanent().withChooseHint("destroy"));
        this.getSpellAbility().addMode(mode1);

    }

    private Vandalize(final Vandalize card) {
        super(card);
    }

    @Override
    public Vandalize copy() {
        return new Vandalize(this);
    }
}
