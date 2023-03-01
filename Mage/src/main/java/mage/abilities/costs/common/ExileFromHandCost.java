package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.mana.VariableManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class ExileFromHandCost extends CostImpl {

    List<Card> cards = new ArrayList<>();
    private final boolean setXFromCMC;

    public ExileFromHandCost(TargetCardInHand target) {
        this(target, false);
    }

    /**
     * @param target
     * @param setXFromCMC the spells X value on the stack is set to the
     *                    converted mana costs of the exiled card (alternative cost)
     */
    public ExileFromHandCost(TargetCardInHand target, boolean setXFromCMC) {
        this.addTarget(target);
        this.text = "exile " + target.getDescription();
        this.setXFromCMC = setXFromCMC;
    }

    public ExileFromHandCost(final ExileFromHandCost cost) {
        super(cost);
        for (Card card : cost.cards) {
            this.cards.add(card.copy());
        }
        this.setXFromCMC = cost.setXFromCMC;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.Exile, controllerId, source.getSourceId(), source, game)) {
            Player player = game.getPlayer(controllerId);
            int cmc = 0;
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card == null) {
                    return false;
                }
                cmc += card.getManaValue();
                this.cards.add(card);
            }
            Cards cardsToExile = new CardsImpl();
            cardsToExile.addAll(cards);
            player.moveCards(cardsToExile, Zone.EXILED, ability, game);
            paid = true;
            if (setXFromCMC) {
                VariableManaCost vmc = new VariableManaCost(VariableCostType.ALTERNATIVE);
                // no x events - rules from Unbound Flourishing:
                // - Spells with additional costs that include X won't be affected by Unbound Flourishing. X must be in the spell's mana cost.
                // TODO: wtf, look at setXFromCMC usage -- it used in cards with alternative costs, not additional... need to fix?
                vmc.setAmount(cmc, cmc, false);
                vmc.setPaid();
                ability.getManaCostsToPay().add(vmc);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public ExileFromHandCost copy() {
        return new ExileFromHandCost(this);
    }

    public List<Card> getCards() {
        return cards;
    }
}
