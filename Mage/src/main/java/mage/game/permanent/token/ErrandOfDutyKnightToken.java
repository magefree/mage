

package mage.game.permanent.token;
import mage.MageInt;
import mage.abilities.keyword.BandingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class ErrandOfDutyKnightToken extends TokenImpl {

    public ErrandOfDutyKnightToken() {
        super("Knight Token", "1/1 white Knight creature token with banding");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.KNIGHT);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(BandingAbility.getInstance());
    }

    public ErrandOfDutyKnightToken(final ErrandOfDutyKnightToken token) {
        super(token);
    }

    public ErrandOfDutyKnightToken copy() {
        return new ErrandOfDutyKnightToken(this);
    }
}
