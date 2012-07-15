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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public class AugurOfBolas extends CardImpl<AugurOfBolas> {

    public AugurOfBolas(UUID ownerId) {
        super(ownerId, 43, "Augur of Bolas", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "M13";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Augur of Bolas enters the battlefield, look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AugurOfBolasEffect()));
    }

    public AugurOfBolas(final AugurOfBolas card) {
        super(card);
    }

    @Override
    public AugurOfBolas copy() {
        return new AugurOfBolas(this);
    }
}

class AugurOfBolasEffect extends OneShotEffect<AugurOfBolasEffect> {
    
    final static private FilterCard filter = new FilterCard("Instant or Sorcery card");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }
    
    public AugurOfBolasEffect() {
        super(Constants.Outcome.DrawCard);
        staticText = "look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order";
    }

    public AugurOfBolasEffect(final AugurOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public AugurOfBolasEffect copy() {
        return new AugurOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Constants.Zone.PICK);
        boolean spellFound = false;
        int count = Math.min(player.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Constants.Zone.PICK);
                if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
                    spellFound = true;
                }
            }
        }
        player.lookAtCards("Augur of Bolas", cards, game);

        if (spellFound && player.chooseUse(Constants.Outcome.DrawCard, "Do you wish to reveal an instant or sorcery card and put it into your hand?", game)) {
            TargetCard target = new TargetCard(Constants.Zone.PICK, filter);
            if (player.choose(Constants.Outcome.DrawCard, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);

                    Cards reveal = new CardsImpl(Constants.Zone.OUTSIDE);
                    reveal.add(card);
                    player.revealCards("Augur of Bolas", reveal, game);
                }
            }
        }

        TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Constants.Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }
}
