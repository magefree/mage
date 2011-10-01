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
package mage.sets.innistrad;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.WatcherImpl;

/**
 *
 * @author North
 */
public class InsectileAberration extends CardImpl<InsectileAberration> {

    public InsectileAberration(UUID ownerId) {
        super(ownerId, 1051, "Insectile Aberration", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Insect");

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.canTransform = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
    }

    public InsectileAberration(final InsectileAberration card) {
        super(card);
    }

    @Override
    public InsectileAberration copy() {
        return new InsectileAberration(this);
    }
    
    public static class InsectileAberrationWatcher extends WatcherImpl<InsectileAberrationWatcher> {

        public Map<UUID, Set<UUID>> blockedCreatures = new HashMap<UUID, Set<UUID>>();

        public InsectileAberrationWatcher() {
            super("InsectileAberrationWatcher");
        }

        public InsectileAberrationWatcher(final InsectileAberrationWatcher watcher) {
            super(watcher);
        }

        @Override
        public InsectileAberrationWatcher copy() {
            return new InsectileAberrationWatcher(this);
        }

        @Override
        public void watch(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.DRAW_STEP_PRE && event.getSourceId().equals(sourceId)) {
            	Player player = game.getPlayer(event.getPlayerId());
                if (player != null && player.getLibrary().size() > 0) {
                    Card card = player.getLibrary().getFromTop(game);
                    Cards cards = new CardsImpl();
                    cards.add(card);
                    player.lookAtCards("Insectile Aberration", cards, game);

                    if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
                    	player.revealCards("This card", cards, game);
                        condition = true;
                    }
                }
            }
        }
    }
}
