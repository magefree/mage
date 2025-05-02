package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ATATToken extends TokenImpl {

    public ATATToken() {
        super("AT-AT Token", "5/5 white artifact AT-AT creature tokens with \"When this creature dies, create two 1/1 white Trooper creature tokens.\"");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TrooperToken(), 2)));
        subtype.add(SubType.ATAT);
    }

    private ATATToken(final ATATToken token) {
        super(token);
    }

    public ATATToken copy() {
        return new ATATToken(this);
    }
}
