package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author North
 */
public final class DrakeToken extends TokenImpl {

    public DrakeToken() {
        super("Drake Token", "2/2 blue Drake creature token with flying");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.DRAKE);

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AKH", "C15", "C19", "M13", "C20", "ZNR", "C21", "UMA", "NCC");
    }

    public DrakeToken(final DrakeToken token) {
        super(token);
    }

    public DrakeToken copy() {
        return new DrakeToken(this);
    }
}
