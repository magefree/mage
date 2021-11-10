

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;

/**
 *
 * @author spjspj
 */
public final class ATATToken extends TokenImpl {

    public ATATToken() {
        super("AT-AT Token", "5/5 white artifact AT-AT creature tokens with \"When this creature dies, create two 1/1 white Trooper creature tokens.\"", 5, 5);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        color.setWhite(true);
        addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TrooperToken(), 2)));
        subtype.add(SubType.ATAT);
    }

    public ATATToken(final ATATToken token) {
        super(token);
    }

    public ATATToken copy() {
        return new ATATToken(this);
    }
}

