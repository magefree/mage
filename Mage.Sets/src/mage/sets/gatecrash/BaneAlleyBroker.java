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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants.AsThoughEffectType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 * Gatecrash FAQ (01.2013)
 *
 * If Bane Alley Broker's first ability resolves when you have no cards in your hand,
 * you'll draw a card and then exile it. You won't have the opportunity to cast that
 * card (or do anything else with it) before exiling it.
 *
 * Due to a recent rules change, once you are allowed to look at a face-down card in
 * exile, you are allowed to look at that card as long as it's exiled. If you no longer
 * control Bane Alley Broker when its last ability resolves, you can continue to look
 * at the relevant cards in exile to choose one to return.
 *
 * Bane Alley Broker's second and third abilities apply to cards exiled with that
 * specific Bane Alley Broker, not any other creature named Bane Alley Broker.
 * You should keep cards exiled by different Bane Alley Brokers separate.
 *
 * If Bane Alley Broker leaves the battlefield, the cards exiled with it will be
 * exiled indefinitely. If it later returns to the battlefield, it will be a new
 * object with no connection to the cards exiled with it in its previous existence.
 * You won't be able to use the "new" Bane Alley Broker to return cards exiled with
 * the "old" one.
 *
 * Even if not all players can look at the exiled cards, each card's owner is still
 * known. It is advisable to keep cards owned by different players in distinct piles
 * in case another player gains control of Bane Alley Broker and exiles one or more
 * cards with it.
 *
 * @author LevelX2
 */
public class BaneAlleyBroker extends CardImpl<BaneAlleyBroker> {

    public BaneAlleyBroker(UUID ownerId) {
        super(ownerId, 145, "Bane Alley Broker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {tap}: Draw a card, then exile a card from your hand face down.
        
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BaneAlleyBrokerDrawExileEffect(), new TapSourceCost()));
        
        // You may look at cards exiled with Bane Alley Broker.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BaneAlleyBrokerLookAtCardEffect()));

        // {U}{B}, {tap}: Return a card exiled with Bane Alley Broker to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl("{U}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInBaneAlleyBrokerExile(this.getId()));
        this.addAbility(ability);

    }

    public BaneAlleyBroker(final BaneAlleyBroker card) {
        super(card);
    }

    @Override
    public BaneAlleyBroker copy() {
        return new BaneAlleyBroker(this);
    }
}

class BaneAlleyBrokerDrawExileEffect extends OneShotEffect<BaneAlleyBrokerDrawExileEffect> {

    public BaneAlleyBrokerDrawExileEffect() {
      super(Outcome.DrawCard);
      staticText = "Draw a card, then exile a card from your hand face down";
    }

    public BaneAlleyBrokerDrawExileEffect(final BaneAlleyBrokerDrawExileEffect effect) {
      super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
      Player player = game.getPlayer(source.getControllerId());
      if (player != null) {
          player.drawCards(1, game);
          Target target = new TargetCardInHand(new FilterCard("card to exile"));
          target.setRequired(true);
          if (player.chooseTarget(outcome, target, source, game)) {
              Card card = game.getCard(target.getFirstTarget());
              Card sourceCard = game.getCard(source.getSourceId());
              if (card != null && sourceCard != null) {
                  card.setFaceDown(true);
                  UUID exileId = (UUID) game.getState().getValue(new StringBuilder("exileZone").append(source.getSourceId()).append(sourceCard.getZoneChangeCounter()).toString());
                  if (exileId == null) {
                      exileId = UUID.randomUUID();
                      game.getState().setValue(new StringBuilder("exileZone").append(source.getSourceId()).append(sourceCard.getZoneChangeCounter()).toString(), exileId);
                  }
                  return card.moveToExile(exileId, new StringBuilder(sourceCard.getName()).append("(").append(sourceCard.getZoneChangeCounter()).append(")").toString(), source.getSourceId(), game);
              }
          }
      }
      return false;
    }

    @Override
    public BaneAlleyBrokerDrawExileEffect copy() {
      return new BaneAlleyBrokerDrawExileEffect(this);
    }
}

class TargetCardInBaneAlleyBrokerExile extends TargetCard<TargetCardInBaneAlleyBrokerExile> {

    public TargetCardInBaneAlleyBrokerExile(UUID CardId) {
       super(1, 1, Zone.EXILED, new FilterCard("card exiled with Bane Alley Broker"));
    }

    public TargetCardInBaneAlleyBrokerExile(final TargetCardInBaneAlleyBrokerExile target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<UUID>();
        Card sourceCard = game.getCard(sourceId);
        if (sourceCard != null) {
            UUID exileId = (UUID) game.getState().getValue(new StringBuilder("exileZone").append(sourceId).append(sourceCard.getZoneChangeCounter()).toString());
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && exile.size() > 0) {
                possibleTargets.addAll(exile);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        Card sourceCard = game.getCard(sourceId);
        if (sourceCard != null) {
            UUID exileId = (UUID) game.getState().getValue(new StringBuilder("exileZone").append(sourceId).append(sourceCard.getZoneChangeCounter()).toString());
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && exile.size() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
            ExileZone exile = null;
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                UUID exileId = (UUID) game.getState().getValue(new StringBuilder("exileZone").append(source.getSourceId()).append(sourceCard.getZoneChangeCounter()).toString());
                exile = game.getExile().getExileZone(exileId);
            }
            if (exile != null && exile.contains(id)) {
                return filter.match(card, source.getControllerId(), game);
            }
        }
        return false;
    }

    @Override
    public TargetCardInBaneAlleyBrokerExile copy() {
        return new TargetCardInBaneAlleyBrokerExile(this);
    }
}

class BaneAlleyBrokerLookAtCardEffect extends AsThoughEffectImpl<BaneAlleyBrokerLookAtCardEffect> {

    public BaneAlleyBrokerLookAtCardEffect() {
        super(AsThoughEffectType.REVEAL_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public BaneAlleyBrokerLookAtCardEffect(final BaneAlleyBrokerLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BaneAlleyBrokerLookAtCardEffect copy() {
        return new BaneAlleyBrokerLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null && game.getState().getZone(sourceId) == Zone.EXILED) {
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard == null) {
                return false;
            }
            UUID exileId = (UUID) game.getState().getValue(new StringBuilder("exileZone").append(source.getSourceId()).append(sourceCard.getZoneChangeCounter()).toString());
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && exile.contains(sourceId)) {
                Cards cards = new CardsImpl(card);
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    controller.lookAtCards("Exiled with " + sourceCard.getName(), cards, game);
                }
            }
        }
        return false;
    }

}
