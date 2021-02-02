
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BirdToken;

/**
 *
 * @author LevleX2
 */
public final class EyesInTheSkies extends CardImpl {

    public EyesInTheSkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // Create a 1/1 white Bird creature token with flying, then populate.
        // (Create a token that's a copy of a creature token you control.)
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BirdToken()));
        this.getSpellAbility().addEffect(new PopulateEffect("then"));
    }

    private EyesInTheSkies(final EyesInTheSkies card) {
        super(card);
    }

    @Override
    public EyesInTheSkies copy() {
        return new EyesInTheSkies(this);
    }
}

