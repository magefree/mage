package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class HuntedDragonKnightToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ORI", "RTR", "C15"));
    }

    public HuntedDragonKnightToken() {
        super("Knight", "2/2 white Knight creature tokens with first strike");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);

        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    public HuntedDragonKnightToken(final HuntedDragonKnightToken token) {
        super(token);
    }

    public HuntedDragonKnightToken copy() {
        return new HuntedDragonKnightToken(this);
    }
}
