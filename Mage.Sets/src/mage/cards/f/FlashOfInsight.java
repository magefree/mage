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
package mage.cards.f;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class FlashOfInsight extends CardImpl {

    public FlashOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{1}{U}");

        // Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new FlashOfInsightEffect());

        // Flashback-{1}{U}, Exile X blue cards from your graveyard.
        Ability ability = new FlashbackAbility(new ManaCostsImpl("{1}{U}"), TimingRule.INSTANT);
        FilterCard filter = new FilterCard("blue cards from your graveyard");
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(Predicates.not(new CardIdPredicate(getId())));
        ability.addCost(new ExileXFromYourGraveCost(filter));
        this.addAbility(ability);
    }

    public FlashOfInsight(final FlashOfInsight card) {
        super(card);
    }

    @Override
    public FlashOfInsight copy() {
        return new FlashOfInsight(this);
    }
}

class FlashOfInsightEffect extends OneShotEffect {

    public FlashOfInsightEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order";
    }

    public FlashOfInsightEffect(final FlashOfInsightEffect effect) {
        super(effect);
    }

    @Override
    public FlashOfInsightEffect copy() {
        return new FlashOfInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        int xValue = source.getManaCostsToPay().getX();

        for (Cost cost : source.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                xValue = ((ExileFromGraveCost) cost).getExiledCards().size();
            }
        }

        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, xValue));
        controller.lookAtCards(sourceObject.getIdName(), cards, game);

        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
        target.setNotTarget(true);
        if (controller.chooseTarget(Outcome.DrawCard, cards, target, source, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
                cards.remove(card);
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
