package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AegisOfTheHeavens extends CardImpl {

    public AegisOfTheHeavens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +1/+7 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 7, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AegisOfTheHeavens(final AegisOfTheHeavens card) {
        super(card);
    }

    @Override
    public AegisOfTheHeavens copy() {
        return new AegisOfTheHeavens(this);
    }
}
