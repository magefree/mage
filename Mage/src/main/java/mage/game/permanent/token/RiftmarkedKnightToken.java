
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlankingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class RiftmarkedKnightToken extends TokenImpl {

    public RiftmarkedKnightToken() {
        super("Knight Token", "2/2 black Knight creature token with flanking, protection from white, and haste");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new FlankingAbility());
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        this.addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("PLC", "TSR");
    }

    public RiftmarkedKnightToken(final RiftmarkedKnightToken token) {
        super(token);
    }

    public RiftmarkedKnightToken copy() {
        return new RiftmarkedKnightToken(this);
    }
}
