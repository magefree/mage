
package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EpicEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SnakeToken;

/**
 *
 * @author jeffwadsworth

 */
public final class EndlessSwarm extends CardImpl {

    public EndlessSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{G}{G}{G}");


        // Create a 1/1 green Snake creature token for each card in your hand.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SnakeToken(), CardsInControllerHandCount.instance).setText("create a 1/1 green Snake creature token for each card in your hand"));

        // Epic
        this.getSpellAbility().addEffect(new EpicEffect());
        
    }

    private EndlessSwarm(final EndlessSwarm card) {
        super(card);
    }

    @Override
    public EndlessSwarm copy() {
        return new EndlessSwarm(this);
    }
}
