package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JayDi85
 */
public final class GreenAndWhiteElementalToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("GK1", "PTC", "RTR"));
    }

    public GreenAndWhiteElementalToken() {
        super("Elemental", "8/8 green and white Elemental creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        this.subtype.add(SubType.ELEMENTAL);
        power = new MageInt(8);
        toughness = new MageInt(8);
        this.addAbility(VigilanceAbility.getInstance());
    }

    public GreenAndWhiteElementalToken(final GreenAndWhiteElementalToken token) {
        super(token);
    }

    public GreenAndWhiteElementalToken copy() {
        return new GreenAndWhiteElementalToken(this);
    }
}
