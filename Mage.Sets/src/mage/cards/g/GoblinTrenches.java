package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoblinSoldierToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class GoblinTrenches extends CardImpl {

    public GoblinTrenches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}");

        // {2}, Sacrifice a land: Create two 1/1 red and white Goblin Soldier creature tokens.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new GoblinSoldierToken(), 2), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private GoblinTrenches(final GoblinTrenches card) {
        super(card);
    }

    @Override
    public GoblinTrenches copy() {
        return new GoblinTrenches(this);
    }
}
