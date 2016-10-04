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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public class KrovikanSorcerer extends CardImpl {
    
    private static final FilterCard filterNonBlack = new FilterCard("a nonblack card");
    private static final FilterCard filterBlack = new FilterCard("a black card");
    static {
        filterNonBlack.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public KrovikanSorcerer(UUID ownerId) {
        super(ownerId, 77, "Krovikan Sorcerer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "VMA";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Discard a nonblack card: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filterNonBlack)));
        this.addAbility(ability);
        
        // {tap}, Discard a black card: Draw two cards, then discard one of them.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KrovikanSorcererEffect(), new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filterBlack)));
        this.addAbility(ability);
    }

    public KrovikanSorcerer(final KrovikanSorcerer card) {
        super(card);
    }

    @Override
    public KrovikanSorcerer copy() {
        return new KrovikanSorcerer(this);
    }
}

class KrovikanSorcererEffect extends OneShotEffect {

    KrovikanSorcererEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw two cards, then discard one of them";
    }

    KrovikanSorcererEffect(final KrovikanSorcererEffect effect) {
        super(effect);
    }

    @Override
    public KrovikanSorcererEffect copy() {
        return new KrovikanSorcererEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards initialHand = player.getHand().copy();
            player.drawCards(2, game);
            Cards drawnCards = new CardsImpl();
            for (UUID cardId : player.getHand()) {
                if (!initialHand.contains(cardId)) {
                    drawnCards.add(cardId);
                }
            }
            if (drawnCards.size() > 0) {
                TargetCard cardToDiscard = new TargetCard(Zone.HAND, new FilterCard("card to discard"));
                cardToDiscard.setNotTarget(true);
                if (player.choose(Outcome.Discard, drawnCards, cardToDiscard, game)) {
                    Card card = player.getHand().get(cardToDiscard.getFirstTarget(), game);
                    if (card != null) {
                        return player.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
