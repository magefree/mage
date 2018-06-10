

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
 * @author BetaSteward_at_googlemail.com
 */
public final class DragonToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("DTK", "MMA", "ALA", "MM3", "C17"));
    }

    public DragonToken() {
        this(null, 0);
    }

    public DragonToken(String setCode) {
        this(setCode, 0);
    }

    public DragonToken(String setCode, int tokenType) {
        super("Dragon", "4/4 red Dragon creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public DragonToken(final DragonToken token) {
        super(token);
    }

    public DragonToken copy() {
        return new DragonToken(this);
    }
}
