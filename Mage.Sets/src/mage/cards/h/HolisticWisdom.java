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
package mage.cards.h;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class HolisticWisdom extends CardImpl {

    public HolisticWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{G}");

        // {2}, Exile a card from your hand: Return target card from your graveyard to your hand if it shares a card type with the card exiled this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HolisticWisdomEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new ExileFromHandCost(new TargetCardInHand(new FilterCard("a card from your hand"))));
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    public HolisticWisdom(final HolisticWisdom card) {
        super(card);
    }

    @Override
    public HolisticWisdom copy() {
        return new HolisticWisdom(this);
    }
}

class HolisticWisdomEffect extends OneShotEffect {

    public HolisticWisdomEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target card from your graveyard to your hand if it shares a card type with the card exiled this way";
    }

    public HolisticWisdomEffect(final HolisticWisdomEffect effect) {
        super(effect);
    }

    @Override
    public HolisticWisdomEffect copy() {
        return new HolisticWisdomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof ExileFromHandCost) {
                    List<CardType> cardtypes = new ArrayList<>();
                    ExileFromHandCost exileCost = (ExileFromHandCost) cost;
                    for (CardType cardtype : exileCost.getCards().get(0).getCardType()) {
                        cardtypes.add(cardtype);
                    }
                    for (CardType cardtype : card.getCardType()) {
                        if (cardtypes.contains(cardtype)) {
                            Effect effect = new ReturnToHandTargetEffect();
                            effect.setTargetPointer(new FixedTarget(card.getId()));
                            return effect.apply(game, source);
                        }
                    }
                }
            }
        }
        return false;
    }
}
