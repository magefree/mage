

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
public final class HornetQueenInsectToken extends TokenImpl {

    public HornetQueenInsectToken() {
        super("Insect", "1/1 green Insect creature token with flying and deathtouch");
        setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        addAbility(DeathtouchAbility.getInstance());
    }

    public HornetQueenInsectToken(final HornetQueenInsectToken token) {
        super(token);
    }

    public HornetQueenInsectToken copy() {
        return new HornetQueenInsectToken(this);
    }
}
