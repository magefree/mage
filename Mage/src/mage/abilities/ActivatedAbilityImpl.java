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

import mage.Constants.AbilityType;
import mage.Constants.TimingRule;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.cards.Card;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.target.Target;

import java.util.UUID;
import mage.abilities.costs.mana.KickerManaCost;
import mage.abilities.keyword.MultikickerAbility;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ActivatedAbilityImpl<T extends ActivatedAbilityImpl<T>> extends AbilityImpl<T> implements ActivatedAbility {

    protected TimingRule timing = TimingRule.INSTANT;

    protected ActivatedAbilityImpl(AbilityType abilityType, Zone zone) {
        super(abilityType, zone);
    }

    public ActivatedAbilityImpl(ActivatedAbilityImpl ability) {
        super(ability);
        timing = ability.timing;
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
        if (cost != null)
            this.addManaCost(cost);
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, ManaCosts cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effects != null) {
            for (Effect effect: effects) {
                this.addEffect(effect);
            }
        }
        if (cost != null)
            this.addManaCost(cost);
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, Cost cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
        if (cost != null) {
            if (cost instanceof PhyrexianManaCost) {
                this.addManaCost((PhyrexianManaCost)cost);
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
            for (Cost cost: costs) {
                this.addCost(cost);
            }
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, Cost cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effects != null) {
            for (Effect effect: effects) {
                this.addEffect(effect);
            }
        }
        if (cost != null)
            this.addCost(cost);
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, Costs<Cost> costs) {
        super(AbilityType.ACTIVATED, zone);
        for (Effect effect: effects) {
            if (effect != null) {
                this.addEffect(effect);
            }
        }
        if (costs != null) {
            for (Cost cost: costs) {
                this.addCost(cost);
            }
        }
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        //20091005 - 602.2
        if (!controlsAbility(playerId, game))
            return false;
        //20091005 - 602.5d/602.5e
        if (timing == TimingRule.INSTANT || game.canPlaySorcery(playerId)) {
            if (costs.canPay(sourceId, controllerId, game) && canChooseTarget(game)) {
                return true;
            }
        }
        return false;
    }

    protected boolean controlsAbility(UUID playerId, Game game) {
        if (this.controllerId != null && this.controllerId.equals(playerId))
            return true;
        else {
            Card card = (Card)game.getObject(this.sourceId);
            if (card != null && game.getState().getZone(this.sourceId) != Zone.BATTLEFIELD)
                return card.getOwnerId().equals(playerId);
        }
        return false;
    }

    @Override
    public String getActivatedMessage(Game game) {
        if (game.isSimulation()) {
            return "";
        }
        return " activates ability from " + getMessageText(game);
    }

    protected String getMessageText(Game game) {
        StringBuilder sb = new StringBuilder();
        MageObject object = game.getObject(this.sourceId);
        if (object != null) {
            if (object instanceof StackAbility) {
                Card card = game.getCard(((StackAbility) object).getSourceId());
                if (card != null) {
                    sb.append(card.getName());
                } else {
                    sb.append(object.getName());
                }
            } else {
                if (object instanceof Spell) {
                    Spell<?> spell = (Spell<?>) object;
                    String castText = spell.getSpellAbility().toString();
                    sb.append((castText.startsWith("Cast ") ? castText.substring(5):castText));
                    if (spell.getFromZone() == Zone.GRAVEYARD) {
                        sb.append(" from graveyard");
                    }
                    sb.append(getKickerText(game, spell));
                } else {
                    sb.append(object.getName());
                }
            }
        } else {
            sb.append("unknown");
        }
        if (getTargets().size() > 0) {
            sb.append(" targeting ");
            for (Target target: getTargets()) {
                sb.append(target.getTargetedName(game));
            }
        }
        return sb.toString();
    }

    String getKickerText(Game game, Spell spell) {
        StringBuilder sb = new StringBuilder();
        int numberPaid = 0;
        for (Object cost : spell.getSpellAbility().getOptionalCosts()) {
            if (cost instanceof KickerManaCost) {
                if (((KickerManaCost) cost).isPaid()) {
                    if (numberPaid == 0) {
                        sb.append(" with ").append(((KickerManaCost)cost).getText(true));
                    } else {
                        sb.append(" and ").append(((KickerManaCost)cost).getText(true));
                    }
                    ++numberPaid;
                }
            }
        }
        if (numberPaid > 0) {
            sb.append(" kicker");
        }
        // Multikicker
        int multikickerCount = 0;
        Card card = game.getCard(this.getSourceId());
        if (card != null) {
            for (Ability ability : card.getAbilities()) {
                if (ability instanceof MultikickerAbility) {
                    multikickerCount = ((MultikickerAbility)ability).getActivateCount();
                    }
                }
            }
        if (multikickerCount > 0) {
            sb.append(" with ").append(multikickerCount).append(multikickerCount > 1? " times":" time").append(" multikicker");
        }
        return sb.toString();
    }
}
