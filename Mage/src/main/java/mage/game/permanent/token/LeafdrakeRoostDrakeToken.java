

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
public final class LeafdrakeRoostDrakeToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C13", "CMA"));
    }

    public LeafdrakeRoostDrakeToken() {
        this(null, 0);
    }

    public LeafdrakeRoostDrakeToken(String setCode) {
        this(setCode, 0);
    }

    public LeafdrakeRoostDrakeToken(String setCode, int tokenType) {
        super("Drake Token", "2/2 green and blue Drake creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setBlue(true);
        subtype.add(SubType.DRAKE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }
    
    public LeafdrakeRoostDrakeToken(final LeafdrakeRoostDrakeToken token) {
        super(token);
    }

    public LeafdrakeRoostDrakeToken copy() {
        return new LeafdrakeRoostDrakeToken(this);
    }
}
