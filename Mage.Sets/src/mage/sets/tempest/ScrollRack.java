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

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class ScrollRack extends CardImpl {

    public ScrollRack(UUID ownerId) {
        super(ownerId, 298, "Scroll Rack", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "TMP";

        // {1}, {tap}: Exile any number of cards from your hand face down. Put that many cards from the top of your library into your hand. Then look at the exiled cards and put them on top of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScrollRackEffect(), new GenericManaCost(1));
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

class ScrollRackEffect extends OneShotEffect {

    public ScrollRackEffect() {
        super(Outcome.Neutral);
        staticText = "Exile any number of cards from your hand face down. Put that many cards from the top of your library into your hand. Then look at the exiled cards and put them on top of your library in any order";
    }

    public ScrollRackEffect(final ScrollRackEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            FilterCard filter = new FilterCard("card in your hand to exile");
//            FilterCard filter2 = new FilterCard("(move the window) card exiled by " + sourceObject.getIdName() + " to put on top of library");
            TargetCardInHand target = new TargetCardInHand(0, controller.getHand().size(), filter);
            target.setRequired(false);
            int amountExiled = 0;
            if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), game)) {
                if (!target.getTargets().isEmpty()) {
                    for (UUID targetId : target.getTargets()) {
                        Card card = game.getCard(targetId);
                        if (card != null) {
                            card.setFaceDown(true, game);
                            amountExiled++;
                        }
                    }
                    controller.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game), source, game, false, source.getSourceId(), sourceObject.getIdName());
                    for (Card card : game.getExile().getExileZone(source.getSourceId()).getCards(game)) {
                        card.setFaceDown(true, game);
                    }
                }
            }
            // Put that many cards from the top of your library into your hand.
            if (amountExiled > 0) {
                controller.moveCards(controller.getLibrary().getTopCards(game, amountExiled), null, Zone.HAND, source, game, false);
            }
            // Then look at the exiled cards and put them on top of your library in any order
            controller.putCardsOnTopOfLibrary(game.getExile().getExileZone(source.getSourceId()), game, source, true);
            return true;
        }
        return false;
    }

    @Override
    public ScrollRackEffect copy() {
        return new ScrollRackEffect(this);
    }
}
