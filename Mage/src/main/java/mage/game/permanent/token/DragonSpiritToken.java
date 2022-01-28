package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DragonSpiritToken extends TokenImpl {

    public DragonSpiritToken() {
        super("Dragon Spirit token", "5/5 red Dragon Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }

    public DragonSpiritToken(final DragonSpiritToken token) {
        super(token);
    }

    public DragonSpiritToken copy() {
        return new DragonSpiritToken(this);
    }
}
