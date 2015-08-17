/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities;

import java.util.UUID;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.AsThoughEffectType;
import mage.constants.TargetController;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ActivatedAbilityImpl extends AbilityImpl implements ActivatedAbility {

    protected TimingRule timing = TimingRule.INSTANT;
    protected TargetController mayActivate = TargetController.YOU;
    protected UUID activatorId;
    protected boolean checkPlayableMode;

    protected ActivatedAbilityImpl(AbilityType abilityType, Zone zone) {
        super(abilityType, zone);
        this.checkPlayableMode = false;
    }

    public ActivatedAbilityImpl(final ActivatedAbilityImpl ability) {
        super(ability);
        timing = ability.timing;
        mayActivate = ability.mayActivate;
        activatorId = ability.activatorId;
        checkPlayableMode = ability.checkPlayableMode;
    }

    public ActivatedAbilityImpl(Zone zone) {
        this(zone, null);
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, ManaCosts cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
        if (cost != null) {
            this.addManaCost(cost);
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, ManaCosts cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effects != null) {
            for (Effect effect : effects) {
                this.addEffect(effect);
            }
        }
        if (cost != null) {
            this.addManaCost(cost);
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, Cost cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
        if (cost != null) {
            if (cost instanceof PhyrexianManaCost) {
                this.addManaCost((PhyrexianManaCost) cost);
            } else {
                this.addCost(cost);
            }
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, Costs<Cost> costs) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
        if (costs != null) {
            for (Cost cost : costs) {
                this.addCost(cost);
            }
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, Cost cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effects != null) {
            for (Effect effect : effects) {
                this.addEffect(effect);
            }
        }
        if (cost != null) {
            this.addCost(cost);
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, Costs<Cost> costs) {
        super(AbilityType.ACTIVATED, zone);
        for (Effect effect : effects) {
            if (effect != null) {
                this.addEffect(effect);
            }
        }
        if (costs != null) {
            for (Cost cost : costs) {
                this.addCost(cost);
            }
        }
    }

    @Override
    public abstract ActivatedAbilityImpl copy();

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        //20091005 - 602.2
        switch (mayActivate) {
            case ANY:
                break;

            case NOT_YOU:
                if (controlsAbility(playerId, game)) {
                    return false;
                }
                break;

            case OPPONENT:
                if (!game.getPlayer(controllerId).hasOpponent(playerId, game)) {
                    return false;
                }
                break;
            case YOU:
                if (!controlsAbility(playerId, game)) {
                    return false;
                }
                break;
            case CONTROLLER_ATTACHED_TO:
                Permanent enchantment = game.getPermanent(getSourceId());
                if (enchantment != null && enchantment.getAttachedTo() != null) {
                    Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
                    if (enchanted != null && enchanted.getControllerId().equals(playerId)) {
                        break;
                    }
                }
                return false;
        }
        //20091005 - 602.5d/602.5e
        if (timing == TimingRule.INSTANT || game.canPlaySorcery(playerId)
                || game.getContinuousEffects().asThough(sourceId, AsThoughEffectType.ACTIVATE_AS_INSTANT, this, controllerId, game)) {
            if (costs.canPay(this, sourceId, controllerId, game) && canChooseTarget(game)) {
                this.activatorId = playerId;
                return true;
            }
        }
        return false;
    }

    protected boolean controlsAbility(UUID playerId, Game game) {
        if (this.controllerId != null && this.controllerId.equals(playerId)) {
            return true;
        } else {
            Card card = (Card) game.getObject(this.sourceId);
            if (card != null && game.getState().getZone(this.sourceId) != Zone.BATTLEFIELD) {
                return card.getOwnerId().equals(playerId);
            }
        }
        return false;
    }

    public void setMayActivate(TargetController mayActivate) {
        this.mayActivate = mayActivate;
    }

    public UUID getActivatorId() {
        return this.activatorId;
    }

    public TimingRule getTiming() {
        return timing;
    }

    public void setTiming(TimingRule timing) {
        this.timing = timing;
    }

    @Override
    public void setCheckPlayableMode() {
        checkPlayableMode = true;
    }

    @Override
    public boolean isCheckPlayableMode() {
        return checkPlayableMode;
    }

}
