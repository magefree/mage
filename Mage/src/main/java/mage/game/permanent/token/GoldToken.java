

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class GoldToken extends TokenImpl {
    
    static final private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("BNG", "C17"));
    }

    public GoldToken() {
        this((String)null);
    }

    public GoldToken(String setCode) {
        super("Gold", "colorless artifact token named Gold with \"Sacrifice this artifact: Add one mana of any color.\"");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.ARTIFACT);

        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new SacrificeSourceCost()));
    }

    public GoldToken(final GoldToken token) {
        super(token);
    }

    public GoldToken copy() {
        return new GoldToken(this);
    }
}
