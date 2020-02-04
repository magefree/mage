
package mage.abilities.costs;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Iterator;

/**
 * @author LevelX2
 */
public class AlternativeCostSourceAbility extends StaticAbility implements AlternativeSourceCosts {

    private Costs<AlternativeCost2> alternateCosts = new CostsImpl<>();
    protected Condition condition;
    protected String rule;
    protected FilterCard filter;
    protected boolean onlyMana;
    protected DynamicCost dynamicCost;

    public AlternativeCostSourceAbility(Cost cost) {
        this(cost, null);
    }

    public AlternativeCostSourceAbility(Condition conditon) {
        this(null, conditon, null);
    }

    public AlternativeCostSourceAbility(Cost cost, Condition conditon) {
        this(cost, conditon, null);
    }

    public AlternativeCostSourceAbility(Cost cost, Condition condition, String rule) {
        this(cost, condition, rule, null, true);
    }

    /**
     * @param cost      alternate cost to pay
     * @param condition only if the condition is true it's possible to use the
     *                  alternate costs
     * @param rule      if != null used as rule text
     * @param filter    filters the cards this alternate cost can be applied to
     * @param onlyMana  if true only the mana costs are replaced by this costs,
     *                  other costs stay untouched
     */
    public AlternativeCostSourceAbility(Cost cost, Condition condition, String rule, FilterCard filter, boolean onlyMana) {
        super(Zone.ALL, null);
        this.addCost(cost);
        this.setRuleAtTheTop(true);
        this.condition = condition;
        this.rule = rule;
        this.filter = filter;
        this.onlyMana = onlyMana;
    }

    public AlternativeCostSourceAbility(Condition condition, String rule, FilterCard filter, boolean onlyMana, DynamicCost dynamicCost) {
        super(Zone.ALL, null);
        this.setRuleAtTheTop(true);
        this.condition = condition;
        this.rule = rule;
        this.filter = filter;
        this.onlyMana = onlyMana;
        this.dynamicCost = dynamicCost;
    }

    public AlternativeCostSourceAbility(final AlternativeCostSourceAbility ability) {
        super(ability);
        this.alternateCosts = ability.alternateCosts;
        this.condition = ability.condition;
        this.rule = ability.rule;
        this.filter = ability.filter;
        this.onlyMana = ability.onlyMana;
        this.dynamicCost = ability.dynamicCost;
    }

    @Override
    public void addCost(Cost cost) {
        AlternativeCost2 alternativeCost = convertToAlternativeCost(cost);
        if (alternativeCost != null) {
            this.alternateCosts.add(alternativeCost);
        }
    }

    private AlternativeCost2 convertToAlternativeCost(Cost cost) {
        //return cost != null ? new AlternativeCost2Impl(null, cost.getText(), cost) : null;
        return cost != null ? new AlternativeCost2Impl(null, "", "", cost) : null;
    }

    @Override
    public AlternativeCostSourceAbility copy() {
        return new AlternativeCostSourceAbility(this);
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        boolean conditionApplies = condition == null || condition.apply(game, source);
        boolean filterApplies = filter == null || filter.match(game.getCard(source.getSourceId()), game);

        return conditionApplies && filterApplies;
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability != null && AbilityType.SPELL == ability.getAbilityType()) {
            if (filter != null) {
                Card card = game.getCard(ability.getSourceId());
                if (!filter.match(card, ability.getSourceId(), ability.getControllerId(), game)) {
                    return false;
                }
            }
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null) {
                Costs<AlternativeCost2> alternativeCostsToCheck;
                if (dynamicCost != null) {
                    alternativeCostsToCheck = new CostsImpl<>();
                    alternativeCostsToCheck.add(convertToAlternativeCost(dynamicCost.getCost(ability, game)));
                } else {
                    alternativeCostsToCheck = this.alternateCosts;
                }

                String costChoiceText;
                if (dynamicCost != null) {
                    costChoiceText = dynamicCost.getText(ability, game);
                } else {
                    costChoiceText = alternativeCostsToCheck.isEmpty() ? "Cast without paying its mana cost?" : "Pay alternative costs? (" + alternativeCostsToCheck.getText() + ')';
                }

                if (alternativeCostsToCheck.canPay(ability, ability.getSourceId(), ability.getControllerId(), game)
                        && player.chooseUse(Outcome.Benefit, costChoiceText, this, game)) {
                    if (ability instanceof SpellAbility) {
                        ability.getManaCostsToPay().removeIf(manaCost -> manaCost instanceof VariableCost);
                        CardUtil.reduceCost((SpellAbility) ability, ability.getManaCosts());

                    } else {
                        ability.getManaCostsToPay().clear();
                    }
                    if (!onlyMana) {
                        ability.getCosts().clear();
                    }
                    for (AlternativeCost2 alternateCost : alternativeCostsToCheck) {
                        alternateCost.activate();
                        for (Iterator it = ((Costs) alternateCost).iterator(); it.hasNext(); ) {
                            Cost costDeailed = (Cost) it.next();
                            if (costDeailed instanceof ManaCost) {
                                ability.getManaCostsToPay().add((ManaCost) costDeailed.copy());
                            } else {
                                ability.getCosts().add(costDeailed.copy());
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return isActivated(ability, game);
    }

    @Override
    public boolean isActivated(Ability source, Game game) {
        Costs<AlternativeCost2> alternativeCostsToCheck;
        if (dynamicCost != null) {
            alternativeCostsToCheck = new CostsImpl<>();
            alternativeCostsToCheck.add(convertToAlternativeCost(dynamicCost.getCost(source, game)));
        } else {
            alternativeCostsToCheck = this.alternateCosts;
        }
        for (AlternativeCost2 cost : alternativeCostsToCheck) {
            if (cost.isActivated(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCastMessageSuffix(Game game) {
        return alternateCosts.isEmpty() ? " without paying its mana costs" : " using alternative casting costs";
    }

    @Override
    public String getRule() {
        if (rule != null) {
            return rule;
        }
        StringBuilder sb = new StringBuilder();
        if (condition != null) {
            sb.append(condition.toString());
            if (alternateCosts.size() > 1) {
                sb.append(", rather than pay this spell's mana cost, ");
            } else {
                sb.append(", you may ");
            }
        } else {
            sb.append("You may ");
        }
        int numberCosts = 0;
        String remarkText = "";
        for (AlternativeCost2 alternativeCost : alternateCosts) {
            if (numberCosts == 0) {
                if (alternativeCost.getCost() instanceof ManaCost) {
                    sb.append("pay ");
                }
                sb.append(alternativeCost.getText(false));
                remarkText = alternativeCost.getReminderText();
            } else {
                sb.append(" and ");
                if (alternativeCost.getCost() instanceof ManaCost) {
                    sb.append("pay ");
                }
                String text = alternativeCost.getText(true);
                sb.append(Character.toLowerCase(text.charAt(0)) + text.substring(1));
            }
            ++numberCosts;
        }
        if (condition == null || alternateCosts.size() == 1) {
            sb.append(" rather than pay this spell's mana cost");
        } else if (alternateCosts.isEmpty()) {
            sb.append("cast this spell without paying its mana cost");
        }
        sb.append('.');
        if (numberCosts == 1 && remarkText != null) {
            sb.append(' ').append(remarkText);
        }
        return sb.toString();
    }

    @Override
    public Costs<Cost> getCosts() {
        Costs<Cost> alterCosts = new CostsImpl<>();
        alterCosts.addAll(alternateCosts);
        return alterCosts;
    }

}
