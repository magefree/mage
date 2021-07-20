
package mage.cards.p;

import java.util.UUID;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author fireshoes
 */
public final class PredatorsHowl extends CardImpl {

    public PredatorsHowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // Create a 2/2 green Wolf creature token.
        // <i>Morbid</i> &mdash; Create three 2/2 green Wolf creature tokens instead if a creature died this turn.
        Effect effect = new ConditionalOneShotEffect(
                new CreateTokenEffect(new WolfToken(), 3),
                new CreateTokenEffect(new WolfToken(), 1),
                MorbidCondition.instance,
                "Create a 2/2 green Wolf creature token. <br/><br/><i>Morbid</i> &mdash; Create three 2/2 green Wolf creature tokens instead if a creature died this turn.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(MorbidHint.instance);
    }

    private PredatorsHowl(final PredatorsHowl card) {
        super(card);
    }

    @Override
    public PredatorsHowl copy() {
        return new PredatorsHowl(this);
    }
}
