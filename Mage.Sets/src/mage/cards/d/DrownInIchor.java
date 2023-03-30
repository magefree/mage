package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class DrownInIchor extends CardImpl {

    public DrownInIchor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target creature gets -4/-4 until end of turn. Proliferate.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, -4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private DrownInIchor(final DrownInIchor card) {
        super(card);
    }

    @Override
    public DrownInIchor copy() {
        return new DrownInIchor(this);
    }
}
