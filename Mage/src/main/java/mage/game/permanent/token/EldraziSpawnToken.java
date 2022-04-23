
package mage.game.permanent.token;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class EldraziSpawnToken extends TokenImpl {

    public EldraziSpawnToken() {
        super("Eldrazi Spawn Token", "0/1 colorless Eldrazi Spawn creature token. It has \"Sacrifice this creature: Add {C}.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        subtype.add(SubType.SPAWN);
        power = new MageInt(0);
        toughness = new MageInt(1);
        addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new SacrificeSourceCost()));

        availableImageSetCodes = Arrays.asList("CMD", "DDP", "MM2", "PC2", "ROE", "MIC", "2XM");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DDP")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1); // randomly take image 1, 2 or 3
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MM2")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1); // randomly take image 1, 2 or 3
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ROE")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1); // randomly take image 1, 2 or 3
        }
    }

    public EldraziSpawnToken(final EldraziSpawnToken token) {
        super(token);
    }

    public EldraziSpawnToken copy() {
        return new EldraziSpawnToken(this);
    }
}
