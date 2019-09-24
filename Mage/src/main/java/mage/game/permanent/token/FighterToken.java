package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author jmharmon
 */

public final class FighterToken extends TokenImpl {

    public FighterToken(){
        super("Fighter", "3/3 red Fighter creature token with first strike");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.FIGHTER);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(FirstStrikeAbility.getInstance());
    }

    public FighterToken(final FighterToken token) {
        super(token);
    }

    public FighterToken copy() {
        return new FighterToken(this);
    }
}
