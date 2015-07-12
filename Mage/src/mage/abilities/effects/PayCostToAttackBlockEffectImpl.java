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
package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public abstract class PayCostToAttackBlockEffectImpl extends ReplacementEffectImpl implements PayCostToAttackBlockEffect {

    public static enum RestrictType {

        ATTACK("attack"),
        ATTACK_AND_BLOCK("attack or block"),
        BLOCK("block");

        private final String text;

        RestrictType(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    protected final Cost cost;
    protected final ManaCosts manaCosts;

    protected final RestrictType restrictType;

    public PayCostToAttackBlockEffectImpl(Duration duration, Outcome outcome, RestrictType restrictType) {
        super(duration, outcome, false);
        this.restrictType = restrictType;
        this.cost = null;
        this.manaCosts = null;
    }

    public PayCostToAttackBlockEffectImpl(Duration duration, Outcome outcome, RestrictType restrictType, Cost cost) {
        super(duration, outcome, false);
        this.restrictType = restrictType;
        this.cost = cost;
        this.manaCosts = null;
    }

    public PayCostToAttackBlockEffectImpl(Duration duration, Outcome outcome, RestrictType restrictType, ManaCosts manaCosts) {
        super(duration, outcome, false);
        this.restrictType = restrictType;
        this.cost = null;
        this.manaCosts = manaCosts;
    }

    public PayCostToAttackBlockEffectImpl(PayCostToAttackBlockEffectImpl effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        } else {
            this.cost = null;
        }
        if (effect.manaCosts != null) {
            this.manaCosts = effect.manaCosts.copy();
        } else {
            this.manaCosts = null;
        }
        this.restrictType = effect.restrictType;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (restrictType) {
            case ATTACK:
                return event.getType() == GameEvent.EventType.DECLARE_ATTACKER;
            case BLOCK:
                return event.getType().equals(GameEvent.EventType.DECLARE_BLOCKER);
            case ATTACK_AND_BLOCK:
                return event.getType() == GameEvent.EventType.DECLARE_ATTACKER || event.getType().equals(GameEvent.EventType.DECLARE_BLOCKER);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaCosts attackBlockManaTax = getManaCostToPay(event, source, game);
        if (attackBlockManaTax != null) {
            return handleManaCosts(attackBlockManaTax, event, source, game);
        }
        Cost attackBlockOtherTax = getOtherCostToPay(event, source, game);
        if (attackBlockOtherTax != null) {
            return handleOtherCosts(attackBlockOtherTax, event, source, game);
        }
        return false;
    }

    private boolean handleManaCosts(ManaCosts attackBlockManaTax, GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            String chooseText;
            if (event.getType().equals(GameEvent.EventType.DECLARE_ATTACKER)) {
                chooseText = "Pay " + attackBlockManaTax.getText() + " to attack?";
            } else {
                chooseText = "Pay " + attackBlockManaTax.getText() + " to block?";
            }
            attackBlockManaTax.clearPaid();
            if (attackBlockManaTax.canPay(source, source.getSourceId(), player.getId(), game)
                    && player.chooseUse(Outcome.Neutral, chooseText, source, game)) {
                if (attackBlockManaTax instanceof ManaCostsImpl) {
                    if (attackBlockManaTax.payOrRollback(source, game, source.getSourceId(), event.getPlayerId())) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean handleOtherCosts(Cost attackBlockOtherTax, GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            attackBlockOtherTax.clearPaid();
            if (attackBlockOtherTax.canPay(source, source.getSourceId(), event.getPlayerId(), game)
                    && player.chooseUse(Outcome.Neutral,
                            attackBlockOtherTax.getText() + " to " + (event.getType().equals(EventType.DECLARE_ATTACKER) ? "attack?" : "block?"), source, game)) {
                if (attackBlockOtherTax.pay(source, game, source.getSourceId(), event.getPlayerId(), false)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Cost getOtherCostToPay(GameEvent event, Ability source, Game game) {
        return cost;
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        return manaCosts;
    }

    @Override
    public boolean isCostless(GameEvent event, Ability source, Game game) {
        ManaCosts currentManaCosts = getManaCostToPay(event, source, game);
        if (currentManaCosts != null && currentManaCosts.convertedManaCost() > 0) {
            return false;
        }
        return getOtherCostToPay(event, source, game) == null;
    }

}
