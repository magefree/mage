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
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TombspawnZombieToken;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public class TombstoneStairwell extends CardImpl {

    public TombstoneStairwell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        addSuperType(SuperType.WORLD);

        // Cumulative upkeep-Pay {1}{B}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}{B}")));

        // At the beginning of each upkeep, if Tombstone Stairwell is on the battlefield, each player creates a 2/2 black Zombie creature token with haste named Tombspawn for each creature card in their graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TombstoneStairwellCreateTokenEffect(), TargetController.ANY, false));

        // At the beginning of each end step or when Tombstone Stairwell leaves the battlefield, destroy all tokens created with Tombstone Stairwell. They can't be regenerated.
        this.addAbility(new TombstoneStairwellTriggeredAbility());
    }

    public TombstoneStairwell(final TombstoneStairwell card) {
        super(card);
    }

    @Override
    public TombstoneStairwell copy() {
        return new TombstoneStairwell(this);
    }
}

class TombstoneStairwellCreateTokenEffect extends OneShotEffect {

    TombstoneStairwellCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "if {this} is on the battlefield, each player creates a 2/2 black Zombie creature token with haste named Tombspawn for each creature card in their graveyard";
    }

    TombstoneStairwellCreateTokenEffect(final TombstoneStairwellCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public TombstoneStairwellCreateTokenEffect copy() {
        return new TombstoneStairwellCreateTokenEffect(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Game game, Ability source) {
        Token token = new TombspawnZombieToken();
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (game.getPlayer(source.getControllerId()) != null && activePlayer != null && permanent != null) {
            Object object = game.getState().getValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game));
            Set<UUID> tokensCreated;
            if (object != null) {
                tokensCreated = (Set<UUID>) object;
            } else {
                tokensCreated = new HashSet<>();
            }
            
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                int creatureCardsInGraveyard = player.getGraveyard().count(new FilterCreatureCard(), source.getControllerId(), source.getSourceId(), game);
                token.putOntoBattlefield(creatureCardsInGraveyard, game, source.getSourceId(), playerId);   
                for (UUID tokenId : token.getLastAddedTokenIds()) {
                    tokensCreated.add(tokenId);
                }
            }
            game.getState().setValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game), tokensCreated);
            return true;
        }
        return false;
    }
}

class TombstoneStairwellTriggeredAbility extends TriggeredAbilityImpl {

    TombstoneStairwellTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TombstoneStairwellDestroyEffect(), false);
    }

    TombstoneStairwellTriggeredAbility(TombstoneStairwellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.END_TURN_STEP_PRE 
                || event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.END_TURN_STEP_PRE) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(sourceId);
            if (permanent != null) {
                for (Effect effect : this.getEffects()) {
                    if (effect instanceof TombstoneStairwellDestroyEffect) {
                        ((TombstoneStairwellDestroyEffect) effect).setCardZoneString(CardUtil.getCardZoneString("_tokensCreated", this.getSourceId(), game, false));
                    }
                    if (getTargets().isEmpty()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            }
        }
        else if (event.getType() == EventType.ZONE_CHANGE) {
            if (event.getTargetId().equals(this.getSourceId())) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                    for (Effect effect : this.getEffects()) {
                        if (effect instanceof TombstoneStairwellDestroyEffect) {
                            ((TombstoneStairwellDestroyEffect) effect).setCardZoneString(CardUtil.getCardZoneString("_tokensCreated", this.getSourceId(), game, true));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TombstoneStairwellTriggeredAbility copy() {
        return new TombstoneStairwellTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step or when {this} leaves the battlefield, " + modes.getText();
    }
}

class TombstoneStairwellDestroyEffect extends OneShotEffect {

    private String cardZoneString;

    TombstoneStairwellDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy all tokens created with {this}. They can't be regenerated";
    }

    TombstoneStairwellDestroyEffect(final TombstoneStairwellDestroyEffect effect) {
        super(effect);
        this.cardZoneString = effect.cardZoneString;
    }

    @Override
    public TombstoneStairwellDestroyEffect copy() {
        return new TombstoneStairwellDestroyEffect(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(cardZoneString);
        if (object != null) {
            Set<UUID> tokensCreated = (Set<UUID>) object;
            for (UUID tokenId : tokensCreated) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    token.destroy(source.getSourceId(), game, true);
                }
            }
        }
        return true;
    }

    public void setCardZoneString(String cardZoneString) {
        this.cardZoneString = cardZoneString;
    }
}
