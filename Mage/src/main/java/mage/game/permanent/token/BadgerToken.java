package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;

public final class BadgerToken extends TokenImpl {

    public BadgerToken() {
        super("Badger Token", "3/3 green Badger creature token");
        cardType.add(CardType.CREATURE);
        color.addColor(ObjectColor.GREEN);
        subtype.add(SubType.BADGER);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public BadgerToken(final BadgerToken token) {
        super(token);
    }

    public BadgerToken copy() {
        return new BadgerToken(this);
    }
}
