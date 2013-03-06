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
package mage.sets.gatecrash;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class VizkopaConfessor extends CardImpl<VizkopaConfessor> {

    public VizkopaConfessor(UUID ownerId) {
        super(ownerId, 205, "Vizkopa Confessor", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());

        // When Vizkopa Confessor enters the battlefield, pay any amount of life. Target opponent reveals that many cards from his or her hand. You choose one of them and exile it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VizkopaConfessorEffect());
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
    }

    public VizkopaConfessor(final VizkopaConfessor card) {
        super(card);
    }

    @Override
    public VizkopaConfessor copy() {
        return new VizkopaConfessor(this);
    }
}

class VizkopaConfessorEffect extends OneShotEffect<VizkopaConfessorEffect> {

    public VizkopaConfessorEffect() {
        super(Outcome.Benefit);
        this.staticText = "pay any amount of life. Target opponent reveals that many cards from his or her hand. You choose one of them and exile it";
    }

    public VizkopaConfessorEffect(final VizkopaConfessorEffect effect) {
        super(effect);
    }

    @Override
    public VizkopaConfessorEffect copy() {
        return new VizkopaConfessorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && targetPlayer != null && sourceCard != null) {
            int payLife = controller.getAmount(0, controller.getLife(),"Pay how many life?", game);
            if (payLife > 0) {
                controller.loseLife(payLife, game);
                game.informPlayers(new StringBuilder(sourceCard.getName()).append(": ").append(controller.getName()).append(" pays ").append(payLife).append(" life").toString());

                Cards cardsInHand = new CardsImpl();
                cardsInHand.addAll(targetPlayer.getHand());

                int count = Math.min(cardsInHand.size(), payLife);

                TargetCard target = new TargetCard(count, Zone.HAND, new FilterCard());
                target.setRequired(true);
                Cards revealedCards = new CardsImpl();

                if (targetPlayer.chooseTarget(Outcome.Discard, cardsInHand, target, source, game)) {
                    List<UUID> targets = target.getTargets();
                    for (UUID targetId : targets) {
                        Card card = game.getCard(targetId);
                        if (card != null) {
                            revealedCards.add(card);
                        }
                    }
                }

                TargetCard targetInHand = new TargetCard(Zone.HAND, new FilterCard("card to exile"));
                targetInHand.setRequired(true);

                if (!revealedCards.isEmpty()) {
                    targetPlayer.revealCards("Vizkopa Confessor", revealedCards, game);
                    controller.chooseTarget(Outcome.Exile, revealedCards, targetInHand, source, game);
                    Card card = revealedCards.get(targetInHand.getFirstTarget(), game);
                    if (card != null) {
                        card.moveToExile(null, null, source.getSourceId(), game);
                        game.informPlayers(new StringBuilder(sourceCard.getName()).append(": Exiled card ").append(card.getName()).toString());
                    }
                }
                return true;
            }
        }
        return false;
    }
}
