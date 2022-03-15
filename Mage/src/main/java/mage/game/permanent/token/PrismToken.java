
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class PrismToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Collections.singletonList("VIS"));
    }

    public PrismToken() {
        super("Prism Token", "0/1 colorless Prism artifact creature token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PRISM);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }

    public PrismToken(final PrismToken token) {
        super(token);
    }

    @Override
    public PrismToken copy() {
        return new PrismToken(this); //To change body of generated methods, choose Tools | Templates.
    }

}
