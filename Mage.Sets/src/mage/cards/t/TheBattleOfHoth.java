
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ATATToken;

/**
 *
 * @author Styxo
 */
public final class TheBattleOfHoth extends CardImpl {

    public TheBattleOfHoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{W}{W}{W}");

        // Create X 5/5 white artifact AT-AT creature tokens wiht "When this creature dies, create two 1/1 white Trooper creature tokens."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ATATToken(), ManacostVariableValue.REGULAR));
    }

    private TheBattleOfHoth(final TheBattleOfHoth card) {
        super(card);
    }

    @Override
    public TheBattleOfHoth copy() {
        return new TheBattleOfHoth(this);
    }
}
