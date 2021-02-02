
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TIEFighterToken;

/**
 *
 * @author Styxo
 */
public final class SwarmTheSkies extends CardImpl {

    public SwarmTheSkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Create three 1/1 black Starship artifact creature tokens with Spaceflight named TIE Fighter.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TIEFighterToken(), 3));
    }

    private SwarmTheSkies(final SwarmTheSkies card) {
        super(card);
    }

    @Override
    public SwarmTheSkies copy() {
        return new SwarmTheSkies(this);
    }
}
