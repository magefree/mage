package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class BludgeoningPain extends CardImpl {

    public BludgeoningPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");


        // Target creature gets -2/-2 until end of turn. Tap that creature.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BludgeoningPain(final BludgeoningPain card) {
        super(card);
    }

    @Override
    public BludgeoningPain copy() {
        return new BludgeoningPain(this);
    }
}
