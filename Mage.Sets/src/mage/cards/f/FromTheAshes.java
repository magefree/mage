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
package mage.cards.f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class FromTheAshes extends CardImpl {

    public FromTheAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Destroy all nonbasic lands. For each land destroyed this way, its controller may search his or her library for a basic land card and put it onto the battlefield. Then each player who searched his or her library this way shuffles it.
        this.getSpellAbility().addEffect(new FromTheAshesEffect());
    }

    public FromTheAshes(final FromTheAshes card) {
        super(card);
    }

    @Override
    public FromTheAshes copy() {
        return new FromTheAshes(this);
    }
}

class FromTheAshesEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = FilterLandPermanent.nonbasicLands();

    public FromTheAshesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy all nonbasic lands. For each land destroyed this way, its controller may search his or her library for a basic land card and put it onto the battlefield. Then each player who searched his or her library this way shuffles it";
    }

    public FromTheAshesEffect(final FromTheAshesEffect effect) {
        super(effect);
    }

    @Override
    public FromTheAshesEffect copy() {
        return new FromTheAshesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> playerAmount = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int amount = 0;
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        amount++;
                        permanent.destroy(source.getSourceId(), game, false);
                    }
                    playerAmount.put(playerId, amount);
                }
            }
            for(Map.Entry<UUID, Integer> entry : playerAmount.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, entry.getValue(), new FilterBasicLandCard());
                    if (player.searchLibrary(target, game)) {
                        if (target.getTargets().size() > 0) {
                            for (UUID cardId: target.getTargets()) {
                                Card card = player.getLibrary().getCard(cardId, game);
                                if (card != null) {
                                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), player.getId(), false);
                                }
                            }
                        }
                    }
                    player.shuffleLibrary(source, game);
                }

            }
            return true;
        }
        return false;
    }
}
