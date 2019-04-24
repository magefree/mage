
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
 * @author spjspj
 */
public final class UtvaraHellkiteDragonToken extends TokenImpl {
    
    final static private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("C17"));
    }

    public UtvaraHellkiteDragonToken() {
        super("Dragon", "6/6 red Dragon creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setExpansionSetCodeForImage("C17");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
    }
    public UtvaraHellkiteDragonToken(final UtvaraHellkiteDragonToken token) {
        super(token);
    }

    public UtvaraHellkiteDragonToken copy() {
        return new UtvaraHellkiteDragonToken(this);
    }
    
}
