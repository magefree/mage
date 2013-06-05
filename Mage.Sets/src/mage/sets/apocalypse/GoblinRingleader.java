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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Plopman
 */
public class GoblinRingleader extends CardImpl<GoblinRingleader> {

    public GoblinRingleader(UUID ownerId) {
        super(ownerId, 62, "Goblin Ringleader", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "APC";
        this.subtype.add("Goblin");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Goblin Ringleader enters the battlefield, reveal the top four cards of your library. Put all Goblin cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GoblinRingleaderEffect()));
    }

    public GoblinRingleader(final GoblinRingleader card) {
        super(card);
    }

    @Override
    public GoblinRingleader copy() {
        return new GoblinRingleader(this);
    }
}

class GoblinRingleaderEffect extends OneShotEffect<GoblinRingleaderEffect> {

    private static final FilterCard filter = new FilterCard("Goblin");
    static {
        filter.add(new SubtypePredicate("Goblin"));
    }
    
    public GoblinRingleaderEffect() {
        super(Constants.Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. Put all Goblin cards revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public GoblinRingleaderEffect(final GoblinRingleaderEffect effect) {
        super(effect);
    }

    @Override
    public GoblinRingleaderEffect copy() {
        return new GoblinRingleaderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Constants.Zone.PICK);
            Cards cards2 = new CardsImpl(Constants.Zone.PICK);
            int count = Math.min(player.getLibrary().size(), 4);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    game.setZone(card.getId(), Constants.Zone.PICK);
                    if (filter.match(card, game)) {
                        card.moveToZone(Constants.Zone.HAND, source.getId(), game, true);
                    } else {
                        cards2.add(card);
                    }
                }
            }
            
            Card sourceCard = game.getCard(source.getSourceId());
            if (!cards.isEmpty() && sourceCard != null) {
                player.revealCards(sourceCard.getName(), cards, game);
            }
            TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterCard("card to put on the bottom of your library"));
            target.setRequired(true);
            while (cards2.size() > 0 && player.choose(Constants.Outcome.Detriment, cards2, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards2.remove(card);
                    card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
                }
                target.clearChosen();
            }
        }
        return true;
    }
}
