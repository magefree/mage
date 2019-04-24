
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EldraziHorrorToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Collections.singletonList("EMN"));
    }

    public EldraziHorrorToken() {
        super("Eldrazi Horror", "3/2 colorless Eldrazi Horror creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        subtype.add(SubType.HORROR);
        power = new MageInt(3);
        toughness = new MageInt(2);
        availableImageSetCodes = tokenImageSets;
    }

    public EldraziHorrorToken(final EldraziHorrorToken token) {
        super(token);
    }

    public EldraziHorrorToken copy() {
        return new EldraziHorrorToken(this);
    }
}