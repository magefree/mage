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
import mage.Constants.AsThoughEffectType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
* FAQ
* 
*  The card is exiled face up. All players may look at it.
*  Playing a card exiled with Nightveil Specter follows all the normal rules for 
*  playing that card. You must pay its costs, and you must follow all timing 
*  restrictions, for example.
*
*  Nightveil Specter's last ability applies to cards exiled with that specific 
*  Nightveil Specter, not any other creature named Nightveil Specter. You should 
*  keep cards exiled by different Nightveil Specters separate.
*
* @author LevelX2
*/
public class NightveilSpecter extends CardImpl<NightveilSpecter> {

    public NightveilSpecter(UUID ownerId) {
      super(ownerId, 222, "Nightveil Specter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U/B}{U/B}{U/B}");
      this.expansionSetCode = "GTC";
      this.subtype.add("Specter");

      this.color.setBlue(true);
      this.color.setBlack(true);
      this.power = new MageInt(2);
      this.toughness = new MageInt(3);

      // Flying
      this.addAbility(FlyingAbility.getInstance());

      // Whenever Nightveil Specter deals combat damage to a player, that player exiles the top card of his or her library.
      this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new NightveilSpecterExileEffect(),false, true));

      // You may play cards exiled with Nightveil Specter.
      this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NightveilSpecterEffect()));
    }

    public NightveilSpecter(final NightveilSpecter card) {
      super(card);
    }

    @Override
    public NightveilSpecter copy() {
      return new NightveilSpecter(this);
    }
}

class NightveilSpecterExileEffect extends OneShotEffect<NightveilSpecterExileEffect> {

    public NightveilSpecterExileEffect() {
      super(Outcome.Discard);
      staticText = "that player exiles the top card of his or her library";
    }

    public NightveilSpecterExileEffect(final NightveilSpecterExileEffect effect) {
      super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
      Player player = game.getPlayer(targetPointer.getFirst(game, source));
      if (player != null) {
          Card card = player.getLibrary().removeFromTop(game);
          Card sourceCard = game.getCard(source.getSourceId());
          if (card != null && sourceCard != null) {
              UUID exileId = (UUID) game.getState().getValue(new StringBuilder("exileZone").append(source.getSourceId()).append(sourceCard.getZoneChangeCounter()).toString());
              if (exileId == null) {
                  exileId = UUID.randomUUID();
                  game.getState().setValue(new StringBuilder("exileZone").append(source.getSourceId()).append(sourceCard.getZoneChangeCounter()).toString(), exileId);
              }
              return card.moveToExile(exileId, "Nightveil Specter", source.getSourceId(), game);
          }
      }
      return false;
    }

    @Override
    public NightveilSpecterExileEffect copy() {
      return new NightveilSpecterExileEffect(this);
    }
}

class NightveilSpecterEffect extends AsThoughEffectImpl<NightveilSpecterEffect> {

    public NightveilSpecterEffect() {
      super(AsThoughEffectType.CAST, Duration.EndOfGame, Outcome.Benefit);
      staticText = "You may play cards exiled with Nightveil Specter";
    }

    public NightveilSpecterEffect(final NightveilSpecterEffect effect) {
      super(effect);
    }


    @Override
    public boolean apply(Game game, Ability source) {
      return true;
    }

    @Override
    public NightveilSpecterEffect copy() {
      return new NightveilSpecterEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
      Card card = game.getCard(sourceId);
      if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
          Card sourceCard = game.getCard(source.getSourceId());
          UUID exileId = (UUID) game.getState().getValue(new StringBuilder("exileZone").append(source.getSourceId()).append(sourceCard.getZoneChangeCounter()).toString());
          ExileZone zone = game.getExile().getExileZone(exileId);
          if (zone != null && zone.contains(card.getId())) {
                if (card.getCardType().contains(CardType.INSTANT) || game.canPlaySorcery(source.getControllerId())) {
                    card.setControllerId(source.getControllerId());
                    return true;
                }
          }
      }
      return false;
    }
}
