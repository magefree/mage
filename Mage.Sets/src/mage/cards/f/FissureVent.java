package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetNonBasicLandPermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class FissureVent extends CardImpl {

    public FissureVent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Choose one or both - Destroy target artifact; and/or destroy target nonbasic land.
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent().withChooseHint("destroy"));

        Mode mode1 = new Mode(new DestroyTargetEffect());
        mode1.addTarget(new TargetNonBasicLandPermanent().withChooseHint("destroy"));
        this.getSpellAbility().addMode(mode1);
    }

    private FissureVent(final FissureVent card) {
        super(card);
    }

    @Override
    public FissureVent copy() {
        return new FissureVent(this);
    }

}
