package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */

public final class ShardToken extends TokenImpl {

    public ShardToken() {
        super("Shard Token", "Shard token");
        cardType.add(CardType.ENCHANTMENT);
        subtype.add(SubType.SHARD);

        // {2}, Sacrifice this enchantment: Scry 1, then draw a card.
        Ability ability = new SimpleActivatedAbility(new ScryEffect(1), new GenericManaCost(2));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        SacrificeSourceCost cost = new SacrificeSourceCost();
        cost.setText("Sacrifice this enchantment");
        ability.addCost(cost);
        this.addAbility(ability);

        availableImageSetCodes = Arrays.asList("KHM");
    }

    public ShardToken(final ShardToken token) {
        super(token);
    }

    public ShardToken copy() {
        return new ShardToken(this);
    }
}
