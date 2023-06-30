package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class WaspToken extends TokenImpl {

    public WaspToken() {
        super("Wasp", "1/1 colorless Insect artifact creature token with flying named Wasp");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    public WaspToken(final WaspToken token) {
        super(token);
    }

    public WaspToken copy() {
        return new WaspToken(this);
    }
}
