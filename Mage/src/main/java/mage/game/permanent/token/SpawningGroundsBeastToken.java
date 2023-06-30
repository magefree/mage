package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class SpawningGroundsBeastToken extends TokenImpl {

    public SpawningGroundsBeastToken() {
        super("Beast Token", "5/5 green Beast creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(5);
        toughness = new MageInt(5);

        this.addAbility(TrampleAbility.getInstance());
    }

    public SpawningGroundsBeastToken(final SpawningGroundsBeastToken token) {
        super(token);
    }

    public SpawningGroundsBeastToken copy() {
        return new SpawningGroundsBeastToken(this);
    }
}
