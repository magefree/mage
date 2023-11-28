package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class SkeletonPirateToken extends TokenImpl {

    public SkeletonPirateToken() {
        super("Skeleton Pirate Token", "2/2 black Skeleton Pirate creature token");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.PIRATE);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    protected SkeletonPirateToken(final SkeletonPirateToken token) {
        super(token);
    }

    public SkeletonPirateToken copy() {
        return new SkeletonPirateToken(this);
    }
}
