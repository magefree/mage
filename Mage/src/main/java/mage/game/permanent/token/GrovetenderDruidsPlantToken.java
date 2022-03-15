

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class GrovetenderDruidsPlantToken extends TokenImpl {

    public GrovetenderDruidsPlantToken() {
        super("Plant Token", "1/1 green Plant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PLANT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.setOriginalExpansionSetCode("BFZ");
    }

    public GrovetenderDruidsPlantToken(final GrovetenderDruidsPlantToken token) {
        super(token);
    }

    public GrovetenderDruidsPlantToken copy() {
        return new GrovetenderDruidsPlantToken(this);
    }
}
