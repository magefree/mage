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
package mage.sets.conflux;

import java.util.List;
import java.util.UUID;
import mage.ObjectColor;
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
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class Conflux extends CardImpl<Conflux> {

    public Conflux(UUID ownerId) {
        super(ownerId, 102, "Conflux", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{3}{W}{U}{B}{R}{G}");
        this.expansionSetCode = "CON";

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);

        // Search your library for a white card, a blue card, a black card, a red card, and a green card. Reveal those cards and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new ConfluxEffect());

    }

    public Conflux(final Conflux card) {
        super(card);
    }

    @Override
    public Conflux copy() {
        return new Conflux(this);
    }
}

class ConfluxEffect extends OneShotEffect<ConfluxEffect> {

    public ConfluxEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for a white card, a blue card, a black card, a red card, and a green card. Reveal those cards and put them into your hand. Then shuffle your library";
    }

    public ConfluxEffect(final ConfluxEffect effect) {
        super(effect);
    }

    @Override
    public ConfluxEffect copy() {
        return new ConfluxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl();
        FilterCard filterWhite = new FilterCard("white card");
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        FilterCard filterBlue = new FilterCard("blue card");
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        FilterCard filterBlack = new FilterCard("black card");
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        FilterCard filterRed = new FilterCard("red card");
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        FilterCard filterGreen = new FilterCard("green card");
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        TargetCardInLibrary targetWhite = new TargetCardInLibrary(filterWhite);
        TargetCardInLibrary targetBlue = new TargetCardInLibrary(filterBlue);
        TargetCardInLibrary targetBlack = new TargetCardInLibrary(filterBlack);
        TargetCardInLibrary targetRed = new TargetCardInLibrary(filterRed);
        TargetCardInLibrary targetGreen = new TargetCardInLibrary(filterGreen);

        if (you != null && you.getLibrary().size() > 0) {
            if (you.searchLibrary(targetWhite, game)) {
                if (targetWhite.getTargets().size() > 0) {
                    for (UUID cardId : (List<UUID>) targetWhite.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null && you.getLibrary().size() > 0) {
            if (you.searchLibrary(targetBlue, game)) {
                if (targetBlue.getTargets().size() > 0) {
                    for (UUID cardId : (List<UUID>) targetBlue.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null && you.getLibrary().size() > 0) {
            if (you.searchLibrary(targetBlack, game)) {
                if (targetBlack.getTargets().size() > 0) {
                    for (UUID cardId : (List<UUID>) targetBlack.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null && you.getLibrary().size() > 0) {
            if (you.searchLibrary(targetRed, game)) {
                if (targetRed.getTargets().size() > 0) {
                    for (UUID cardId : (List<UUID>) targetRed.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null && you.getLibrary().size() > 0) {
            if (you.searchLibrary(targetGreen, game)) {
                if (targetGreen.getTargets().size() > 0) {
                    for (UUID cardId : (List<UUID>) targetGreen.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null) {
            you.revealCards("Conflux", cards, game);
            for (Card card : cards.getCards(game)) {
                card.moveToZone(Zone.HAND, source.getId(), game, true);
            }
            you.shuffleLibrary(game);
        }
        return true;
    }
}
