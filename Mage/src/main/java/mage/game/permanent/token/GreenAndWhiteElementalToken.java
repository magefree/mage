package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author JayDi85
 */
public final class GreenAndWhiteElementalToken extends TokenImpl {

    public GreenAndWhiteElementalToken() {
        super("Elemental Token", "8/8 green and white Elemental creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        this.subtype.add(SubType.ELEMENTAL);
        power = new MageInt(8);
        toughness = new MageInt(8);
        this.addAbility(VigilanceAbility.getInstance());

        availableImageSetCodes = Arrays.asList("GK1", "PTC", "RTR");
    }

    public GreenAndWhiteElementalToken(final GreenAndWhiteElementalToken token) {
        super(token);
    }

    public GreenAndWhiteElementalToken copy() {
        return new GreenAndWhiteElementalToken(this);
    }
}
