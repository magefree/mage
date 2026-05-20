package mage.cards.w;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildHypothesis extends CardImpl {

    public WildHypothesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it.
        this.getSpellAbility().addEffect(FractalToken.getEffect(GetXValue.instance, ". Put X +1/+1 counters on it"));

        // Surveil 2.
        this.getSpellAbility().addEffect(new SurveilEffect(2).concatBy("<br>"));
    }

    private WildHypothesis(final WildHypothesis card) {
        super(card);
    }

    @Override
    public WildHypothesis copy() {
        return new WildHypothesis(this);
    }
}
