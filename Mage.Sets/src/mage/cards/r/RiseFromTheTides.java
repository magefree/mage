
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author fireshoes
 */
public final class RiseFromTheTides extends CardImpl {

    public RiseFromTheTides(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");

        // Create a tapped 2/2 black Zombie creature token for each instant and sorcery card in your graveyard.
        CardsInControllerGraveyardCount value = new CardsInControllerGraveyardCount(new FilterInstantOrSorceryCard());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken(), value, true, false));
    }

    public RiseFromTheTides(final RiseFromTheTides card) {
        super(card);
    }

    @Override
    public RiseFromTheTides copy() {
        return new RiseFromTheTides(this);
    }
}
