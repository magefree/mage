
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LifesLegacy extends CardImpl {

    public LifesLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // As an additional cost to cast Life's Legacy, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        // Draw cards equal to the sacrificed creature's power.
        this.getSpellAbility().addEffect(new LifesLegacyEffect());

    }

    private LifesLegacy(final LifesLegacy card) {
        super(card);
    }

    @Override
    public LifesLegacy copy() {
        return new LifesLegacy(this);
    }
}

class LifesLegacyEffect extends OneShotEffect {

    public LifesLegacyEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw cards equal to the sacrificed creature's power";
    }

    public LifesLegacyEffect(final LifesLegacyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int power = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                power = ((SacrificeTargetCost) cost).getPermanents().get(0).getPower().getValue();
                break;
            }
        }
        if (power > 0) {
            controller.drawCards(power, source, game);
        }
        return true;
    }

    @Override
    public LifesLegacyEffect copy() {
        return new LifesLegacyEffect(this);
    }
}
