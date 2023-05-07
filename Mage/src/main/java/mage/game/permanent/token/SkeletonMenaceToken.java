package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SkeletonMenaceToken extends TokenImpl {

    public SkeletonMenaceToken() {
        super("Skeleton Token", "4/1 black Skeleton creature token with menace");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(4);
        toughness = new MageInt(1);

        addAbility(new MenaceAbility());
    }

    public SkeletonMenaceToken(final SkeletonMenaceToken token) {
        super(token);
    }

    public SkeletonMenaceToken copy() {
        return new SkeletonMenaceToken(this);
    }
}
