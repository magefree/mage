package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jmharmon
 */

public final class FoodToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ELD"));
    }

    public FoodToken() {
        super("Food", "Food token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.FOOD);

        // {2}, {T}, Sacrifice this artifact: You gain 3 life.”
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        SacrificeSourceCost cost = new SacrificeSourceCost();
        cost.setText("Sacrifice this artifact");
        ability.addCost(cost);
        this.addAbility(ability);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ELD")) {
            setTokenType(RandomUtil.nextInt(4) + 1); // 1...4
        }
    }

    public FoodToken(final FoodToken token) {
        super(token);
    }

    public FoodToken copy() {
        return new FoodToken(this);
    }
}
