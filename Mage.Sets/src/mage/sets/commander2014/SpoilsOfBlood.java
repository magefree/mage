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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class SpoilsOfBlood extends CardImpl {

    public SpoilsOfBlood(UUID ownerId) {
        super(ownerId, 30, "Spoils of Blood", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "C14";

        this.color.setBlack(true);

        // Put an X/X black Horror creature token onto the battlefield, where X is the number of creatures that died this turn.
        this.getSpellAbility().addEffect(new SpoilsOfBloodEffect());
        this.getSpellAbility().addWatcher(new CreaturesDiedThisTurnWatcher());
    }

    public SpoilsOfBlood(final SpoilsOfBlood card) {
        super(card);
    }

    @Override
    public SpoilsOfBlood copy() {
        return new SpoilsOfBlood(this);
    }
}

class SpoilsOfBloodEffect extends OneShotEffect {

    public SpoilsOfBloodEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Put an X/X black Horror creature token onto the battlefield, where X is the number of creatures that died this turn";
    }

    public SpoilsOfBloodEffect(SpoilsOfBloodEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreaturesDiedThisTurnWatcher watcher = (CreaturesDiedThisTurnWatcher) game.getState().getWatchers().get("CreaturesDied");
            if (watcher != null) {
                new CreateTokenEffect(new SpoilsOfBloodHorrorToken(watcher.creaturesDiedThisTurn)).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public SpoilsOfBloodEffect copy() {
        return new SpoilsOfBloodEffect(this);
    }

}


class CreaturesDiedThisTurnWatcher extends Watcher {

    public int creaturesDiedThisTurn = 0;

    public CreaturesDiedThisTurnWatcher() {
        super("CreaturesDied", WatcherScope.GAME);
    }

    public CreaturesDiedThisTurnWatcher(final CreaturesDiedThisTurnWatcher watcher) {
        super(watcher);
    }

    @Override
    public CreaturesDiedThisTurnWatcher copy() {
        return new CreaturesDiedThisTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (mageObject != null && mageObject.getCardType().contains(CardType.CREATURE)) {
                creaturesDiedThisTurn++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creaturesDiedThisTurn = 0;
    }

}

class SpoilsOfBloodHorrorToken extends Token {
    
    public SpoilsOfBloodHorrorToken(int xValue) {
        super("Horror", "X/X black Horror creature token");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Horror");
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }
}
