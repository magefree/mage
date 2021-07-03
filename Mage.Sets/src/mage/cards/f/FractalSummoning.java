package mage.cards.f;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FractalSummoning extends CardImpl {

    public FractalSummoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G/U}{G/U}");

        this.subtype.add(SubType.LESSON);

        // Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it.
        this.getSpellAbility().addEffect(FractalToken.getEffect(
                ManacostVariableValue.REGULAR, "Put X +1/+1 counters on it"
        ));
    }

    private FractalSummoning(final FractalSummoning card) {
        super(card);
    }

    @Override
    public FractalSummoning copy() {
        return new FractalSummoning(this);
    }
}
