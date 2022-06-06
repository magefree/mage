
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.AnnihilatorAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class EldraziAnnihilatorToken extends TokenImpl {

    public EldraziAnnihilatorToken() {
        super("Eldrazi Token", "7/7 colorless Eldrazi creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        setExpansionSetCodeForImage("PCA");
        power = new MageInt(7);
        toughness = new MageInt(7);
        addAbility(new AnnihilatorAbility(1));

        availableImageSetCodes = Arrays.asList("PCA");
    }

    public EldraziAnnihilatorToken(final EldraziAnnihilatorToken token) {
        super(token);
    }

    public EldraziAnnihilatorToken copy() {
        return new EldraziAnnihilatorToken(this);
    }
}
