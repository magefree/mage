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
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class ScytheSpecter extends CardImpl {

    public ScytheSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add("Specter");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Scythe Specter deals combat damage to a player, each opponent discards a card. Each player who discarded a card with the highest converted mana cost among cards discarded this way loses life equal to that converted mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ScytheSpecterEffect(), false));

    }

    public ScytheSpecter(final ScytheSpecter card) {
        super(card);
    }

    @Override
    public ScytheSpecter copy() {
        return new ScytheSpecter(this);
    }
}

class ScytheSpecterEffect extends OneShotEffect {

    public ScytheSpecterEffect() {
        super(Outcome.Discard);
        this.staticText = "each opponent discards a card. Each player who discarded a card with the highest converted mana cost among cards discarded this way loses life equal to that converted mana cost";
    }

    public ScytheSpecterEffect(final ScytheSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Card> cardDiscarded = new HashMap<>();
        Map<UUID, Integer> discardedCheck = new HashMap<>();
        Integer highestCMC = 0;
        Integer currentCMC = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    Target target = new TargetDiscard(new FilterCard(), playerId);
                    opponent.chooseTarget(Outcome.Discard, target, source, game);
                    Card targetCard = game.getCard(target.getFirstTarget());
                    if (targetCard != null) {
                        if (targetCard.isSplitCard()) { //check Split Cards
                            if (targetCard.getSecondCardFace().getConvertedManaCost() < targetCard.getConvertedManaCost()) {
                                currentCMC = targetCard.getConvertedManaCost();
                            }
                        } else {
                            currentCMC = targetCard.getConvertedManaCost();
                        }
                        if (highestCMC <= currentCMC) {
                            highestCMC = currentCMC;
                        }
                        cardDiscarded.put(playerId, targetCard);
                    }
                }
            }
            for (UUID opponentId : cardDiscarded.keySet()) {//discard must happen simultaneously
                Player player = game.getPlayer(opponentId);
                if (player != null
                        && player.discard(cardDiscarded.get(opponentId), source, game)) {
                    discardedCheck.put(opponentId, 1);//note that a card was discarded
                }
            }

            for (UUID playerId : game.getOpponents(controller.getId())) {//lose life equal to CMC
                if (cardDiscarded.get(playerId).getConvertedManaCost() == highestCMC) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null
                            && discardedCheck.get(playerId) == 1) {//check that card was discarded
                        opponent.loseLife(highestCMC, game, false);
                    }
                }
            }
            cardDiscarded.clear();
            discardedCheck.clear();
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new ScytheSpecterEffect(this);
    }
}
