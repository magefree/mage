

package mage.game.permanent.token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.abilities.keyword.ForestwalkAbility;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class CatWarriorToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("PLC", "C17"));
    }

    public CatWarriorToken() {
        super("Cat Warrior", "2/2 green Cat Warrior creature token with forestwalk");
        availableImageSetCodes = tokenImageSets;
        this.setOriginalExpansionSetCode("PLC");
        this.getPower().modifyBaseValue(2);
        this.getToughness().modifyBaseValue(2);
        this.color.setGreen(true);
        this.getSubtype(null).add(SubType.CAT);
        this.getSubtype(null).add(SubType.WARRIOR);
        this.addCardType(CardType.CREATURE);
        this.addAbility(new ForestwalkAbility());
    }

    public CatWarriorToken(final CatWarriorToken token) {
        super(token);
    }

    public CatWarriorToken copy() {
        return new CatWarriorToken(this);
    }
}
