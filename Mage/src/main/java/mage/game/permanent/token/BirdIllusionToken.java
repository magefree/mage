

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

import java.util.Arrays;

/**
 *
 * @author TheElk801
 */
public final class BirdIllusionToken extends TokenImpl {

    public BirdIllusionToken() {
        super("Bird Illusion Token", "1/1 blue Bird Illusion creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("GRN", "C20", "GN3");
    }

    public BirdIllusionToken(final BirdIllusionToken token) {
        super(token);
    }

    public BirdIllusionToken copy() {
        return new BirdIllusionToken(this);
    }
}
