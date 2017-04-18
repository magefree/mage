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
package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DiscipleOfDeceit extends CardImpl {

    public DiscipleOfDeceit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Inspired - Whenever Disciple of Deceit becomes untapped, you may discard a nonland card. If you do, search your library for a card with the same converted mana cost as that card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new InspiredAbility(new DiscipleOfDeceitEffect(), false));
        
    }

    public DiscipleOfDeceit(final DiscipleOfDeceit card) {
        super(card);
    }

    @Override
    public DiscipleOfDeceit copy() {
        return new DiscipleOfDeceit(this);
    }
}

class DiscipleOfDeceitEffect extends OneShotEffect {
    
    public DiscipleOfDeceitEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may discard a nonland card. If you do, search your library for a card with the same converted mana cost as that card, reveal it, put it into your hand, then shuffle your library";
    }
    
    public DiscipleOfDeceitEffect(final DiscipleOfDeceitEffect effect) {
        super(effect);
    }
    
    @Override
    public DiscipleOfDeceitEffect copy() {
        return new DiscipleOfDeceitEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player != null && mageObject != null) {
            Cost cost = new DiscardTargetCost(new TargetCardInHand(new FilterNonlandCard()));
            String message = "Discard a nonland card to search your library?";
            if (cost.canPay(source, source.getSourceId(), source.getControllerId(), game) 
                    && player.chooseUse(Outcome.Detriment, message, source, game)) {
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    Card card = game.getCard(cost.getTargets().getFirstTarget());
                    if (card == null) {
                        return false;
                    }
                    String targetName = new StringBuilder("card with converted mana cost of ").append(card.getConvertedManaCost()).toString();
                    FilterCard filter = new FilterCard(targetName);
                    filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, card.getConvertedManaCost()));
                    return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true).apply(game, source);                    
                }
            }
            return true;
        }
        return false;
    }
}
