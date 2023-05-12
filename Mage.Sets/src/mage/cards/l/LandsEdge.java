
package mage.cards.l;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 * @author L_J
 */
public final class LandsEdge extends CardImpl {

    public LandsEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.supertype.add(SuperType.WORLD);

        // Discard a card: If the discarded card was a land card, Land's Edge deals 2 damage to target player. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LandsEdgeEffect(), new DiscardCardCost(false));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private LandsEdge(final LandsEdge card) {
        super(card);
    }

    @Override
    public LandsEdge copy() {
        return new LandsEdge(this);
    }

}

class LandsEdgeEffect extends OneShotEffect {

    public LandsEdgeEffect() {
        super(Outcome.Neutral);
        staticText = "If the discarded card was a land card, {this} deals 2 damage to target player";
    }

    public LandsEdgeEffect(final LandsEdgeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            DiscardCardCost cost = (DiscardCardCost) source.getCosts().get(0);
            if (cost != null) {
                List<Card> cards = cost.getCards();
                if (cards.size() == 1 && cards.get(0).isLand(game)) {
                    Effect effect = new DamageTargetEffect(2);
                    effect.setTargetPointer(getTargetPointer());
                    effect.apply(game, source);
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public LandsEdgeEffect copy() {
        return new LandsEdgeEffect(this);
    }
}
