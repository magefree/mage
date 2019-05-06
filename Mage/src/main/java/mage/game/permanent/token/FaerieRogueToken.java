

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author LoneFox
 */
public final class FaerieRogueToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("SHM", "MOR", "MMA", "MM2"));
    }

    public FaerieRogueToken() {
        super("Faerie Rogue", "1/1 black Faerie Rogue creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.FAERIE);
        subtype.add(SubType.ROGUE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = tokenImageSets;
    }

    public FaerieRogueToken(final FaerieRogueToken token) {
        super(token);
    }

    public FaerieRogueToken copy() {
        return new FaerieRogueToken(this);
    }
}
