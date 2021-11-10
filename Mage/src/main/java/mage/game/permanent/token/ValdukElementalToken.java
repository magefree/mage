
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;

/**
 *
 * @author spjspj
 */
public final class ValdukElementalToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("DOM"));
    }

    public ValdukElementalToken() {
        this("DOM");
    }

    public ValdukElementalToken(String setCode) {
        super("Elemental Token", "3/1 red Elemental creature token with trample and haste");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.setOriginalExpansionSetCode("DOM");
        this.setTokenType(1);
    }

    public ValdukElementalToken(final ValdukElementalToken token) {
        super(token);
    }

    public ValdukElementalToken copy() {
        return new ValdukElementalToken(this);
    }
}
