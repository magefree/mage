
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.util.RandomUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class EldraziSpawnToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ROE", "MM2", "DDP", "C17"));
    }

    public EldraziSpawnToken() {
        super("Eldrazi Spawn", "0/1 colorless Eldrazi Spawn creature with \"Sacrifice this creature: Add {C}.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        subtype.add(SubType.SPAWN);
        power = new MageInt(0);
        toughness = new MageInt(1);
        addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new SacrificeSourceCost()));

        availableImageSetCodes = tokenImageSets;
        // Get one of the four possible token images
        this.setTokenType(RandomUtil.nextInt(4) + 1);
    }

    public EldraziSpawnToken(final EldraziSpawnToken token) {
        super(token);
    }

    public EldraziSpawnToken copy() {
        return new EldraziSpawnToken(this);
    }
}
