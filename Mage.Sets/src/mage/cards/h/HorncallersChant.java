
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.RhinoToken;

/**
 *
 * @author LevleX2
 */
public final class HorncallersChant extends CardImpl {

    public HorncallersChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{G}");

        // Create a 4/4 green Rhino creature token with trample, then populate.
        // (Create a token that's a copy of a creature token you control.)
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RhinoToken()));
        this.getSpellAbility().addEffect(new PopulateEffect("then"));
    }

    private HorncallersChant(final HorncallersChant card) {
        super(card);
    }

    @Override
    public HorncallersChant copy() {
        return new HorncallersChant(this);
    }
}
