
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetCreaturePermanent;

public final class ProtectServe extends SplitCard {

    public ProtectServe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}", "{1}{U}", SpellAbilityType.SPLIT_FUSED);

        // Protect
        // Target creature gets +2/+4 until end of turn.
        getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 4, Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("gets +2/+4"));

        // Serve
        // Target creature gets -6/-0 until end of turn.
        getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(-6, 0, Duration.EndOfTurn));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("gets -6/-0"));
    }

    private ProtectServe(final ProtectServe card) {
        super(card);
    }

    @Override
    public ProtectServe copy() {
        return new ProtectServe(this);
    }
}
