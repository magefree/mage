
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author fireshoes
 */
public final class SproutSwarm extends CardImpl {

    public SproutSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        // Create a 1/1 green Saproling creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken()));
    }

    private SproutSwarm(final SproutSwarm card) {
        super(card);
    }

    @Override
    public SproutSwarm copy() {
        return new SproutSwarm(this);
    }
}
