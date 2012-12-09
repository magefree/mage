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
package mage.abilities.keyword;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import mage.Constants;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.costs.OptionalAdditionalSourceCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class BuybackAbility extends StaticAbility<BuybackAbility> implements OptionalAdditionalSourceCosts {

    private static final String keywordTextMana = "Buyback ";
    private static final String keywordTextCost = "Buyback-";
    private static final String reminderTextCost = "<i>(You may {cost} in addition to any other costs as you cast this spell. If you do, put this card into your hand as it resolves.)</i>";
    private static final String reminderTextMana = "<i>(You may pay an additional {cost} as you cast this spell. If you do, put this card into your hand as it resolves.)</i>";
    protected List<OptionalAdditionalCost> buybackCosts = new LinkedList<OptionalAdditionalCost>();

    public BuybackAbility(String manaString) {
       super(Zone.STACK, new BuybackEffect());
       OptionalAdditionalCostImpl buybackCost = new OptionalAdditionalCostImpl(keywordTextMana, reminderTextMana, new ManaCostsImpl(manaString));
       buybackCosts.add(buybackCost);
       setRuleAtTheTop(true);
    }
    
    public BuybackAbility(Cost cost) {
       super(Zone.STACK, new BuybackEffect());
       OptionalAdditionalCostImpl buybackCost = new OptionalAdditionalCostImpl(keywordTextCost, reminderTextCost, cost);
       buybackCosts.add(buybackCost);
       setRuleAtTheTop(true);
    }

    public BuybackAbility(final BuybackAbility ability) {
       super(ability);
       buybackCosts = ability.buybackCosts;
    }

    @Override
    public BuybackAbility copy() {
       return new BuybackAbility(this);
    }

    @Override
    public void addCost(Cost cost) {
        if (buybackCosts.size() > 0) {
            ((Costs) buybackCosts.get(0)).add(cost);
        } else {
            OptionalAdditionalCostImpl buybackCost = new OptionalAdditionalCostImpl(keywordTextCost, reminderTextCost, cost);
            buybackCosts.add(buybackCost);
        }
    }

    public boolean isActivated() {
        for (OptionalAdditionalCost cost: buybackCosts) {
            if(cost.isActivated()) {
                return true;
            }
        }
        return false;
    }

    public void resetBuyback() {
        for (OptionalAdditionalCost cost: buybackCosts) {
            cost.reset();
        }
    }

    public List<OptionalAdditionalCost> getBuybackCosts () {
        return buybackCosts;
    }

    public void addKickerManaCost(OptionalAdditionalCost kickerCost) {
        buybackCosts.add(kickerCost);
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                this.resetBuyback();
                for (OptionalAdditionalCost buybackCost: buybackCosts) {
                    boolean again = true;
                    while (again) {
                        String times = "";
                        if (buybackCost.isRepeatable()) {
                            int activated = buybackCost.getActivateCount();
                            times = Integer.toString(activated + 1) + (activated == 0 ? " time ":" times ");
                        }
                        if (player.chooseUse(Constants.Outcome.Benefit, "Pay " + times + buybackCost.getText(false) + " ?", game)) {
                            buybackCost.activate();
                            for (Iterator it = ((Costs) buybackCost).iterator(); it.hasNext();) {
                                Cost cost = (Cost) it.next();
                                if (cost instanceof ManaCostsImpl) {
                                    ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                                } else {
                                    ability.getCosts().add(cost.copy());
                                }
                            }

                            again = buybackCost.isRepeatable();
                        } else {
                            again = false;
                        }
                    }
                }
            }
        }
    }


    @Override
    public String getRule() {
       StringBuilder sb = new StringBuilder();
       String reminderText = "";
       int numberBuyback = 0;
       for (OptionalAdditionalCost buybackCost: buybackCosts) {
           if (numberBuyback == 0) {
               sb.append(buybackCost.getText(false));
               reminderText = buybackCost.getReminderText();
           } else {
               sb.append(", ").append(buybackCost.getText(true));
           }
           ++numberBuyback;
       }
       sb.append(" ").append(reminderText);

       return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        String message = "";
        if (isActivated()) {
            message = " with " + keywordTextMana;
        }
        return message;
    }

    public String getReminderText() {
        String costsReminderText = reminderTextCost;
        StringBuilder costsText = new StringBuilder();
        int costCounter = 0;
        for (OptionalAdditionalCost cost : buybackCosts) {
            if (costCounter > 0) {
                costsText.append(" and ");
            } else {
                if (((Costs)cost).get(0) instanceof ManaCostsImpl) {
                    costsReminderText = reminderTextMana;
                }
            }
            costsText.append(cost.getText(true));
            ++costCounter;
        }
        return costsReminderText.replace("{cost}", costsText);
    }
}

class BuybackEffect extends ReplacementEffectImpl<BuybackEffect> {

    public BuybackEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Exile);
        staticText = "When {this} resolves and you payed buyback costs, put it back to hand instead";
    }

    public BuybackEffect(final BuybackEffect effect) {
        super(effect);
    }

    @Override
    public BuybackEffect copy() {
        return new BuybackEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.STACK && 
                    (event.getAppliedEffects() == null || !event.getAppliedEffects().contains(this.getId()))) {
                event.getAppliedEffects().add(this.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && source instanceof BuybackAbility) {
            if (((BuybackAbility) source).isActivated()) {
                return card.moveToZone(Zone.HAND, source.getId(), game, true, event.getAppliedEffects());
            }
        }
        return false;
    }

}