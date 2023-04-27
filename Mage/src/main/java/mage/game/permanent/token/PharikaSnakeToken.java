

package mage.game.permanent.token;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;

/**
 *
 * @author spjspj
 */
public final class PharikaSnakeToken extends TokenImpl {

    public PharikaSnakeToken() {
        super("Snake Token", "1/1 black and green Snake enchantment creature token with deathtouch");
        this.setOriginalExpansionSetCode("JOU");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SNAKE);
        color.setBlack(true);
        color.setGreen(true);
        this.addAbility(DeathtouchAbility.getInstance());
    }
    public PharikaSnakeToken(final PharikaSnakeToken token) {
        super(token);
    }

    public PharikaSnakeToken copy() {
        return new PharikaSnakeToken(this);
    }
}
