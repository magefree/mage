package mage.cards.l;

import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineInvocation extends CardImpl {

    public LeylineInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it, where X is the number of lands you control.
        this.getSpellAbility().addEffect(FractalToken.getEffect(
                LandsYouControlCount.instance, "Put X +1/+1 counters on it, " +
                        "where X is the number of lands you control"
        ));
        this.getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private LeylineInvocation(final LeylineInvocation card) {
        super(card);
    }

    @Override
    public LeylineInvocation copy() {
        return new LeylineInvocation(this);
    }
}
