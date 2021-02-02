
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CentaurToken;

/**
 *
 * @author LevleX2
 */
public final class CoursersAccord extends CardImpl {

    public CoursersAccord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}{W}");


        // Create a 3/3 green Centaur creature token, then populate.
        // (Create a token that's a copy of a creature token you control.)
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CentaurToken()));
        this.getSpellAbility().addEffect(new PopulateEffect("then"));
    }

    private CoursersAccord(final CoursersAccord card) {
        super(card);
    }

    @Override
    public CoursersAccord copy() {
        return new CoursersAccord(this);
    }
}
