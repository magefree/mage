

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class AnotherSpiritToken extends TokenImpl {

    public AnotherSpiritToken() {
        super("Spirit", "3/3 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
    }
    public AnotherSpiritToken(final AnotherSpiritToken token) {
        super(token);
    }

    public AnotherSpiritToken copy() {
        return new AnotherSpiritToken(this);
    }
}
