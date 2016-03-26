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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author fireshoes
 */
public class Triskaidekaphobia extends CardImpl {

    public Triskaidekaphobia(UUID ownerId) {
        super(ownerId, 141, "Triskaidekaphobia", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "SOI";

        // At the beginning of your upkeep, choose one - Each player with exactly 13 life loses the game, then each player gains 1 life.
        // Each player with exactly 13 life loses the game, then each player loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new TriskaidekaphobiaGainLifeEffect(), TargetController.YOU, false);
        Mode mode = new Mode();
        mode.getEffects().add(new TriskaidekaphobiaLoseLifeEffect());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public Triskaidekaphobia(final Triskaidekaphobia card) {
        super(card);
    }

    @Override
    public Triskaidekaphobia copy() {
        return new Triskaidekaphobia(this);
    }
}

class TriskaidekaphobiaGainLifeEffect extends OneShotEffect {

    public TriskaidekaphobiaGainLifeEffect() {
        super(Outcome.Neutral);
        this.staticText = "Each player with exactly 13 life loses the game, then each player gains 1 life";
    }

    public TriskaidekaphobiaGainLifeEffect(final TriskaidekaphobiaGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public TriskaidekaphobiaGainLifeEffect copy() {
        return new TriskaidekaphobiaGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int life;
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID pid : playerList) {
            Player player = game.getPlayer(pid);
            if (player != null) {
                life = player.getLife();
                if (life == 13) {
                    player.lost(game);
                }
            }
        }
        for (UUID pid : playerList) {
            Player player = game.getPlayer(pid);
            if (player != null) {
                player.gainLife(1, game);
            }
        }
        return true;
    }
}

class TriskaidekaphobiaLoseLifeEffect extends OneShotEffect {

    public TriskaidekaphobiaLoseLifeEffect() {
        super(Outcome.Neutral);
        this.staticText = "Each player with exactly 13 life loses the game, then each player loses 1 life";
    }

    public TriskaidekaphobiaLoseLifeEffect(final TriskaidekaphobiaLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public TriskaidekaphobiaLoseLifeEffect copy() {
        return new TriskaidekaphobiaLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int life;
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID pid : playerList) {
            Player player = game.getPlayer(pid);
            if (player != null) {
                life = player.getLife();
                if (life == 13) {
                    player.lost(game);
                }
            }
        }
        for (UUID pid : playerList) {
            Player player = game.getPlayer(pid);
            if (player != null) {
                player.loseLife(1, game);
            }
        }
        return true;
    }
}
