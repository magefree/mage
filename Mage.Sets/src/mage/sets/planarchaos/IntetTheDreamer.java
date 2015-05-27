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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public class IntetTheDreamer extends CardImpl {

    public IntetTheDreamer(UUID ownerId) {
        super(ownerId, 158, "Intet, the Dreamer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{R}{G}");
        this.expansionSetCode = "PLC";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Intet, the Dreamer deals combat damage to a player, you may pay {2}{U}. If you do, exile the top card of your library face down.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(new IntetTheDreamerExileEffect(), new ManaCostsImpl("{2}{U}")), false, true));
        // You may look at that card for as long as it remains exiled. You may play that card without paying its mana cost for as long as Intet remains on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IntetTheDreamerEffect()));
    }
    public IntetTheDreamer(final IntetTheDreamer card) {
        super(card);
    }

    @Override
    public IntetTheDreamer copy() {
        return new IntetTheDreamer(this);
    }
}

class IntetTheDreamerExileEffect extends OneShotEffect {
    
    public IntetTheDreamerExileEffect() {
        super(Outcome.Discard);
        staticText = "exile the top card of your library face down";
    }
    
    public IntetTheDreamerExileEffect(final IntetTheDreamerExileEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
          Card card = player.getLibrary().getFromTop(game);
          MageObject sourceObject = source.getSourceObject(game);
          if (card != null && sourceObject != null) {
              UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
              player.moveCardToExileWithInfo(card, exileZoneId, sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, false);
              card.setFaceDown(true, game);
              return true;
          }
      }
      return false;
    }
    
    @Override
    public IntetTheDreamerExileEffect copy() {
        return new IntetTheDreamerExileEffect(this);
    }
}

class IntetTheDreamerEffect extends AsThoughEffectImpl {

    public IntetTheDreamerEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play the card from exile without paying its mana cost for as long as {this} remains on the battlefield";
    }

    public IntetTheDreamerEffect(final IntetTheDreamerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IntetTheDreamerEffect copy() {
        return new IntetTheDreamerEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = game.getCard(objectId);
            if (affectedControllerId.equals(source.getControllerId()) && card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
                ExileZone zone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
                if (zone != null && zone.contains(card.getId())) {
                    if (controller.chooseUse(outcome, "Look at the card?", game)) {
                        Cards cards = new CardsImpl();
                        cards.add(card);
                        controller.lookAtCards(sourceObject.getName(), cards, game);
                        return false;
                    } 
                    controller.setCastSourceIdWithAlternateMana(objectId, null);
                    return true;                    
                }
            }
        }
        return false;
    }
}