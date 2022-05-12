package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class BiogenicOozeToken extends TokenImpl {

    public BiogenicOozeToken() {
        super("Ooze Token", "2/2 green Ooze creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        setOriginalExpansionSetCode("RNA");
    }

    public BiogenicOozeToken(final BiogenicOozeToken token) {
        super(token);
    }

    public BiogenicOozeToken copy() {
        return new BiogenicOozeToken(this);
    }
}
