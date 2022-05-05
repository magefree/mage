
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author JRHerlehy
 *         Created on 4/5/18.
 */
public final class KaroxBladewingDragonToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("DOM"));
    }

    public KaroxBladewingDragonToken() {
        super("Karox Bladewing Token", "legendary 4/4 red Dragon creature token with flying", 4, 4);

        availableImageSetCodes = tokenImageSets;
        this.setOriginalExpansionSetCode("DOM");

        this.addSuperType(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.DRAGON);
        this.color.setRed(true);

        this.addAbility(FlyingAbility.getInstance());
    }

    public KaroxBladewingDragonToken(final KaroxBladewingDragonToken token) {
        super(token);
    }

    @Override
    public KaroxBladewingDragonToken copy() {
        return new KaroxBladewingDragonToken(this);
    }
}
