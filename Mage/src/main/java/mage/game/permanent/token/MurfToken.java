package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author muz
 */
public final class MurfToken extends TokenImpl {

    public MurfToken() {
        super("Murf", "Murf, a legendary 0/1 green Ooze Slug creature token with indestructible");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        subtype.add(SubType.SLUG);
        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(1);
        this.addAbility(IndestructibleAbility.getInstance());
    }

    private MurfToken(final MurfToken token) {
        super(token);
    }

    @Override
    public MurfToken copy() {
        return new MurfToken(this);
    }
}
