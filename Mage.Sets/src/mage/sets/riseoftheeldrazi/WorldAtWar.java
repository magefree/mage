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
package mage.sets.riseoftheeldrazi;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.Set;
import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class WorldAtWar extends CardImpl<WorldAtWar> {

    public WorldAtWar(UUID ownerId) {
        super(ownerId, 172, "World at War", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        // After the first postcombat main phase this turn, there's an additional combat phase followed by an additional main phase. At the beginning of that combat, untap all creatures that attacked this turn.
        this.getSpellAbility().addEffect(new WorldAtWarEffect());

        // Rebound
        this.addAbility(new ReboundAbility());

        this.addWatcher(new AttackedThisTurnWatcher());
    }

    public WorldAtWar(final WorldAtWar card) {
        super(card);
    }

    @Override
    public WorldAtWar copy() {
        return new WorldAtWar(this);
    }
}

class WorldAtWarEffect extends OneShotEffect<WorldAtWarEffect> {

    public WorldAtWarEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "After the first postcombat main phase this turn, there's an additional combat phase followed by an additional main phase. At the beginning of that combat, untap all creatures that attacked this turn";
    }

    public WorldAtWarEffect(final WorldAtWarEffect effect) {
        super(effect);
    }

    @Override
    public WorldAtWarEffect copy() {
        return new WorldAtWarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // we can't add two turn modes at once, will add additional post combat on delayed trigger resolution
        TurnMod combat = new TurnMod(source.getControllerId(), Constants.TurnPhase.COMBAT, Constants.TurnPhase.POSTCOMBAT_MAIN, false);
        game.getState().getTurnMods().add(combat);
        UntapDelayedTriggeredAbility delayedTriggeredAbility = new UntapDelayedTriggeredAbility();
        delayedTriggeredAbility.setSourceId(source.getSourceId());
        delayedTriggeredAbility.setControllerId(source.getControllerId());
        delayedTriggeredAbility.setConnectedTurnMod(combat.getId());
        game.addDelayedTriggeredAbility(delayedTriggeredAbility);
        return true;
    }

}

class UntapDelayedTriggeredAbility extends DelayedTriggeredAbility<UntapDelayedTriggeredAbility> {

    private UUID connectedTurnMod;
    private boolean enabled;
    
    public UntapDelayedTriggeredAbility() {
        super(new UntapAttackingThisTurnEffect());
    }

    public UntapDelayedTriggeredAbility(UntapDelayedTriggeredAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
        this.enabled = ability.enabled;
    }

    @Override
    public UntapDelayedTriggeredAbility copy() {
        return new UntapDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PHASE_CHANGED && this.connectedTurnMod.equals(event.getSourceId())) {
            enabled = true;
            return false;
        }
        if (event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE && enabled) {
            // add additional post combat phase after that
            game.getState().getTurnMods().add(new TurnMod(getControllerId(), Constants.TurnPhase.POSTCOMBAT_MAIN, Constants.TurnPhase.COMBAT, false));
            enabled = false;
            return true;
        }
        return false;
    }

    public void setConnectedTurnMod(UUID connectedTurnMod) {
        this.connectedTurnMod = connectedTurnMod;
    }

    @Override
    public String getRule() {
        return "At the beginning of that combat, untap all creatures that attacked this turn";
    }
}

class UntapAttackingThisTurnEffect extends OneShotEffect<UntapAttackingThisTurnEffect> {

    public UntapAttackingThisTurnEffect() {
        super(Constants.Outcome.Benefit);
    }

    public UntapAttackingThisTurnEffect(final UntapAttackingThisTurnEffect effect) {
        super(effect);
    }

    @Override
    public UntapAttackingThisTurnEffect copy() {
        return new UntapAttackingThisTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get("AttackedThisTurn");
        if (watcher != null && watcher instanceof AttackedThisTurnWatcher) {
            Set<UUID> attackedThisTurn = ((AttackedThisTurnWatcher)watcher).getAttackedThisTurnCreatures();
            for (UUID uuid : attackedThisTurn) {
                Permanent permanent = game.getPermanent(uuid);
                if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                    permanent.untap(game);
                }
            }
        }
        return true;
    }

}


