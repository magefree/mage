package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author spjspj
 */
public final class MaritLageToken extends TokenImpl {

    public MaritLageToken() {
        super("Marit Lage", "Marit Lage, a legendary 20/20 black Avatar creature token with flying and indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.AVATAR);
        this.supertype.add(SuperType.LEGENDARY);

        color.setBlack(true);
        power = new MageInt(20);
        toughness = new MageInt(20);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(IndestructibleAbility.getInstance());
    }

    public MaritLageToken(final MaritLageToken token) {
        super(token);
    }

    public MaritLageToken copy() {
        return new MaritLageToken(this);
    }
}
