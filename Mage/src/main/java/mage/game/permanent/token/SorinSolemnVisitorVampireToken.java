

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class SorinSolemnVisitorVampireToken extends TokenImpl {

    public SorinSolemnVisitorVampireToken() {
        super("Vampire Token", "2/2 black Vampire creature token with flying");
        setOriginalExpansionSetCode("KTK");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
    }

    public SorinSolemnVisitorVampireToken(final SorinSolemnVisitorVampireToken token) {
        super(token);
    }

    public SorinSolemnVisitorVampireToken copy() {
        return new SorinSolemnVisitorVampireToken(this);
    }
}
