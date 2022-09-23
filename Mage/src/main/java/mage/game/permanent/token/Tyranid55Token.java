package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Tyranid55Token extends TokenImpl {

    public Tyranid55Token() {
        super("Tyranid Token", "5/5 green Tyranid creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TYRANID);
        power = new MageInt(5);
        toughness = new MageInt(5);

        availableImageSetCodes.addAll(Arrays.asList("40K"));
    }

    public Tyranid55Token(final Tyranid55Token token) {
        super(token);
    }

    @Override
    public Tyranid55Token copy() {
        return new Tyranid55Token(this);
    }
}
