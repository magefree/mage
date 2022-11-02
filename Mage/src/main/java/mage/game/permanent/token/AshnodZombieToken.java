package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class AshnodZombieToken extends TokenImpl {

    public AshnodZombieToken() {
        super("Zombie Token", "3/3 colorless Zombie artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(3);
        toughness = new MageInt(3);

        setOriginalExpansionSetCode("BRO");
    }

    public AshnodZombieToken(final AshnodZombieToken token) {
        super(token);
    }

    public AshnodZombieToken copy() {
        return new AshnodZombieToken(this);
    }
}
