package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class KorAllyToken extends TokenImpl {

    public KorAllyToken() {
        super("Kor Ally", "1/1 white Kor Ally creature token");
        this.setExpansionSetCodeForImage("BFZ");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.KOR);
        subtype.add(SubType.ALLY);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public KorAllyToken(final KorAllyToken token) {
        super(token);
    }

    public KorAllyToken copy() {
        return new KorAllyToken(this);
    }
}
