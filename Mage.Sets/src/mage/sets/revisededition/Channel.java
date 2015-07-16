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
package mage.sets.revisededition;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpecialAction;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateSpecialActionEffect;
import mage.abilities.effects.common.RemoveSpecialActionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author emerald000
 */
public class Channel extends CardImpl {

    public Channel(UUID ownerId) {
        super(ownerId, 95, "Channel", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{G}{G}");
        this.expansionSetCode = "3ED";

        // Until end of turn, any time you could activate a mana ability, you may pay 1 life. If you do, add {1} to your mana pool.
        this.getSpellAbility().addEffect(new ChannelEffect());
    }

    public Channel(final Channel card) {
        super(card);
    }

    @Override
    public Channel copy() {
        return new Channel(this);
    }
}

class ChannelEffect extends OneShotEffect {
    
    ChannelEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Until end of turn, any time you could activate a mana ability, you may pay 1 life. If you do, add {1} to your mana pool";
    }
    
    ChannelEffect(final ChannelEffect effect) {
        super(effect);
    }
    
    @Override
    public ChannelEffect copy() {
        return new ChannelEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        SpecialAction specialAction = new ChannelSpecialAction();
        new CreateSpecialActionEffect(specialAction).apply(game, source);
        
        // Create a hidden delayed triggered ability to remove the special action at end of turn.
        new CreateDelayedTriggeredAbilityEffect(new ChannelDelayedTriggeredAbility(specialAction.getId()), false).apply(game, source);
        return true;
    }
}

class ChannelSpecialAction extends SpecialAction {

    ChannelSpecialAction() {
        super();
        this.addCost(new PayLifeCost(1));
        this.addEffect(new BasicManaEffect(Mana.ColorlessMana));
    }

    ChannelSpecialAction(final ChannelSpecialAction ability) {
        super(ability);
    }

    @Override
    public ChannelSpecialAction copy() {
        return new ChannelSpecialAction(this);
    }
}

class ChannelDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChannelDelayedTriggeredAbility(UUID specialActionId) {
        super(new RemoveSpecialActionEffect(specialActionId), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    ChannelDelayedTriggeredAbility(ChannelDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChannelDelayedTriggeredAbility copy() {
        return new ChannelDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CLEANUP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
