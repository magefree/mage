package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class UndercitySkeletonToken extends TokenImpl {

    public UndercitySkeletonToken() {
        super("Skeleton Token", "4/1 black Skeleton creature token with menace");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(4);
        toughness = new MageInt(1);

        addAbility(new MenaceAbility());

        availableImageSetCodes = Arrays.asList("CLB");
    }

    public UndercitySkeletonToken(final UndercitySkeletonToken token) {
        super(token);
    }

    public UndercitySkeletonToken copy() {
        return new UndercitySkeletonToken(this);
    }
}
