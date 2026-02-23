package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public class Skeleton21Token extends TokenImpl {

    public Skeleton21Token() {
        super("Skeleton Token", "2/1 black Skeleton creature token");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(1);
    }

    private Skeleton21Token(final Skeleton21Token token) {
        super(token);
    }

    public Skeleton21Token copy() {
        return new Skeleton21Token(this);
    }
}
