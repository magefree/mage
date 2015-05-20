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
package mage.sets.theros;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class SteamAugury extends CardImpl {

    public SteamAugury(UUID ownerId) {
        super(ownerId, 205, "Steam Augury", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");
        this.expansionSetCode = "THS";


        // Reveal the top five cards of your library and separate them into two piles. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new SteamAuguryEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public SteamAugury(final SteamAugury card) {
        super(card);
    }

    @Override
    public SteamAugury copy() {
        return new SteamAugury(this);
    }
}

class SteamAuguryEffect extends OneShotEffect {

    public SteamAuguryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library and separate them into two piles. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard";
    }

    public SteamAuguryEffect(final SteamAuguryEffect effect) {
        super(effect);
    }

    @Override
    public SteamAuguryEffect copy() {
        return new SteamAuguryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 5));
        controller.revealCards(sourceObject.getName(), cards, game);

        Player opponent;
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            Target target = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            opponent = game.getPlayer(target.getFirstTarget());
        }

        if (opponent != null) {
            TargetCard target = new TargetCard(0, cards.size(), Zone.PICK, new FilterCard("cards to put in the first pile"));
            List<Card> pile1 = new ArrayList<>();
            Cards pile1CardsIds = new CardsImpl();
            target.setRequired(false);
            if (controller.choose(Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        pile1.add(card);
                        pile1CardsIds.add(card.getId());
                    }
                }
            }
            List<Card> pile2 = new ArrayList<>();
            Cards pile2CardsIds = new CardsImpl();
            for (UUID cardId :cards) {
                Card card = game.getCard(cardId);
                if (card != null && !pile1.contains(card)) {
                    pile2.add(card);
                    pile2CardsIds.add(card.getId());
                }
            }
            boolean choice = opponent.choosePile(Outcome.Detriment, new StringBuilder("Choose a pile to put into ").append(controller.getLogName()).append("'s hand.").toString(), pile1, pile2, game);

            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (choice) {
                pile1Zone = Zone.HAND;
                pile2Zone = Zone.GRAVEYARD;
            }

            StringBuilder sb = new StringBuilder(sourceObject.getLogName() + ": Pile 1, going to ").append(pile1Zone.equals(Zone.HAND)?"Hand":"Graveyard").append (": ");
            int i = 0;
            for (UUID cardUuid : pile1CardsIds) {
                i++;
                Card card = game.getCard(cardUuid);
                if (card != null) {
                    sb.append(card.getName());
                    if (i < pile1CardsIds.size()) {
                        sb.append(", ");
                    }
                    card.moveToZone(pile1Zone, source.getSourceId(), game, false);
                }
            }
            game.informPlayers(sb.toString());

            sb = new StringBuilder(sourceObject.getLogName() + ": Pile 2, going to ").append(pile2Zone.equals(Zone.HAND)?"Hand":"Graveyard").append (":");
            i = 0;
            for (UUID cardUuid : pile2CardsIds) {
                Card card = game.getCard(cardUuid);
                if (card != null) {
                    i++;
                    sb.append(" ").append(card.getName());
                    if (i < pile2CardsIds.size()) {
                        sb.append(", ");
                    }
                    card.moveToZone(pile2Zone, source.getSourceId(), game, false);
                }
            }
            game.informPlayers(sb.toString());
        }

        return true;
    }
}
