
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TrooperToken;

/**
 *
 * @author Styxo
 */
public final class DeployTheTroops extends CardImpl {

    public DeployTheTroops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{W}");

        // Create 3 1/1 white Trooper creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TrooperToken(), 3));

    }

    private DeployTheTroops(final DeployTheTroops card) {
        super(card);
    }

    @Override
    public DeployTheTroops copy() {
        return new DeployTheTroops(this);
    }
}
