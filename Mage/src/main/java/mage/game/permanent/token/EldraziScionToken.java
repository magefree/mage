
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
 * @author fireshoes
 */
public final class EldraziScionToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("BFZ", "OGW", "DDR"));
    }

    public EldraziScionToken() {
        super("Eldrazi Scion Token", "1/1 colorless Eldrazi Scion creature token with \"Sacrifice this creature: Add {C}.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        subtype.add(SubType.SCION);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GenericMana(1), new SacrificeSourceCost()));
        availableImageSetCodes = tokenImageSets;
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("BFZ")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1); // 3 different images
        }
        if (getOriginalExpansionSetCode().equals("OGW")) {
            this.setTokenType(RandomUtil.nextInt(6) + 1); // 6 different images
        }
    }

    public EldraziScionToken(final EldraziScionToken token) {
        super(token);
    }

    @Override
    public EldraziScionToken copy() {
        return new EldraziScionToken(this); //To change body of generated methods, choose Tools | Templates.
    }
}
