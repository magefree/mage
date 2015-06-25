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
package mage.sets.conspiracy;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class TyrantsChoice extends CardImpl {

    public TyrantsChoice(UUID ownerId) {
        super(ownerId, 30, "Tyrant's Choice", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "CNS";

        // Will of the council - Starting with you, each player votes for death or torture. If death gets more votes, each opponent sacrifices a creature. If torture gets more votes or the vote is tied, each opponent loses 4 life.
        this.getSpellAbility().addEffect(new TyrantsChoiceEffect());
    }

    public TyrantsChoice(final TyrantsChoice card) {
        super(card);
    }

    @Override
    public TyrantsChoice copy() {
        return new TyrantsChoice(this);
    }
}

class TyrantsChoiceEffect extends OneShotEffect {
    
    TyrantsChoiceEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> - Starting with you, each player votes for death or torture. If death gets more votes, each opponent sacrifices a creature. If torture gets more votes or the vote is tied, each opponent loses 4 life";
    }
    
    TyrantsChoiceEffect(final TyrantsChoiceEffect effect) {
        super(effect);
    }
    
    @Override
    public TyrantsChoiceEffect copy() {
        return new TyrantsChoiceEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int deathCount = 0;
            int tortureCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.Sacrifice, "Choose death?", game)) {
                        deathCount++;
                        game.informPlayers(player.getLogName() + " has chosen: death");
                    }
                    else {
                        tortureCount++;
                        game.informPlayers(player.getLogName() + " has chosen: torture");
                    }
                }
            }
            if (deathCount > tortureCount) {
                new SacrificeOpponentsEffect(new FilterControlledCreaturePermanent("a creature")).apply(game, source);
            } else {
                new TyrantsChoiceLoseLifeEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }
}

class TyrantsChoiceLoseLifeEffect extends OneShotEffect {

    public TyrantsChoiceLoseLifeEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 2 life";
    }

    public TyrantsChoiceLoseLifeEffect(final TyrantsChoiceLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            game.getPlayer(opponentId).loseLife(4, game);
        }
        return true;
    }

    @Override
    public TyrantsChoiceLoseLifeEffect copy() {
        return new TyrantsChoiceLoseLifeEffect(this);
    }

}