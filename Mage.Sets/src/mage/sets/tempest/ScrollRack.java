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
package mage.sets.tempest;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class ScrollRack extends CardImpl<ScrollRack> {

    public ScrollRack(UUID ownerId) {
        super(ownerId, 298, "Scroll Rack", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "TMP";

        // {1}, {tap}: Exile any number of cards from your hand face down. Put that many cards from the top of your library into your hand. Then look at the exiled cards and put them on top of your library in any order.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ScrollRackEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public ScrollRack(final ScrollRack card) {
        super(card);
    }

    @Override
    public ScrollRack copy() {
        return new ScrollRack(this);
    }
}

class ScrollRackEffect extends OneShotEffect<ScrollRackEffect> {

    public ScrollRackEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "Exile any number of cards from your hand face down. Put that many cards from the top of your library into your hand. Then look at the exiled cards and put them on top of your library in any order";
    }

    public ScrollRackEffect(final ScrollRackEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("card in your hand");
        FilterCard filter2 = new FilterCard("card exiled by Scroll Rack to put on top of library");
        Player you = game.getPlayer(source.getControllerId());
        TargetCardInHand target = new TargetCardInHand(0, you.getHand().size(), filter);
        int amountExiled = 0;
        if (you != null) {
            if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.Neutral, source.getControllerId(), source.getId(), game)) {
                if (!target.getTargets().isEmpty()) {
                    List<UUID> targets = target.getTargets();
                    for (UUID targetId : targets) {
                        Card card = game.getCard(targetId);
                        if (card != null) {
                            card.setFaceDown(true);
                            if (card.moveToExile(source.getId(), "Scroll Rack Exile", source.getSourceId(), game)) {
                                amountExiled++;
                            }
                        }
                    }
                }
            }
            if (amountExiled > 0) {
                int count = Math.min(you.getLibrary().size(), amountExiled);
                for (int i = 0; i < count; i++) {
                    Card card = you.getLibrary().removeFromTop(game);
                    if (card != null) {
                        card.moveToZone(Zone.HAND, id, game, false);
                    }
                }
            }
            Cards cards = game.getExile().getExileZone(source.getId());
            TargetCardInExile targetExiled = new TargetCardInExile(filter2, source.getSourceId());
            targetExiled.setRequired(true);
            while (cards.size() > 1) {
                you.choose(Constants.Outcome.Neutral, cards, targetExiled, game);
                Card card = cards.get(targetExiled.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, true);
                }
                targetExiled.clearChosen();
            }
            if (cards.size() == 1) {
                Card card = cards.get(cards.iterator().next(), game);
                card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public ScrollRackEffect copy() {
        return new ScrollRackEffect(this);
    }
}