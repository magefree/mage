package mage.cards.i;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Infuriate extends CardImpl {

    public Infuriate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +3/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Infuriate(final Infuriate card) {
        super(card);
    }

    @Override
    public Infuriate copy() {
        return new Infuriate(this);
    }
}
