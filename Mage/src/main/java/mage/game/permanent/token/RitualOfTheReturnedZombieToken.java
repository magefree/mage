

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class RitualOfTheReturnedZombieToken extends TokenImpl {

    public RitualOfTheReturnedZombieToken() {
        this(1,1);
    }
    public RitualOfTheReturnedZombieToken(int power, int toughness) {
        super("Zombie Token", "black Zombie creature token with power equal to the exiled card's power and toughness equal to the exiled card's toughness");
        this.setOriginalExpansionSetCode("JOU");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);
    }

    public RitualOfTheReturnedZombieToken(final RitualOfTheReturnedZombieToken token) {
        super(token);
    }

    public RitualOfTheReturnedZombieToken copy() {
        return new RitualOfTheReturnedZombieToken(this);
    }
}
