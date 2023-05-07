package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SkeletonToken extends TokenImpl {

    public SkeletonToken() {
        super("Skeleton Token", "1/1 black Skeleton creature token");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SkeletonToken(final SkeletonToken token) {
        super(token);
    }

    public SkeletonToken copy() {
        return new SkeletonToken(this);
    }
}
