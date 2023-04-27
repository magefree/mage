

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class CorpseweftZombieToken extends TokenImpl {

    public CorpseweftZombieToken() {
        this(2,2);
    }

    public CorpseweftZombieToken(int power, int toughness) {
        super("Zombie Horror Token", "X/X black Zombie Horror creature token, where X is twice the number of cards exiled this way");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.HORROR);
        color.setBlack(true);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);
        setOriginalExpansionSetCode("DTK");
    }

    public CorpseweftZombieToken(final CorpseweftZombieToken token) {
        super(token);
    }

    public CorpseweftZombieToken copy() {
        return new CorpseweftZombieToken(this);
    }
}
