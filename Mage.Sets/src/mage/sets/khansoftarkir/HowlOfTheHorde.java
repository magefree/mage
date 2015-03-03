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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author emerald000
 */
public class HowlOfTheHorde extends CardImpl {

    public HowlOfTheHorde(UUID ownerId) {
        super(ownerId, 112, "Howl of the Horde", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "KTK";

        this.color.setRed(true);

        // When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy. 
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new HowlOfTheHordeDelayedTriggeredAbility());
        effect.setText("When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.");
        this.getSpellAbility().addEffect(effect);
        
        // <i>Raid</i> - If you attacked with a creature this turn, when you cast your next instant or sorcery spell this turn, copy that spell an additional time. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateDelayedTriggeredAbilityEffect(new HowlOfTheHordeDelayedTriggeredAbility()), RaidCondition.getInstance(), "<br><br><i>Raid</i> - If you attacked with a creature this turn, when you cast your next instant or sorcery spell this turn, copy that spell an additional time. You may choose new targets for the copy."));
        this.getSpellAbility().addWatcher(new PlayerAttackedWatcher());
    }

    public HowlOfTheHorde(final HowlOfTheHorde card) {
        super(card);
    }

    @Override
    public HowlOfTheHorde copy() {
        return new HowlOfTheHorde(this);
    }
}

class HowlOfTheHordeDelayedTriggeredAbility extends DelayedTriggeredAbility {
    
    HowlOfTheHordeDelayedTriggeredAbility() {
        super(new CopyTargetSpellEffect(), Duration.EndOfTurn);
    }

    HowlOfTheHordeDelayedTriggeredAbility(final HowlOfTheHordeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HowlOfTheHordeDelayedTriggeredAbility copy() {
        return new HowlOfTheHordeDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
         if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && (spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY))) {
                for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.";
    }
}
