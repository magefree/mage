

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class HornetNestInsectToken extends TokenImpl {

    public HornetNestInsectToken() {
        super("Insect", "1/1 green Insect creature tokens with flying and deathtouch");
        setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());
    }

    public HornetNestInsectToken(final HornetNestInsectToken token) {
        super(token);
    }

    public HornetNestInsectToken copy() {
        return new HornetNestInsectToken(this);
    }
}
