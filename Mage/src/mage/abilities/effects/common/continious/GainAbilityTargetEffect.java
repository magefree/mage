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

package mage.abilities.effects.common.continious;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.UUID;
import mage.cards.Card;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityTargetEffect extends ContinuousEffectImpl<GainAbilityTargetEffect> {

    protected Ability ability;
    // shall a card gain the ability (otherwise permanent)
    private boolean onCard;

    public GainAbilityTargetEffect(Ability ability, Duration duration) {
        this(ability, duration, null);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule) {
        this(ability, duration, rule, false);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule, boolean onCard) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA,
                ability.getEffects().size() > 0 ? ability.getEffects().get(0).getOutcome() : Outcome.AddAbility);
        this.ability = ability;
        staticText = rule;
        this.onCard = onCard;
    }

    public GainAbilityTargetEffect(final GainAbilityTargetEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.onCard = effect.onCard;
    }

    @Override
    public GainAbilityTargetEffect copy() {
        return new GainAbilityTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        targetPointer.init(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        if (onCard) {
            for (UUID cardId : targetPointer.getTargets(game, source)) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    card.addAbility(ability);
                    affectedTargets++;
                }
            }
            if (duration.equals(Duration.OneUse)) {
                discard();
            }
        } else {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    permanent.addAbility(ability, source.getSourceId(), game);
                    affectedTargets++;
                }
            }
        }
        if (duration.equals(Duration.Custom) && affectedTargets == 0) {
            this.discard();
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if(target.getMaxNumberOfTargets() > 1){
            if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                sb.append("Up to");
            }
            sb.append(target.getMaxNumberOfTargets()).append(" target ").append(target.getTargetName()).append(" gain ");
        } else {
            if (!target.getTargetName().startsWith("another")) {
                sb.append("Target ");
            }
            sb.append(target.getTargetName()).append(" gains ");

        }
        sb.append(ability.getRule()).append(" ").append(duration.toString());
        return sb.toString();
    }

}
