
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class AltarOfDementia extends CardImpl {

    public AltarOfDementia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Sacrifice a creature: Target player puts a number of cards equal to the sacrificed creature's power from the top of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AltarOfDementiaEffect(), new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private AltarOfDementia(final AltarOfDementia card) {
        super(card);
    }

    @Override
    public AltarOfDementia copy() {
        return new AltarOfDementia(this);
    }
}

class AltarOfDementiaEffect extends OneShotEffect {

    public AltarOfDementiaEffect() {
        super(Outcome.Damage);
        staticText = "Target player mills cards equal to the sacrificed creature's power";
    }

    public AltarOfDementiaEffect(final AltarOfDementiaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            int amount = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                    amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getPower().getValue();
                    break;
                }
            }
            if (amount > 0) {
                player.millCards(amount, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public AltarOfDementiaEffect copy() {
        return new AltarOfDementiaEffect(this);
    }
}
