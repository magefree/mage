
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CarnivoreToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class ToothAndClaw extends CardImpl {

    public ToothAndClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Sacrifice two creatures: Create a 3/1 red Beast creature token named Carnivore.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new CarnivoreToken(), 1),
                new SacrificeTargetCost(2, StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private ToothAndClaw(final ToothAndClaw card) {
        super(card);
    }

    @Override
    public ToothAndClaw copy() {
        return new ToothAndClaw(this);
    }
}
