
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class UtvaraHellkiteDragonToken extends TokenImpl {
    
    static final private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("RTR", "C17", "GK2"));
    }

    public UtvaraHellkiteDragonToken() {
        super("Dragon Token", "6/6 red Dragon creature token with flying");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
    }
    public UtvaraHellkiteDragonToken(final UtvaraHellkiteDragonToken token) {
        super(token);
    }

    public UtvaraHellkiteDragonToken copy() {
        return new UtvaraHellkiteDragonToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C17")) {
            setTokenType(2);
        }
    }
    
}
