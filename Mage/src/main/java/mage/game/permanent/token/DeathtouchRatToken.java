

package mage.game.permanent.token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.SubType;

/**
 *
 * @author Saga
 */
public final class DeathtouchRatToken extends TokenImpl {
    
    static final private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("C17"));
    }

    public DeathtouchRatToken() {
        super("Rat Token", "1/1 black Rat creature token with deathtouch");
        this.setExpansionSetCodeForImage("C17");
        availableImageSetCodes = tokenImageSets;
        this.cardType.add(CardType.CREATURE);
        this.color.setBlack(true);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(DeathtouchAbility.getInstance());
    }

    public DeathtouchRatToken(final DeathtouchRatToken token) {
        super(token);
    }

    public DeathtouchRatToken copy() {
        return new DeathtouchRatToken(this);
    }
}
