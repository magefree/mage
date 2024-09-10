package mage.game.permanent.token;

import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LevelX2
 */
public final class GoldToken extends TokenImpl {

    public GoldToken() {
        super("Gold Token", "Gold token");
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
