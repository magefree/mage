package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public class SkeletonToken2 extends TokenImpl {

    public SkeletonToken2() {
        super("Skeleton Token", "2/1 black Skeleton creature token");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(1);
    }

    private SkeletonToken2(final SkeletonToken2 token) {
        super(token);
    }

    public SkeletonToken2 copy() {
        return new SkeletonToken2(this);
    }
}
