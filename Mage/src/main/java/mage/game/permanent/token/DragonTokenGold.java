

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
 * @author Saga
 */
public final class DragonTokenGold extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("UST","H17"));
    }

    public DragonTokenGold() {
        this(null, 0);
    }

    public DragonTokenGold(String setCode) {
        this(setCode, 0);
    }

    public DragonTokenGold(String setCode, int tokenType) {
        super("Dragon Token", "4/4 gold Dragon creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGold(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public DragonTokenGold(final DragonTokenGold token) {
        super(token);
    }

    public DragonTokenGold copy() {
        return new DragonTokenGold(this);
    }
}