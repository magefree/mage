package mage.cards.o;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Overkill extends CardImpl {

    public Overkill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target creature gets -0/-9999 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(0, -9999));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Overkill(final Overkill card) {
        super(card);
    }

    @Override
    public Overkill copy() {
        return new Overkill(this);
    }
}
