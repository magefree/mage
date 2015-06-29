/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.costs;

import java.util.Iterator;
import mage.abilities.Ability;
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

/**
 *
 * @author LevelX2
 */
public class AlternativeCostSourceAbility extends StaticAbility implements AlternativeSourceCosts {

    Costs<AlternativeCost2> alternateCosts = new CostsImpl<>();
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
     *
     * @param cost alternate cost to pay
     * @param condition only if the condition is true it's possible to use the
     * alternate costs
     * @param rule if != null used as rule text
     * @param filter filters the cards this alternate cost can be applied to
     * @param onlyMana if true only the mana costs are replaced by this costs,
     * other costs stay untouched
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
        return cost != null ? new AlternativeCost2Impl(null, cost.getText(), cost) : null;
    }

    @Override
    public AlternativeCostSourceAbility copy() {
        return new AlternativeCostSourceAbility(this);
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        if (condition != null) {
            return condition.apply(game, source);
        }
        return true;
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability != null && AbilityType.SPELL.equals(ability.getAbilityType())) {
            if (filter != null) {
                Card card = game.getCard(ability.getSourceId());
                if (!filter.match(card, ability.getSourceId(), ability.getControllerId(), game)) {
                    return false;
                }
            }
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null) {
                Costs<AlternativeCost2> alternativeCosts;
                if (dynamicCost != null) {
                    alternativeCosts = new CostsImpl<>();
                    alternativeCosts.add(convertToAlternativeCost(dynamicCost.getCost(ability, game)));
                } else {
                    alternativeCosts = this.alternateCosts;
                }

                String costChoiceText;
                if (dynamicCost != null) {
                    costChoiceText = dynamicCost.getText(ability, game);
                } else {
                    costChoiceText = alternativeCosts.isEmpty() ? "Cast without paying its mana cost?" : "Pay alternative costs? (" + alternativeCosts.getText() + ")";
                }

                if (alternativeCosts.canPay(ability, ability.getSourceId(), ability.getControllerId(), game)
                        && player.chooseUse(Outcome.Benefit, costChoiceText, this, game)) {
                    ability.getManaCostsToPay().clear();
                    if (!onlyMana) {
                        ability.getCosts().clear();
                    }
                    for (Cost cost : alternativeCosts) {
                        AlternativeCost2 alternateCost = (AlternativeCost2) cost;
                        alternateCost.activate();
                        for (Iterator it = ((Costs) alternateCost).iterator(); it.hasNext();) {
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
        Costs<AlternativeCost2> alternativeCosts;
        if (dynamicCost != null) {
            alternativeCosts = new CostsImpl<>();
            alternativeCosts.add(convertToAlternativeCost(dynamicCost.getCost(source, game)));
        } else {
            alternativeCosts = this.alternateCosts;
        }
        for (AlternativeCost2 cost : alternativeCosts) {
            if (cost.isActivated(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCastMessageSuffix(Game game) {
        return alternateCosts.isEmpty() ? " without paying it's mana costs" : " using alternative casting costs";
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
                sb.append(", rather than pay {source}'s mana cost, ");
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
                sb.append(alternativeCost.getText(true));
            }
            ++numberCosts;
        }
        if (condition == null || alternateCosts.size() == 1) {
            sb.append(" rather than pay {source}'s mana cost");
        } else if (alternateCosts.isEmpty()) {
            sb.append("cast {this} without paying its mana cost");
        }
        sb.append(".");
        if (numberCosts == 1 && remarkText != null) {
            sb.append(" ").append(remarkText);
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
