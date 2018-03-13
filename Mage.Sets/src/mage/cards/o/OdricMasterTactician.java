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
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.watchers.common.ChooseBlockersRedundancyWatcher;

/**
 * @author noxx
 */
public class OdricMasterTactician extends CardImpl {

    public OdricMasterTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Odric, Master Tactician and at least three other creatures attack, you choose which creatures block this combat and how those creatures block.
        this.addAbility(new OdricMasterTacticianTriggeredAbility());
    }

    public OdricMasterTactician(final OdricMasterTactician card) {
        super(card);
    }

    @Override
    public OdricMasterTactician copy() {
        return new OdricMasterTactician(this);
    }
}

class OdricMasterTacticianTriggeredAbility extends TriggeredAbilityImpl {

    public OdricMasterTacticianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OdricMasterTacticianChooseBlockersEffect());
        this.addWatcher(new ChooseBlockersRedundancyWatcher());
        this.addEffect(new ChooseBlockersRedundancyWatcherIncrementEffect());
    }

    public OdricMasterTacticianTriggeredAbility(final OdricMasterTacticianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OdricMasterTacticianTriggeredAbility copy() {
        return new OdricMasterTacticianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 4 && game.getCombat().getAttackers().contains(this.sourceId);
    }

    private class ChooseBlockersRedundancyWatcherIncrementEffect extends OneShotEffect {
    
        ChooseBlockersRedundancyWatcherIncrementEffect() {
            super(Outcome.Neutral);
        }
    
        ChooseBlockersRedundancyWatcherIncrementEffect(final ChooseBlockersRedundancyWatcherIncrementEffect effect) {
            super(effect);
        }
    
        @Override
        public boolean apply(Game game, Ability source) {
            ChooseBlockersRedundancyWatcher watcher = (ChooseBlockersRedundancyWatcher) game.getState().getWatchers().get(ChooseBlockersRedundancyWatcher.class.getSimpleName());
            if (watcher != null) {
                watcher.increment();
                return true;
            }
            return false;
        }
    
        @Override
        public ChooseBlockersRedundancyWatcherIncrementEffect copy() {
            return new ChooseBlockersRedundancyWatcherIncrementEffect(this);
        }
    }
}

class OdricMasterTacticianChooseBlockersEffect extends ContinuousRuleModifyingEffectImpl {

    public OdricMasterTacticianChooseBlockersEffect() {
        super(Duration.EndOfCombat, Outcome.Benefit, false, false);
        staticText = "Whenever {this} and at least three other creatures attack, you choose which creatures block this combat and how those creatures block";
    }

    public OdricMasterTacticianChooseBlockersEffect(final OdricMasterTacticianChooseBlockersEffect effect) {
        super(effect);
    }

    @Override
    public OdricMasterTacticianChooseBlockersEffect copy() {
        return new OdricMasterTacticianChooseBlockersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ChooseBlockersRedundancyWatcher watcher = (ChooseBlockersRedundancyWatcher) game.getState().getWatchers().get(ChooseBlockersRedundancyWatcher.class.getSimpleName());
        watcher.decrement();
        watcher.copyCount--;
        if (watcher.copyCountApply > 0) {
            game.informPlayers(source.getSourceObject(game).getIdName() + " didn't apply");
            this.discard();
            return false;
        }
        watcher.copyCountApply = watcher.copyCount;
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, game);
            return true;
        }
        this.discard();
        return false;
    }
}
