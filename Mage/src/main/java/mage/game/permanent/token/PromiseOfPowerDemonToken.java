

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class PromiseOfPowerDemonToken extends TokenImpl {

    public PromiseOfPowerDemonToken() {
        this(1);
    }

    public PromiseOfPowerDemonToken(int xValue) {
        super("Demon", "X/X black Demon creature token with flying");
        setOriginalExpansionSetCode("C14");
        setTokenType(2);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        addAbility(FlyingAbility.getInstance());
    }

    public PromiseOfPowerDemonToken(final PromiseOfPowerDemonToken token) {
        super(token);
    }

    public PromiseOfPowerDemonToken copy() {
        return new PromiseOfPowerDemonToken(this);
    }
}
