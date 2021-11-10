
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;

/**
 *
 * @author LevelX2
 */
public final class HuntedCentaurToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("RTR", "MM3"));
    }

    public HuntedCentaurToken() {
        super("Centaur Token", "3/3 green Centaur creature tokens with protection from black");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CENTAUR);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
    }

    public HuntedCentaurToken(final HuntedCentaurToken token) {
        super(token);
    }

    public HuntedCentaurToken copy() {
        return new HuntedCentaurToken(this);
    }
}
