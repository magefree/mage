

package mage.game.permanent.token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;

/**
 *
 * @author spjspj
 */
public final class TitaniaProtectorOfArgothElementalToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C14", "CMA"));
    }

    public TitaniaProtectorOfArgothElementalToken() {
        this((String)null);
    }

    public TitaniaProtectorOfArgothElementalToken(String setCode) {
        super("Elemental", "5/3 green Elemental creature token");
        availableImageSetCodes = tokenImageSets;
        this.setOriginalExpansionSetCode(setCode);
        this.cardType.add(CardType.CREATURE);
        this.color = ObjectColor.GREEN;
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
    }

    public TitaniaProtectorOfArgothElementalToken(final TitaniaProtectorOfArgothElementalToken token) {
        super(token);
    }

    public TitaniaProtectorOfArgothElementalToken copy() {
        return new TitaniaProtectorOfArgothElementalToken(this);
    }
}
