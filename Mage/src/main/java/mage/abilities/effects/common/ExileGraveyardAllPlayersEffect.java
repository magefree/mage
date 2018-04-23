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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Jgod
 */
public class ExileGraveyardAllPlayersEffect extends OneShotEffect {

    private final FilterCard filter;
    private final TargetController targetController;

    public ExileGraveyardAllPlayersEffect() {
        this(StaticFilters.FILTER_CARD_CARDS);
    }

    public ExileGraveyardAllPlayersEffect(FilterCard filter) {
        this(filter, TargetController.ANY);
    }

    public ExileGraveyardAllPlayersEffect(FilterCard filter, TargetController targetController) {
        super(Outcome.Exile);
        this.filter = filter;
        this.targetController = targetController;
        staticText = "exile all " + filter.getMessage() + " from all "
                + (targetController.equals(TargetController.OPPONENT) ? "opponents' " : "")
                + "graveyards";
    }

    public ExileGraveyardAllPlayersEffect(final ExileGraveyardAllPlayersEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.targetController = effect.targetController;
    }

    @Override
    public ExileGraveyardAllPlayersEffect copy() {
        return new ExileGraveyardAllPlayersEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards toExile = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (TargetController.OPPONENT.equals(targetController) && playerId.equals(source.getControllerId())) {
                continue;
            }
            Player player = game.getPlayer(playerId);
            if (player != null) {
                toExile.addAll(player.getGraveyard());
            }
        }
        controller.moveCards(toExile, Zone.EXILED, source, game);
        return true;
    }
}
