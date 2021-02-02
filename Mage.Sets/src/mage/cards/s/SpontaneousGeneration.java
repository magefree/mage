
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author fireshoes
 */
public final class SpontaneousGeneration extends CardImpl {

    public SpontaneousGeneration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Create a 1/1 green Saproling creature token for each card in your hand.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), CardsInControllerHandCount.instance));
    }

    private SpontaneousGeneration(final SpontaneousGeneration card) {
        super(card);
    }

    @Override
    public SpontaneousGeneration copy() {
        return new SpontaneousGeneration(this);
    }
}
