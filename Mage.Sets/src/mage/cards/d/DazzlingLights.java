package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DazzlingLights extends CardImpl {

    public DazzlingLights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Target creature gets -3/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Surveil 2.
        this.getSpellAbility().addEffect(new SurveilEffect(2).concatBy("<br>"));
    }

    private DazzlingLights(final DazzlingLights card) {
        super(card);
    }

    @Override
    public DazzlingLights copy() {
        return new DazzlingLights(this);
    }
}
