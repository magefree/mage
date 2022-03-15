

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;

/**
 *
 * @author spjspj
 */
public final class GarrukApexPredatorBeastToken extends TokenImpl {

    public GarrukApexPredatorBeastToken() {
        super("Beast Token", "3/3 black Beast creature token with deathtouch");
        setOriginalExpansionSetCode("M15");
        setTokenType(1);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(3);

        abilities.add(DeathtouchAbility.getInstance());

    }

    public GarrukApexPredatorBeastToken(final GarrukApexPredatorBeastToken token) {
        super(token);
    }

    public GarrukApexPredatorBeastToken copy() {
        return new GarrukApexPredatorBeastToken(this);
    }
}
