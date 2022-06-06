package mage.game.permanent.token;

import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LevelX2
 */
public final class GoldToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("BNG", "C15", "C17", "THB", "CLB"));
    }

    public GoldToken() {
        super("Gold Token", "Gold token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.GOLD);

        Cost cost = new SacrificeSourceCost();
        cost.setText("Sacrifice this artifact");
        this.addAbility(new AnyColorManaAbility(cost));
    }

    private GoldToken(final GoldToken token) {
        super(token);
    }

    public GoldToken copy() {
        return new GoldToken(this);
    }
}
