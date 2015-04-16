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
package mage.sets.tempestremastered;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class Necrologia extends CardImpl {

    public Necrologia(UUID ownerId) {
        super(ownerId, 110, "Necrologia", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");
        this.expansionSetCode = "TPR";

        // Cast Necrologia only during your end step.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new NecrologiaTimingEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        
        // As an additional cost to cast to Necrologia, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));
        
        // Draw X cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new GetXValue()));
    }

    public Necrologia(final Necrologia card) {
        super(card);
    }

    @Override
    public Necrologia copy() {
        return new Necrologia(this);
    }
}

class NecrologiaTimingEffect extends ContinuousRuleModifyingEffectImpl {
    NecrologiaTimingEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only during your end step";
    }

    NecrologiaTimingEffect(final NecrologiaTimingEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.CAST_SPELL);
    }

    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            if (!game.getActivePlayerId().equals(source.getControllerId()) || !PhaseStep.END_TURN.equals(game.getTurn().getStepType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public NecrologiaTimingEffect copy() {
        return new NecrologiaTimingEffect(this);
    }
}
