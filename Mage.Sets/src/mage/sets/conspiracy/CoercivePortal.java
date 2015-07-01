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
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class CoercivePortal extends CardImpl {

    public CoercivePortal(UUID ownerId) {
        super(ownerId, 56, "Coercive Portal", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "CNS";

        // Will of the council - At the beginning of your upkeep, starting with you, each player votes for carnage or homage. If carnage gets more votes, sacrifice Coercive Portal and destroy all nonland permanents. If homage gets more votes or the vote is tied, draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CoercivePortalEffect(), TargetController.YOU, false));
    }

    public CoercivePortal(final CoercivePortal card) {
        super(card);
    }

    @Override
    public CoercivePortal copy() {
        return new CoercivePortal(this);
    }
}

class CoercivePortalEffect extends OneShotEffect {
    
    CoercivePortalEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> - At the beginning of your upkeep, starting with you, each player votes for carnage or homage. If carnage gets more votes, sacrifice Coercive Portal and destroy all nonland permanents. If homage gets more votes or the vote is tied, draw a card";
    }
    
    CoercivePortalEffect(final CoercivePortalEffect effect) {
        super(effect);
    }
    
    @Override
    public CoercivePortalEffect copy() {
        return new CoercivePortalEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int carnageCount = 0;
            int homageCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.DestroyPermanent, "Choose carnage?", source, game)) {
                        carnageCount++;
                        game.informPlayers(player.getLogName() + " has chosen: carnage");
                    }
                    else {
                        homageCount++;
                        game.informPlayers(player.getLogName() + " has chosen: homage");
                    }
                }
            }
            if (carnageCount > homageCount) {
                new SacrificeSourceEffect().apply(game, source);
                new CoercivePortalDestroyEffect().apply(game, source);                
            } else {
                controller.drawCards(1, game);
            }
            return true;
        }
        return false;
    }
}

class CoercivePortalDestroyEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterNonlandPermanent();

    public CoercivePortalDestroyEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "sacrifice Coercive Portal and destroy all nonland permanents";
    }

    public CoercivePortalDestroyEffect(final CoercivePortalDestroyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.destroy(source.getSourceId(), game, false);
        }
        return true;
    }

    @Override
    public CoercivePortalDestroyEffect copy() {
        return new CoercivePortalDestroyEffect(this);
    }

}