
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
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinTrenchesToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class GoblinTrenches extends CardImpl {

    static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public GoblinTrenches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}");

        // {2}, Sacrifice a land: Create two 1/1 red and white Goblin Soldier creature tokens.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GoblinTrenchesToken(), 2), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
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
