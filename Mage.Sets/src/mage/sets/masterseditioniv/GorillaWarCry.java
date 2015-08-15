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
package mage.sets.masterseditioniv;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public class GorillaWarCry extends CardImpl {

    public GorillaWarCry(UUID ownerId) {
        super(ownerId, 124, "Gorilla War Cry", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ME4";

        // Cast Gorilla War Cry only during combat before blockers are declared.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new GorillaWarCryRuleModifyingEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        
        // All creatures gain menace until end of turn. <i>(They can't be blocked except by two or more creatures.)</i>
        Effect effect = new GainAbilityAllEffect(new MenaceAbility(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("All creatures gain menace until end of turn. <i>(They can't be blocked except by two or more creatures.)</i>");
        this.getSpellAbility().addEffect(effect);
        
        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
    }

    public GorillaWarCry(final GorillaWarCry card) {
        super(card);
    }

    @Override
    public GorillaWarCry copy() {
        return new GorillaWarCry(this);
    }
}

class GorillaWarCryRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    GorillaWarCryRuleModifyingEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only during combat before blockers are declared";
    }

    GorillaWarCryRuleModifyingEffect(final GorillaWarCryRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.CAST_SPELL);
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            return !TurnPhase.COMBAT.equals(game.getTurn().getPhaseType()) ||
                    game.getStep().getType().equals(PhaseStep.DECLARE_BLOCKERS) ||
                    game.getStep().getType().equals(PhaseStep.FIRST_COMBAT_DAMAGE) ||
                    game.getStep().getType().equals(PhaseStep.COMBAT_DAMAGE) ||
                    game.getStep().getType().equals(PhaseStep.END_COMBAT);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GorillaWarCryRuleModifyingEffect copy() {
        return new GorillaWarCryRuleModifyingEffect(this);
    }
}

