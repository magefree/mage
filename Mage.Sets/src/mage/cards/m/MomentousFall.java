
package mage.cards.m;

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
 * @author jeffwadsworth
 */
public final class MomentousFall extends CardImpl {

    public MomentousFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{G}");

        // As an additional cost to cast Momentous Fall, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // You draw cards equal to the sacrificed creature's power, then you gain life equal to its toughness.
        this.getSpellAbility().addEffect(new MomentousFallEffect());
    }

    private MomentousFall(final MomentousFall card) {
        super(card);
    }

    @Override
    public MomentousFall copy() {
        return new MomentousFall(this);
    }
}

class MomentousFallEffect extends OneShotEffect {

    public MomentousFallEffect() {
        super(Outcome.GainLife);
        staticText = "You draw cards equal to the sacrificed creature's power, then you gain life equal to its toughness";
    }

    public MomentousFallEffect(final MomentousFallEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

            int power = 0;
            int toughness = 0;

            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                    power = ((SacrificeTargetCost) cost).getPermanents().get(0).getPower().getValue();
                    toughness = ((SacrificeTargetCost) cost).getPermanents().get(0).getToughness().getValue();
                    break;
                }
            }
            if (power > 0) {
                controller.drawCards(power, source, game);
            }
            if (toughness > 0) {
                controller.gainLife(toughness, game, source);
            }
            return true;
        }
        return false;

    }

    @Override
    public MomentousFallEffect copy() {
        return new MomentousFallEffect(this);
    }
}
