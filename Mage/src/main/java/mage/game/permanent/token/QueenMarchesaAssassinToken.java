package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class QueenMarchesaAssassinToken extends TokenImpl {

    public QueenMarchesaAssassinToken() {
        super("Assassin Token", "1/1 black Assassin creature tokens with deathtouch and haste");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ASSASSIN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(DeathtouchAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    public QueenMarchesaAssassinToken(final QueenMarchesaAssassinToken token) {
        super(token);
    }

    public QueenMarchesaAssassinToken copy() {
        return new QueenMarchesaAssassinToken(this);
    }
}
