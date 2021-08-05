package mage.cards.a;

import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneSubtraction extends CardImpl {

    public ArcaneSubtraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target creature gets -4/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, 0));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private ArcaneSubtraction(final ArcaneSubtraction card) {
        super(card);
    }

    @Override
    public ArcaneSubtraction copy() {
        return new ArcaneSubtraction(this);
    }
}
