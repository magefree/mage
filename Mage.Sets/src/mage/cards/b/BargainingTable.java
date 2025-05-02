package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.EarlyTargetCost;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author awjackson, JayDi85
 */
public final class BargainingTable extends CardImpl {

    public BargainingTable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {X}, {T}: Draw a card. X is the number of cards in an opponent's hand.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new BargainingTableXCost());
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("X is the number of cards in an opponent's hand"));
        ability.setCostAdjuster(BargainingTableCostAdjuster.instance);
        this.addAbility(ability);
    }

    private BargainingTable(final BargainingTable card) {
        super(card);
    }

    @Override
    public BargainingTable copy() {
        return new BargainingTable(this);
    }
}

class BargainingTableXCost extends VariableManaCost implements EarlyTargetCost {

    // You choose an opponent on announcement. This is not targeted, but a choice is still made.
    // This choice is made before determining the value for X that is used in the cost.
    // (2004-10-04)

    public BargainingTableXCost() {
        super(VariableCostType.NORMAL, 1);
    }

    public BargainingTableXCost(final BargainingTableXCost cost) {
        super(cost);
    }

    @Override
    public void chooseTarget(Game game, Ability source, Player controller) {
        Target targetOpponent = new TargetOpponent(true);
        controller.choose(Outcome.Benefit, targetOpponent, source, game);
        addTarget(targetOpponent);
    }

    @Override
    public BargainingTableXCost copy() {
        return new BargainingTableXCost(this);
    }
}

enum BargainingTableCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void prepareX(Ability ability, Game game) {
        // make sure early target used
        BargainingTableXCost cost = ability.getManaCostsToPay().getVariableCosts().stream()
                .filter(c -> c instanceof BargainingTableXCost)
                .map(c -> (BargainingTableXCost) c)
                .findFirst()
                .orElse(null);
        if (cost == null) {
            throw new IllegalArgumentException("Wrong code usage: cost item lost");
        }

        if (game.inCheckPlayableState()) {
            // possible X
            int minHandSize = game.getOpponents(ability.getControllerId(), true).stream()
                    .map(game::getPlayer)
                    .filter(Objects::nonNull)
                    .mapToInt(p -> p.getHand().size())
                    .min()
                    .orElse(0);
            int maxHandSize = game.getOpponents(ability.getControllerId(), true).stream()
                    .map(game::getPlayer)
                    .filter(Objects::nonNull)
                    .mapToInt(p -> p.getHand().size())
                    .max()
                    .orElse(Integer.MAX_VALUE);
            ability.setVariableCostsMinMax(minHandSize, maxHandSize);
        } else {
            // real X
            Player opponent = game.getPlayer(cost.getTargets().getFirstTarget());
            if (opponent == null) {
                throw new IllegalStateException("Wrong code usage: cost target lost");
            }
            ability.setVariableCostsValue(opponent.getHand().size());
        }
    }
}