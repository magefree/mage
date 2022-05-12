

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class PentaviteToken extends TokenImpl {

    public PentaviteToken() {
        super("Pentavite Token", "1/1 colorless Pentavite artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PENTAVITE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    public PentaviteToken(final PentaviteToken token) {
        super(token);
    }

    public PentaviteToken copy() {
        return new PentaviteToken(this);
    }
}
