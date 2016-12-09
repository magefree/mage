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
package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author JRHerlehy
 */
public class CapitalPunishment extends CardImpl {

    public CapitalPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");
        

        // <i>Council's dilemma</i> &mdash; Starting with you, each player votes for death or taxes. Each opponent sacrifices a creature for each death vote and discards a card for each taxes vote.
        this.getSpellAbility().addEffect(new CapitalPunishmentDilemmaEffect());
    }

    public CapitalPunishment(final CapitalPunishment card) {
        super(card);
    }

    @Override
    public CapitalPunishment copy() {
        return new CapitalPunishment(this);
    }
}

class CapitalPunishmentDilemmaEffect extends OneShotEffect {

    public CapitalPunishmentDilemmaEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Council's dilemma</i> — Starting with you, each player votes for death or taxes. Each opponent sacrifices a creature for each death vote and discards a card for each taxes vote";
    }

    public CapitalPunishmentDilemmaEffect(final CapitalPunishmentDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        //If no controller, exit out here and do not vote.
        if (controller == null) return false;

        int deathCount = 0, taxesCount = 0;

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseUse(Outcome.Detriment, "Choose death?", source, game)) {
                    deathCount++;
                    game.informPlayers(player.getName() + " has voted for death");
                } else {
                    taxesCount++;
                    game.informPlayers(player.getName() + " has voted for taxes");
                }
            }
        }

        if (deathCount > 0) {
            Effect sacraficeEffect = new SacrificeOpponentsEffect(deathCount, new FilterControlledCreaturePermanent());
            sacraficeEffect.apply(game, source);
        }

        if (taxesCount > 0) {
            Effect discardEffect = new DiscardEachPlayerEffect(new StaticValue(taxesCount), false, TargetController.OPPONENT);
            discardEffect.apply(game, source);
        }

        return true;
    }

    @Override
    public CapitalPunishmentDilemmaEffect copy() {
        return new CapitalPunishmentDilemmaEffect(this);
    }
}
