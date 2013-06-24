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
package mage.sets.morningtide;

import java.util.List;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.Card;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class NogginWhack extends CardImpl<NogginWhack> {

    public NogginWhack(UUID ownerId) {
        super(ownerId, 70, "Noggin Whack", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "MOR";
        this.supertype.add("Tribal");
        this.subtype.add("Rogue");

        this.color.setBlack(true);

        // Prowl {1}{B}
        this.addAbility(new ProwlAbility(this, "{1}{B}"));
        // Target player reveals three cards from his or her hand. You choose two of them. That player discards those cards.
        this.getSpellAbility().addEffect(new NogginWhackEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));

    }

    public NogginWhack(final NogginWhack card) {
        super(card);
    }

    @Override
    public NogginWhack copy() {
        return new NogginWhack(this);
    }
}

class NogginWhackEffect extends OneShotEffect<NogginWhackEffect> {

    public NogginWhackEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player reveals three cards from his or her hand. You choose two of them. That player discards those cards";
    }

    public NogginWhackEffect(final NogginWhackEffect effect) {
        super(effect);
    }

    @Override
    public NogginWhackEffect copy() {
        return new NogginWhackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && targetPlayer != null && sourceCard != null) {
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(targetPlayer.getHand());

            int count = Math.min(cardsInHand.size(), 3);

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

            int cardsToDiscard = Math.min(revealedCards.size(), 2);
            TargetCard targetInHand = new TargetCard(cardsToDiscard, cardsToDiscard, Zone.HAND, new FilterCard("card to discard"));
            targetInHand.setRequired(true);

            if (!revealedCards.isEmpty()) {
                targetPlayer.revealCards("Noggin Whack", revealedCards, game);
                controller.chooseTarget(Outcome.Exile, revealedCards, targetInHand, source, game);
                for (UUID cardId : (List<UUID>) targetInHand.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                        game.informPlayers(new StringBuilder(sourceCard.getName()).append(": Discarded card ").append(card.getName()).toString());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
