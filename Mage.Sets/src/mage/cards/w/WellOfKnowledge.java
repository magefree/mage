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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class WellOfKnowledge extends CardImpl {

    public WellOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}: Draw a card. Any player may activate this ability but only during his or her draw step.
        this.addAbility(new WellOfKnowledgeConditionalActivatedAbility());

    }

    public WellOfKnowledge(final WellOfKnowledge card) {
        super(card);
    }

    @Override
    public WellOfKnowledge copy() {
        return new WellOfKnowledge(this);
    }
}

class WellOfKnowledgeConditionalActivatedAbility extends ActivatedAbilityImpl {

    private final Condition condition;

    public WellOfKnowledgeConditionalActivatedAbility() {
        super(Zone.BATTLEFIELD, new WellOfKnowledgeEffect(), new GenericManaCost(2));
        condition = new IsStepCondition(PhaseStep.DRAW, false);
    }

    public WellOfKnowledgeConditionalActivatedAbility(final WellOfKnowledgeConditionalActivatedAbility ability) {
        super(ability);
        this.condition = ability.condition;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return new Effects();
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (condition.apply(game, this)
                && costs.canPay(this, sourceId, playerId, game)
                && game.getActivePlayerId().equals(playerId)) {
            this.activatorId = playerId;
            return true;
        }
        return false;

    }

    @Override
    public WellOfKnowledgeConditionalActivatedAbility copy() {
        return new WellOfKnowledgeConditionalActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return "{2}: Draw a card. Any player may activate this ability but only during his or her draw step.";
    }
}

class WellOfKnowledgeEffect extends OneShotEffect {

    public WellOfKnowledgeEffect() {
        super(Outcome.DrawCard);
    }

    public WellOfKnowledgeEffect(final WellOfKnowledgeEffect effect) {
        super(effect);
    }

    @Override
    public WellOfKnowledgeEffect copy() {
        return new WellOfKnowledgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof ActivatedAbilityImpl) {
            Player activator = game.getPlayer(((ActivatedAbilityImpl) source).getActivatorId());
            if (activator != null) {
                activator.drawCards(1, game);
                return true;
            }

        }
        return false;
    }
}
