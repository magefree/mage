

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author spjspj
 */
public final class RagavanToken extends TokenImpl {

    public RagavanToken() {
        super("Ragavan", "Ragavan, a legendary 2/1 red Monkey creature token");
        this.setOriginalExpansionSetCode("AER");
        this.supertype.add(SuperType.LEGENDARY);
        this.getPower().modifyBaseValue(2);
        this.getToughness().modifyBaseValue(1);
        this.color.setRed(true);
        this.subtype.add(SubType.MONKEY);
        this.cardType.add(CardType.CREATURE);
    }

    public RagavanToken(final RagavanToken token) {
        super(token);
    }

    public RagavanToken copy() {
        return new RagavanToken(this);
    }
}
