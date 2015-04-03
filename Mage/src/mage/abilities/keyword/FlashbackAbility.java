/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 * 702.32. Flashback
 *
 * 702.32a. Flashback appears on some instants and sorceries. It represents two static abilities: 
 * one that functions while the card is in a player‘s graveyard and the other that functions 
 * while the card is on the stack. Flashback [cost] means, "You may cast this card from your 
 * graveyard by paying [cost] rather than paying its mana cost" and, "If the flashback cost 
 * was paid, exile this card instead of putting it anywhere else any time it would leave the 
 * stack." Casting a spell using its flashback ability follows the rules for paying alternative 
 * costs in rules 601.2b and 601.2e–g.
 *
 * @author nantuko
 */
public class FlashbackAbility extends SpellAbility {

    private String abilityName;

    public FlashbackAbility(Cost cost, TimingRule timingRule) {
        super(null, "", Zone.GRAVEYARD);
        this.setAdditionalCostsRuleVisible(false);
        this.name = new StringBuilder("Flashback ").append(cost.getText()).toString();
        this.addEffect(new FlashbackEffect());
        this.addCost(cost);
        this.timing = timingRule;
        this.usesStack = false;
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
    }

    public FlashbackAbility(final FlashbackAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
        this.abilityName = ability.abilityName;
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game)) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                // Flashback can never cast a split card by Fuse, because Fuse only works from hand
                if (card.isSplitCard()) {
                    if (((SplitCard)card).getLeftHalfCard().getName().equals(abilityName)) {
                        return ((SplitCard)card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((SplitCard)card).getRightHalfCard().getName().equals(abilityName)) {
                        return ((SplitCard)card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);                        
                    }
                }
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return false;
    }

    @Override
    public FlashbackAbility copy() {
        return new FlashbackAbility(this);
    }
   
    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder("Flashback");
        if (costs.size() > 0) {
            sbRule.append(" - ");
        } else {
            sbRule.append(" ");
        }
        if (manaCosts.size() > 0) {
            sbRule.append(manaCosts.getText());
        }
        if (costs.size() > 0) {
            sbRule.append(costs.getText());
            sbRule.append(".");
        }
        if (abilityName != null) {
            sbRule.append(" ");
            sbRule.append(abilityName);
        }
        sbRule.append(" <i>(You may cast this card from your graveyard for its flashback cost. Then exile it.)</i>");
        return sbRule.toString();
    }

    @Override
    public void setSpellAbilityType(SpellAbilityType spellAbilityType) {
        this.spellAbilityType = spellAbilityType;
    }

    @Override
    public SpellAbilityType getSpellAbilityType() {
        return this.spellAbilityType;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

}

class FlashbackEffect extends OneShotEffect {

    public FlashbackEffect() {
        super(Outcome.Benefit);
        staticText = "";
    }

    public FlashbackEffect(final FlashbackEffect effect) {
        super(effect);
    }

    @Override
    public FlashbackEffect copy() {
        return new FlashbackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) game.getObject(source.getSourceId());
        if (card != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                SpellAbility spellAbility;
                switch(((FlashbackAbility) source).getSpellAbilityType()) {
                    case SPLIT_LEFT:
                        spellAbility = ((SplitCard)card).getLeftHalfCard().getSpellAbility();
                        break;
                    case SPLIT_RIGHT:
                        spellAbility = ((SplitCard)card).getRightHalfCard().getSpellAbility();
                        break;
                    default:
                        spellAbility = card.getSpellAbility();
                }

                spellAbility.clear();
                // used if flashbacked spell has a {X} cost
                int amount = source.getManaCostsToPay().getX();
                if (amount == 0) {
                    // add variable cost like Discard X cards to get the X value to the spell
                    // because there is currently no way to set the x value in anotehr way, it's set for the
                    // x mana value to be known by the spell
                    for (Cost cost:source.getCosts()) {
                        if (cost instanceof VariableCost && cost.isPaid()) {
                            amount = ((VariableCost) cost).getAmount();
                            break;
                        }
                    }                    
                }
                if (amount > 0) {
                    // multiplier must be taken into account because if the base spell has {X}{X} the x value would be wrongly halfed
                    for (VariableCost variableCost: spellAbility.getManaCostsToPay().getVariableCosts()) {
                        if (variableCost instanceof  VariableManaCost) {
                            amount = amount * ((VariableManaCost)variableCost).getMultiplier();
                            break;
                        }
                    }
                    spellAbility.getManaCostsToPay().setX(amount);
                }
                game.informPlayers(new StringBuilder(controller.getName()).append(" flashbacks ").append(card.getName()).toString());
                spellAbility.setCostModificationActive(false); // prevents to apply cost modification twice for flashbacked spells
                if (controller.cast(spellAbility, game, true)) {
                    game.addEffect(new FlashbackReplacementEffect(), source);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}

class FlashbackReplacementEffect extends ReplacementEffectImpl {

    public FlashbackReplacementEffect() {
        super(Duration.OneUse, Outcome.Exile);
        staticText = "(If the flashback cost was paid, exile this card instead of putting it anywhere else any time it would leave the stack)";
    }

    public FlashbackReplacementEffect(final FlashbackReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FlashbackReplacementEffect copy() {
        return new FlashbackReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                return controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, game.getState().getZone(card.getId()));
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId()) 
                && ((ZoneChangeEvent)event).getFromZone() == Zone.STACK
                && ((ZoneChangeEvent)event).getToZone() != Zone.EXILED;
    }
}