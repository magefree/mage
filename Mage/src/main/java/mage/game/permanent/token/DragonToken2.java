
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class DragonToken2 extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("WWK", "10E", "BFZ", "C15", "CN2", "CMA"));
    }

    public DragonToken2() {
        this((String)null);
    }

    public DragonToken2(String setCode) {
        super("Dragon", "5/5 red Dragon creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }

    public DragonToken2(final DragonToken2 token) {
        super(token);
    }

    public DragonToken2 copy() {
        return new DragonToken2(this);
    }
}
