
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author jonubuu
 */
public final class GoblinOffensive extends CardImpl {

    public GoblinOffensive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{1}{R}{R}");

        // create X 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), ManacostVariableValue.REGULAR));
    }

    private GoblinOffensive(final GoblinOffensive card) {
        super(card);
    }

    @Override
    public GoblinOffensive copy() {
        return new GoblinOffensive(this);
    }
}
