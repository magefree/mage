

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class SeedGuardianToken extends TokenImpl {

    public SeedGuardianToken() {
        this(1);
    }
    public SeedGuardianToken(int xValue) {
        super("Elemental", "X/X green Elemental creature token");
        setTokenType(2);
        setOriginalExpansionSetCode("OGW");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

    }

    public SeedGuardianToken(final SeedGuardianToken token) {
        super(token);
    }

    public SeedGuardianToken copy() {
        return new SeedGuardianToken(this);
    }
}
