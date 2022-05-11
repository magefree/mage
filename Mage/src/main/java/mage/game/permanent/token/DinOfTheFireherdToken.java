

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class DinOfTheFireherdToken extends TokenImpl {

    public DinOfTheFireherdToken() {
        super("Elemental Token", "5/5 black and red Elemental creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setBlack(true);
        color.setRed(true);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("SHM")) {
            this.setTokenType(1);
        }
    }

    public DinOfTheFireherdToken(final DinOfTheFireherdToken token) {
        super(token);
    }

    public DinOfTheFireherdToken copy() {
        return new DinOfTheFireherdToken(this);
    }
}
