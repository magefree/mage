package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class InfernalHarvest extends CardImpl {

    public InfernalHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast Infernal Harvest, return X Swamps you control to their owner's hand.
        this.getSpellAbility().addCost(new InfernalHarvestAdditionalCost());

        // Infernal Harvest deals X damage divided as you choose among any number of target creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(GetXValue.instance));
    }

    private InfernalHarvest(final InfernalHarvest card) {
        super(card);
    }

    @Override
    public InfernalHarvest copy() {
        return new InfernalHarvest(this);
    }
}

class InfernalHarvestAdditionalCost extends VariableCostImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.SWAMP);

    InfernalHarvestAdditionalCost() {
        super(VariableCostType.ADDITIONAL, "Swamps to return");
        this.text = "return " + xText + " Swamps you control to their owner's hand";
    }

    private InfernalHarvestAdditionalCost(final InfernalHarvestAdditionalCost cost) {
        super(cost);
    }

    @Override
    public InfernalHarvestAdditionalCost copy() {
        return new InfernalHarvestAdditionalCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().countAll(filter, source.getControllerId(), game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new InfernalHarvestCost(xValue);
    }

    private static final class InfernalHarvestCost extends CostImpl {

        private final int xValue;

        private InfernalHarvestCost(int xValue) {
            super();
            this.xValue = xValue;
            this.text = "return " + xValue + " Swamps you control to their owner's hand";
            this.addTarget(new TargetControlledPermanent(xValue, xValue, filter, true));
        }

        private InfernalHarvestCost(final InfernalHarvestCost cost) {
            super(cost);
            this.xValue = cost.xValue;
        }

        @Override
        public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
            return game.getBattlefield().countAll(filter, controllerId, game) >= xValue;
        }

        @Override
        public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
            if (!this.canPay(ability, source, controllerId, game)) {
                return false;
            }
            Player player = game.getPlayer(controllerId);
            if (player == null || !targets.choose(Outcome.ReturnToHand, controllerId, source.getSourceId(), source, game)) {
                return false;
            }
            return paid = player.moveCards(
                    targets.stream()
                            .map(Target::getTargets)
                            .flatMap(Collection::stream)
                            .map(game::getCard)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet()),
                    Zone.HAND, ability, game
            );
        }

        @Override
        public Cost copy() {
            return new InfernalHarvestCost(this);
        }
    }
}
