

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class ReignOfThePitToken extends TokenImpl {

    public ReignOfThePitToken() {
        this(1);
    }
    public ReignOfThePitToken(int xValue) {
        super("Demon", "X/X black Demon creature token with flying");
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        cardType.add(CardType.CREATURE);
        this.addAbility(FlyingAbility.getInstance());
    }
    public ReignOfThePitToken(final ReignOfThePitToken token) {
        super(token);
    }

    public ReignOfThePitToken copy() {
        return new ReignOfThePitToken(this);
    }
    
}
