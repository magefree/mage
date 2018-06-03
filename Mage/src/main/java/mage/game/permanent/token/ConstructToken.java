

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;

/**
 *
 * @author spjspj
 */
public final class ConstructToken extends TokenImpl {

    public ConstructToken() {
        this("CNS");
    }

    public ConstructToken(String setCode) {
        super("Construct", "1/1 colorless Construct artifact creature token with defender");
        this.setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(DefenderAbility.getInstance());
    }

    public ConstructToken(final ConstructToken token) {
        super(token);
    }

    public ConstructToken copy() {
        return new ConstructToken(this);
    }
}
