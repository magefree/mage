
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DjinnMonkToken;

/**
 *
 * @author fireshoes
 */
public final class OjutaisSummons extends CardImpl {

    public OjutaisSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Create a 2/2 blue Djinn Monk creature token with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DjinnMonkToken()));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private OjutaisSummons(final OjutaisSummons card) {
        super(card);
    }

    @Override
    public OjutaisSummons copy() {
        return new OjutaisSummons(this);
    }
}
