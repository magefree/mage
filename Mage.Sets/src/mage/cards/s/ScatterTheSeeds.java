
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author jonubuu
 */
public final class ScatterTheSeeds extends CardImpl {

    public ScatterTheSeeds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}{G}");


        // Convoke
        this.addAbility(new ConvokeAbility());
        // Create three 1/1 green Saproling creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), 3));
    }

    private ScatterTheSeeds(final ScatterTheSeeds card) {
        super(card);
    }

    @Override
    public ScatterTheSeeds copy() {
        return new ScatterTheSeeds(this);
    }
}
