
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author North
 */
public final class DrakeToken extends TokenImpl {

    public DrakeToken() {
        super("Drake", "2/2 blue Drake creature token with flying");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.DRAKE);

        this.color = ObjectColor.BLUE;
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
    }

    public DrakeToken(final DrakeToken token) {
        super(token);
    }

    public DrakeToken copy() {
        return new DrakeToken(this);
    }
}
