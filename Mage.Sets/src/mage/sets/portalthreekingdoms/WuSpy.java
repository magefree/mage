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
package mage.sets.portalthreekingdoms;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author djbrez
 */
public class WuSpy extends CardImpl {

    public WuSpy(UUID ownerId) {
        super(ownerId, 63, "Wu Spy", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "PTK";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.subtype.add("Rogue");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Wu Spy enters the battlefield, look at the top two cards of target player's library. Put one of them into his or her graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new WuSpyEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public WuSpy(final WuSpy card) {
        super(card);
    }

    @Override
    public WuSpy copy() {
        return new WuSpy(this);
    }
}

class WuSpyEffect extends OneShotEffect {

    WuSpyEffect() {
        super(Outcome.Exile);
        this.staticText = "look at the top two cards of target player's library. Put one of them into his or her graveyard";
    }

    WuSpyEffect(final WuSpyEffect effect) {
        super(effect);
    }

    @Override
    public WuSpyEffect copy() {
        return new WuSpyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && opponent != null) {
            Cards cards = new CardsImpl();
            cards.addAll(opponent.getLibrary().getTopCards(game, 2));
            if (!cards.isEmpty()) {
                TargetCard target = new TargetCardInLibrary(new FilterCard("card to put into graveyard"));
                controller.choose(Outcome.Benefit, cards, target, game);
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
