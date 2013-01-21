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

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class VendilionClique extends CardImpl<VendilionClique> {

    public VendilionClique(UUID ownerId) {
        super(ownerId, 55, "Vendilion Clique", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "MOR";
        this.supertype.add("Legendary");
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Vendilion Clique enters the battlefield, look at target player's hand. You may choose a nonland card from it. If you do, that player reveals the chosen card, puts it on the bottom of his or her library, then draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VendilionCliqueEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public VendilionClique(final VendilionClique card) {
        super(card);
    }

    @Override
    public VendilionClique copy() {
        return new VendilionClique(this);
    }
}

class VendilionCliqueEffect extends OneShotEffect<VendilionCliqueEffect> {
    VendilionCliqueEffect() {
        super(Constants.Outcome.Discard);
        staticText = "look at target player's hand. You may choose a nonland card from it. If you do, that player reveals the chosen card, puts it on the bottom of his or her library, then draws a card";
    }

    VendilionCliqueEffect(final VendilionCliqueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (player != null && sourcePlayer != null) {
            TargetCard targetCard = new TargetCard(Constants.Zone.ALL, new FilterNonlandCard());
            if (sourcePlayer.choose(Constants.Outcome.Discard, player.getHand(), targetCard, game)) {
                Card c = game.getCard(targetCard.getFirstTarget());
                if (c != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(c);
                    player.revealCards("Vendilion Clique effect", cards, game);
                    c.moveToZone(Constants.Zone.LIBRARY, source.getSourceId(), game, false);
                    player.drawCards(1, game);
                }
            }
        }
        return false;
    }

    @Override
    public VendilionCliqueEffect copy() {
        return new VendilionCliqueEffect(this);
    }
}