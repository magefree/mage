package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AppliedBiomancy extends CardImpl {

    public AppliedBiomancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("gets +1/+1 until end of turn"));

        // • Return target creature to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetCreaturePermanent().withChooseHint("return to its owner's hand"));
        this.getSpellAbility().addMode(mode);
    }

    private AppliedBiomancy(final AppliedBiomancy card) {
        super(card);
    }

    @Override
    public AppliedBiomancy copy() {
        return new AppliedBiomancy(this);
    }
}
