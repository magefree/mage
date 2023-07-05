package mage.abilities.costs;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Targets;
import mage.util.CardUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrCost implements Cost {

    private final List<Cost> costs = new ArrayList<>();
    private String description;
    // which cost was slected to pay
    private Cost selectedCost;

    public OrCost(String description, Cost... costs) {
        Collections.addAll(this.costs, costs);
        this.description = description;
    }

    public OrCost(final OrCost cost) {
        cost.costs.stream().map(Cost::copy).forEach(this.costs::add);
        this.description = cost.description;
        this.selectedCost = cost.selectedCost;
    }

    @Override
    public UUID getId() {
        throw new RuntimeException("Not supported method");
    }

    @Override
    public OrCost setText(String text) {
        this.description = text;
        return this;
    }

    @Override
    public String getText() {
        return description;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return costs.stream().anyMatch(cost -> cost.canPay(ability, source, controllerId, game));
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana) {
        return pay(ability, game, source, controllerId, noMana, this);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        selectedCost = null;
        List<Cost> usable = costs
                .stream()
                .filter(cost -> cost.canPay(ability, source, controllerId, game))
                .collect(Collectors.toList());
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        switch (usable.size()) {
            case 0:
                return false;
            case 1:
                selectedCost = usable.get(0);
                break;
            case 2:
                StringBuilder sb = new StringBuilder();
                if (usable.get(0) instanceof ManaCost) {
                    sb.append("Pay ");
                    sb.append(usable.get(0).getText());
                } else {
                    sb.append(CardUtil.getTextWithFirstCharUpperCase(usable.get(0).getText()));
                }
                sb.append(" or ");
                sb.append(usable.get(1).getText());
                sb.append('?');
                if (controller.chooseUse(
                        Outcome.Detriment, sb.toString(), null,
                        CardUtil.getTextWithFirstCharUpperCase(usable.get(0).getText()),
                        CardUtil.getTextWithFirstCharUpperCase(usable.get(1).getText()),
                        ability, game
                )) {
                    selectedCost = usable.get(0);
                } else {
                    selectedCost = usable.get(1);
                }
                break;
            default:
                Map<String, Cost> costMap = usable
                        .stream()
                        .collect(Collectors.toMap(Cost::getText, Function.identity()));
                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Choose a cost to pay");
                choice.setChoices(costMap
                        .keySet()
                        .stream()
                        .map(CardUtil::getTextWithFirstCharUpperCase)
                        .collect(Collectors.toSet()));
                controller.choose(Outcome.Neutral, choice, game);
                selectedCost = costMap.getOrDefault(
                        CardUtil.getTextWithFirstCharLowerCase(choice.getChoice()), null
                );
        }
        if (selectedCost == null) {
            return false;
        }
        return selectedCost.pay(ability, game, source, controllerId, noMana, costToPay);
    }

    @Override
    public boolean isPaid() {
        return selectedCost != null ? selectedCost.isPaid() : false;
    }

    @Override
    public void clearPaid() {
        selectedCost = null;
        costs.forEach(Cost::clearPaid);
    }

    @Override
    public void setPaid() {
        if (selectedCost != null) {
            selectedCost.setPaid();
        }
    }

    @Override
    public Targets getTargets() {
        if (selectedCost != null) {
            return selectedCost.getTargets();
        }
        return null;
    }

    @Override
    public Cost copy() {
        return new OrCost(this);
    }
}
