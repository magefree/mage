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
package mage.sets.vintagemasters;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author emerald000
 */
public class CouncilsJudgment extends CardImpl {

    public CouncilsJudgment(UUID ownerId) {
        super(ownerId, 20, "Council's Judgment", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");
        this.expansionSetCode = "VMA";


        // Will of the council - Starting with you, each player votes for a nonland permanent you don't control. Exile each permanent with the most votes or tied for most votes.
        this.getSpellAbility().addEffect(new CouncilsJudgmentEffect());
    }

    public CouncilsJudgment(final CouncilsJudgment card) {
        super(card);
    }

    @Override
    public CouncilsJudgment copy() {
        return new CouncilsJudgment(this);
    }
}

class CouncilsJudgmentEffect extends OneShotEffect {
    
    CouncilsJudgmentEffect() {
        super(Outcome.Exile);
        this.staticText = "<i>Will of the council</i> — Starting with you, each player votes for a nonland permanent you don't control. Exile each permanent with the most votes or tied for most votes";
    }
    
    CouncilsJudgmentEffect(final CouncilsJudgmentEffect effect) {
        super(effect);
    }
    
    @Override
    public CouncilsJudgmentEffect copy() {
        return new CouncilsJudgmentEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Permanent, Integer> chosenCards = new HashMap<>(2);
            int maxCount = 0;
            FilterNonlandPermanent filter = new FilterNonlandPermanent("a nonland permanent " + controller.getLogName() + " doesn't control");
            filter.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
            //Players each choose a legal permanent
            PlayerList playerList = game.getState().getPlayerList().copy();
            while (!playerList.get().equals(controller.getId()) && controller.isInGame()) {
                playerList.getNext();
            }
            do {
                Player player = game.getPlayer(playerList.get());
                if (player != null && player.isInGame()) {
                    Target target = new TargetNonlandPermanent(filter);
                    target.setNotTarget(true);
                    if (player.choose(Outcome.Exile, target, source.getSourceId(), game)) {
                        Permanent permanent = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            if (chosenCards.containsKey(permanent)) {
                                int count = chosenCards.get(permanent) + 1;
                                if (count > maxCount) {
                                    maxCount = count;
                                }
                                chosenCards.put(permanent, count);
                            }
                            else {
                                if (maxCount == 0) {
                                    maxCount = 1;
                                }
                                chosenCards.put(permanent, 1);
                            }
                            game.informPlayers(player.getLogName() + " has chosen: " + permanent.getName());
                        }
                    }
                }
            } while (playerList.getNextInRange(controller, game) != controller && controller.isInGame());
            //Exile the card(s) with the most votes.
            for (Entry<Permanent, Integer> entry : chosenCards.entrySet()) {
                if (entry.getValue() == maxCount) {
                    Permanent permanent = entry.getKey();
                    controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
                }
            }
            return true;
        }
        return false;
    }
}
