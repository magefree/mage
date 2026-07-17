package mage.cards.f;

import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FractalAnomaly extends CardImpl {

    public FractalAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Create a 0/0 green and blue Fractal creature token and put X +1/+1 counters on it, where X is the number of cards you've drawn this turn.
        this.getSpellAbility().addEffect(FractalToken.getEffect(
                CardsDrawnThisTurnDynamicValue.instance, " and put X +1/+1 counters on it, " +
                        "where X is the number of cards you've drawn this turn"
        ));
        this.getSpellAbility().addHint(CardsDrawnThisTurnDynamicValue.getHint());
    }

    private FractalAnomaly(final FractalAnomaly card) {
        super(card);
    }

    @Override
    public FractalAnomaly copy() {
        return new FractalAnomaly(this);
    }
}
