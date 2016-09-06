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
package mage.sets.gatecrash;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Plopman
 */
public class BorborygmosEnraged extends CardImpl {

    public BorborygmosEnraged(UUID ownerId) {
        super(ownerId, 147, "Borborygmos Enraged", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{G}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Cyclops");
        
        this.supertype.add("Legendary");

        
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        //Trample
        this.addAbility(TrampleAbility.getInstance());
        
        //Whenever Borborygmous Enraged deals combat damage to a player, reveal the top three cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new BorborygmosEnragedEffect(), false, false));
        
        //Discard a land card: Borborygmos Enraged deals 3 damage to target creature or player
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new DiscardTargetCost(new TargetCardInHand(new FilterLandCard())));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public BorborygmosEnraged(final BorborygmosEnraged card) {
        super(card);
    }

    @Override
    public BorborygmosEnraged copy() {
        return new BorborygmosEnraged(this);
    }
}

class BorborygmosEnragedEffect extends OneShotEffect {

    public BorborygmosEnragedEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top three cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard";
    }

    public BorborygmosEnragedEffect(final BorborygmosEnragedEffect effect) {
        super(effect);
    }

    @Override
    public BorborygmosEnragedEffect copy() {
        return new BorborygmosEnragedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                controller.revealCards("Borborygmous Enraged", cards, game);
                Cards landCards = new CardsImpl();
                for(Card card: cards.getCards(game) ) {
                    if (card.getCardType().contains(CardType.LAND)) {
                        landCards.add(card);
                        cards.remove(card);
                    }                    
                }
                controller.moveCards(landCards, Zone.HAND, source, game);
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
