

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class SkywiseTeachingsToken extends TokenImpl {

    public SkywiseTeachingsToken() {
        super("Djinn Monk Token", "2/2 blue Djinn Monk creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    public SkywiseTeachingsToken(final SkywiseTeachingsToken token) {
        super(token);
    }

    public SkywiseTeachingsToken copy() {
        return new SkywiseTeachingsToken(this);
    }
}
