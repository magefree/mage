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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.turn.TurnMod;
import mage.players.Player;


/**
 *
 * @author LevelX2
 */
public class SearchTheCity extends CardImpl<SearchTheCity> {

    public SearchTheCity(UUID ownerId) {
        super(ownerId, 49, "Search the City", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);

        // When Search the City enters the battlefield, exile the top five cards of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchTheCityExileEffect()));

        // Whenever you play a card with the same name as one of the exiled cards, you may put one of those cards with that name into its owner's hand. Then if there are no cards exiled with Search the City, sacrifice it. If you do, take an extra turn after this one.
         this.addAbility(new SearchTheCityTriggeredAbility());
        
    }

    public SearchTheCity(final SearchTheCity card) {
        super(card);
    }

    @Override
    public SearchTheCity copy() {
        return new SearchTheCity(this);
    }
}


class SearchTheCityExileEffect extends OneShotEffect<SearchTheCityExileEffect> {

    public SearchTheCityExileEffect() {
        super(Constants.Outcome.DrawCard);
        staticText = "exile the top five cards of your library";
    }

    public SearchTheCityExileEffect(final SearchTheCityExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            // move cards from library to exile
            for (int i = 0; i < 5; i++) {
                if (player != null && player.getLibrary().size() > 0) {
                    Card topCard = player.getLibrary().getFromTop(game);
                    topCard.moveToExile(source.getSourceId(), "Cards exiled by Search the City", source.getId(), game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SearchTheCityExileEffect copy() {
        return new SearchTheCityExileEffect(this);
    }
}


class SearchTheCityTriggeredAbility extends TriggeredAbilityImpl<SearchTheCityTriggeredAbility> {

    public SearchTheCityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SearchTheCityExiledCardToHandEffect(), true);

    }

    public SearchTheCityTriggeredAbility(final SearchTheCityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED)
                && event.getPlayerId().equals(this.getControllerId())) {
            String cardName = "";
            if (event.getType() == GameEvent.EventType.SPELL_CAST) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null) {
                    cardName = spell.getName();
                }
            }
            if (event.getType() == GameEvent.EventType.LAND_PLAYED) {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    cardName = card.getName();
                }
            }
            if (!cardName.isEmpty()) {
                ExileZone searchTheCityExileZone = game.getExile().getExileZone(this.getSourceId());
                FilterCard filter = new FilterCard();
                filter.add(new NamePredicate(cardName));
                
                if (searchTheCityExileZone.count(filter, game) > 0) {
                    this.getEffects().get(0).setValue("cardName",cardName);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you play a card with the same name as one of the exiled cards, " + super.getRule();
    }

    @Override
    public SearchTheCityTriggeredAbility copy() {
        return new SearchTheCityTriggeredAbility(this);
    }
}


class SearchTheCityExiledCardToHandEffect extends OneShotEffect<SearchTheCityExiledCardToHandEffect> {

    public SearchTheCityExiledCardToHandEffect() {
        super(Constants.Outcome.DrawCard);
        staticText = "you may put one of those cards with that name into its owner's hand. Then if there are no cards exiled with Search the City, sacrifice it. If you do, take an extra turn after this one";
    }

    public SearchTheCityExiledCardToHandEffect(final SearchTheCityExiledCardToHandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) this.getValue("cardName");
        ExileZone searchTheCityExileZone = game.getExile().getExileZone(source.getSourceId());
        if (cardName != null && searchTheCityExileZone != null) {
            for (Card card :searchTheCityExileZone.getCards(game)) {
                if (card.getName().equals(cardName)) {
                    if (card.moveToZone(Zone.HAND, source.getSourceId(), game, true)) {
                        game.informPlayers("Search the City: put " + card.getName() + " into owner's hand");
                    }
                    searchTheCityExileZone.remove(card);
                    if (searchTheCityExileZone.isEmpty()) {
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.sacrifice(source.getSourceId(), game);
                            // extra turn
                            game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SearchTheCityExiledCardToHandEffect copy() {
        return new SearchTheCityExiledCardToHandEffect(this);
    }
}