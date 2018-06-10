

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class BeckonApparitionToken extends TokenImpl {

    public BeckonApparitionToken() {
        super("Spirit", "1/1 white and black Spirit creature token with flying");
        this.setOriginalExpansionSetCode("GTC");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
    public BeckonApparitionToken(final BeckonApparitionToken token) {
        super(token);
    }

    public BeckonApparitionToken copy() {
        return new BeckonApparitionToken(this);
    }
    
}
