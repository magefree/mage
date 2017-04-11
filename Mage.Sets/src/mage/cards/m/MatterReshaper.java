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
package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class MatterReshaper extends CardImpl {

    public MatterReshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{C}");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Matter Reshaper dies, reveal the top card of your library. You may put that card onto the battlefield
        // if it's a permanent card with converted mana cost 3 or less. Otherwise, put that card into your hand.
        this.addAbility(new DiesTriggeredAbility(new MatterReshaperEffect(), false));
    }

    public MatterReshaper(final MatterReshaper card) {
        super(card);
    }

    @Override
    public MatterReshaper copy() {
        return new MatterReshaper(this);
    }
}

class MatterReshaperEffect extends OneShotEffect {

    public MatterReshaperEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library. You may put that card onto the battlefield if it's a permanent card"
                + " with converted mana cost 3 or less. Otherwise, put that card into your hand";
    }

    public MatterReshaperEffect(final MatterReshaperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
            FilterPermanentCard filter = new FilterPermanentCard("permanent card with converted mana cost 3 or less");
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
            if (filter.match(card, game)) {
                if (controller.chooseUse(Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield (otherwise put in hand)?", source, game)) {
                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), source.getControllerId(), false);
                    return true;
                }
            }
            card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public MatterReshaperEffect copy() {
        return new MatterReshaperEffect(this);
    }
}
