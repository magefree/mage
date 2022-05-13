

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class HauntedAngelToken extends TokenImpl {

    public HauntedAngelToken() {
        super("Angel Token", "3/3 black Angel creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
    }

    public HauntedAngelToken(final HauntedAngelToken token) {
        super(token);
    }

    public HauntedAngelToken copy() {
        return new HauntedAngelToken(this);
    }
}
