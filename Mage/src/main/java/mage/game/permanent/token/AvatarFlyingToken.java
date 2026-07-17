package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class AvatarFlyingToken extends TokenImpl {

    public AvatarFlyingToken() {
        super("Avatar Token", "4/4 white Avatar creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.AVATAR);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    protected AvatarFlyingToken(final AvatarFlyingToken token) {
        super(token);
    }

    public AvatarFlyingToken copy() {
        return new AvatarFlyingToken(this);
    }
}
