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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
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
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Plopman
 */
public class BorborygmosEnraged extends CardImpl<BorborygmosEnraged> {

    public BorborygmosEnraged(UUID ownerId) {
        super(ownerId, 147, "Borborygmos Enraged", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{G}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Cyclops");
        
        this.supertype.add("Legendary");

        this.color.setGreen(true);
        this.color.setRed(true);
        
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        //Trample
        this.addAbility(TrampleAbility.getInstance());
        
        //Whenever Borborygmous Enraged deals combat damage to a player, reveal the top three cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new BorborygmosEnragedEffect(), false, false));
        
        //Discard a land card: Borborygmos Enraged deals 3 damage to target creature or player
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(3), new DiscardTargetCost(new TargetCardInHand(new FilterLandCard())));
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

class BorborygmosEnragedEffect extends OneShotEffect<BorborygmosEnragedEffect> {

    public BorborygmosEnragedEffect() {
        super(Constants.Outcome.DrawCard);
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
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Constants.Zone.PICK);
            int count = Math.min(player.getLibrary().size(), 3);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    game.setZone(card.getId(), Constants.Zone.PICK);
                    if (card.getCardType().contains(CardType.LAND)) {
                        card.moveToZone(Constants.Zone.HAND, source.getId(), game, true);
                    } else {
                        card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, false);
                    }
                }
            }

            if (!cards.isEmpty()) {
                player.revealCards("Borborygmous Enraged", cards, game);
                return true;
            }
        }
        return false;
    }
}
