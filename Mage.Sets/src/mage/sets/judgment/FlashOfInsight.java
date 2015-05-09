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
package mage.sets.judgment;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class FlashOfInsight extends CardImpl {

    private static final FilterCard filter = new FilterCard("blue cards from your graveyard");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public FlashOfInsight(UUID ownerId) {
        super(ownerId, 40, "Flash of Insight", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{1}{U}");
        this.expansionSetCode = "JUD";

        this.color.setBlue(true);

        // Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new FlashOfInsightEffect());
        // Flashback-{1}{U}, Exile X blue cards from your graveyard.
        Ability ability = new FlashbackAbility(new ManaCostsImpl("{1}{U}"), TimingRule.INSTANT);
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
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player == null || sourceObject == null) {
            return false;
        }

        int xValue;
        xValue = source.getManaCostsToPay().getX();

        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), xValue);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.lookAtCards(sourceObject.getName(), cards, game);

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put into your hand"));
        if (player.choose(Outcome.DrawCard, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                game.informPlayers(sourceObject.getName() + ": " + player.getLogName() + " puts a card into his or her hand");
            }
        }

        target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        if (cards.size() > 0) {
            game.informPlayers(new StringBuilder(sourceObject.getName()).append(": ")
                    .append(player.getLogName()).append(" puts ")
                    .append(cards.size() == 1 ? "a":cards.size())
                    .append(" card").append(cards.size() > 1 ? "s":"")
                    .append(" on the bottom of his or her library").toString());
        }
        while (player.isInGame() && cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
        }

        return true;
    }
}
