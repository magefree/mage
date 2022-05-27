package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Skeleton41Token extends TokenImpl {

    public Skeleton41Token() {
        super("Skeleton Token", "4/1 black Skeleton creature token with menace");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(4);
        toughness = new MageInt(1);

        addAbility(new MenaceAbility());

        availableImageSetCodes = Arrays.asList("CLB");
    }

    public Skeleton41Token(final Skeleton41Token token) {
        super(token);
    }

    public Skeleton41Token copy() {
        return new Skeleton41Token(this);
    }
}
