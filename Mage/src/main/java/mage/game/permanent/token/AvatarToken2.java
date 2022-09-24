package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class AvatarToken2 extends TokenImpl {

    public AvatarToken2() {
        super("Avatar Token", "4/4 white Avatar creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.AVATAR);
        power = new MageInt(4);
        toughness = new MageInt(4);
        setOriginalExpansionSetCode("M19");
        addAbility(FlyingAbility.getInstance());
    }

    public AvatarToken2(final AvatarToken2 token) {
        super(token);
    }

    public AvatarToken2 copy() {
        return new AvatarToken2(this);
    }
}
