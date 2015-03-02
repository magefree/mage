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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author LevelX2
 */
public class FirstResponse extends CardImpl {

    public FirstResponse(UUID ownerId) {
        super(ownerId, 12, "First Response", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "M15";

        this.color.setWhite(true);

        // At the beginning of each upkeep, if you lost life last turn, put a 1/1 white Soldier creature token onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new FirstResponseEffect(), TargetController.ANY, false), new PlayerLostLifeWatcher());

    }

    public FirstResponse(final FirstResponse card) {
        super(card);
    }

    @Override
    public FirstResponse copy() {
        return new FirstResponse(this);
    }
}

class FirstResponseEffect extends OneShotEffect {

    public FirstResponseEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "if you lost life last turn, put a 1/1 white Soldier creature token onto the battlefield";
    }

    public FirstResponseEffect(final FirstResponseEffect effect) {
        super(effect);
    }

    @Override
    public FirstResponseEffect copy() {
        return new FirstResponseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get("PlayerLostLifeWatcher");
        if (watcher != null) {
            if (watcher.getLiveLostLastTurn(source.getControllerId()) > 0) {
                return new CreateTokenEffect(new SoldierToken("M15")).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
