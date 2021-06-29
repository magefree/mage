package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SkeletonToken2 extends TokenImpl {

    public SkeletonToken2() {
        super("Skeleton", "1/1 black Skeleton creature token");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SkeletonToken2(final SkeletonToken2 token) {
        super(token);
    }

    public SkeletonToken2 copy() {
        return new SkeletonToken2(this);
    }
}
