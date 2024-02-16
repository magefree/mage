package mage.cards.r;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RefuseToYield extends CardImpl {

    public RefuseToYield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+7 until end of turn. Untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 7));
        this.getSpellAbility().addEffect(new UntapTargetEffect("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RefuseToYield(final RefuseToYield card) {
        super(card);
    }

    @Override
    public RefuseToYield copy() {
        return new RefuseToYield(this);
    }
}
