

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class RiseOfEaglesBirdToken extends TokenImpl {

    public RiseOfEaglesBirdToken() {
        super("Bird Token", "2/2 blue Bird enchantment creature tokens with flying");
        this.setOriginalExpansionSetCode("BNG");
        this.setTokenType(2);
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setColor(ObjectColor.BLUE);
        subtype.add(SubType.BIRD);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
    }

    public RiseOfEaglesBirdToken(final RiseOfEaglesBirdToken token) {
        super(token);
    }

    public RiseOfEaglesBirdToken copy() {
        return new RiseOfEaglesBirdToken(this);
    }
}
