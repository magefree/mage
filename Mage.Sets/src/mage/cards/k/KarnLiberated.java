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
package mage.cards.k;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentImpl;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author anonymous
 */
public class KarnLiberated extends CardImpl {

    private UUID exileId = UUID.randomUUID();

    public KarnLiberated(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{7}");
        this.subtype.add("Karn");
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(6));

        // +4: Target player exiles a card from his or her hand.
        LoyaltyAbility ability1 = new LoyaltyAbility(new ExileFromZoneTargetEffect(Zone.HAND, exileId, this.getIdName(), new FilterCard()), 4);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        // -3: Exile target permanent.
        LoyaltyAbility ability2 = new LoyaltyAbility(new ExileTargetEffect(exileId, this.getIdName()), -3);
        ability2.addTarget(new TargetPermanent());
        this.addAbility(ability2);

        // -14: Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated. Then put those cards onto the battlefield under your control.
        this.addAbility(new LoyaltyAbility(new KarnLiberatedEffect(exileId), -14));
    }

    public KarnLiberated(final KarnLiberated card) {
        super(card);
    }

    @Override
    public KarnLiberated copy() {
        return new KarnLiberated(this);
    }
}

class KarnLiberatedEffect extends OneShotEffect {

    private UUID exileId;

    public KarnLiberatedEffect(UUID exileId) {
        super(Outcome.ExtraTurn);
        this.exileId = exileId;
        this.staticText = "Restart the game, leaving in exile all non-Aura permanent cards exiled with {this}. Then put those cards onto the battlefield under your control";
    }

    public KarnLiberatedEffect(final KarnLiberatedEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }
        List<Card> cards = new ArrayList<>();
        for (ExileZone zone : game.getExile().getExileZones()) {
            if (zone.getId().equals(exileId)) {
                for (Card card : zone.getCards(game)) {
                    if (!card.getSubtype(game).contains("Aura") && card.isPermanent()) {
                        cards.add(card);
                    }
                }
            }
        }
        game.getState().clear();
        for (Card card : game.getCards()) {
            game.getState().addCard(card);
        }
        for (Player player : game.getPlayers().values()) {
            if (player.isInGame()) { // only players alive are in the restarted game
                player.getGraveyard().clear();
                player.getHand().clear();
                player.getLibrary().clear();
                for (Card card : game.getCards()) {
                    if (card.getOwnerId().equals(player.getId()) && !card.isCopy() // no copies
                            && !player.getSideboard().contains(card.getId())
                            && !cards.contains(card)) { // not the exiled cards
                        if (player.getCommandersIds().contains(card.getId())) {
                            game.addCommander(new Commander(card));
                            game.setZone(card.getId(), Zone.COMMAND);
                        } else {
                            player.getLibrary().putOnTop(card, game);
                        }
                    }
                }
                player.init(game);
            }
        }
        for (Card card : cards) {
            game.getState().setZone(card.getId(), Zone.EXILED);
            if (card.isPermanent() && !card.getSubtype(game).contains("Aura")) {
                game.getExile().add(exileId, sourceObject.getIdName(), card);
            }
        }
        game.addDelayedTriggeredAbility(new KarnLiberatedDelayedTriggeredAbility(exileId), source);
        game.start(null);
        return true;
    }

    @Override
    public KarnLiberatedEffect copy() {
        return new KarnLiberatedEffect(this);
    }

}

class KarnLiberatedDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public KarnLiberatedDelayedTriggeredAbility(UUID exileId) {
        super(new KarnLiberatedDelayedEffect(exileId));
    }

    public KarnLiberatedDelayedTriggeredAbility(final KarnLiberatedDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.BEGINNING_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public KarnLiberatedDelayedTriggeredAbility copy() {
        return new KarnLiberatedDelayedTriggeredAbility(this);
    }

}

class KarnLiberatedDelayedEffect extends OneShotEffect {

    private final UUID exileId;

    public KarnLiberatedDelayedEffect(UUID exileId) {
        super(Outcome.PlayForFree);
        this.exileId = exileId;
        this.staticText = "Put those cards onto the battlefield under your control";
    }

    public KarnLiberatedDelayedEffect(final KarnLiberatedDelayedEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null) {
                // Creatures put onto the battlefield due to Karn's ability will have been under their controller's control continuously
                // since the beginning of the first turn. They can attack and their activated abilities with {T} in the cost can be activated.
                Cards cards = new CardsImpl(); // needed because putOntoTheBattlefield removes from exile
                cards.addAll(exile);
                controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
                for (Card card : cards.getCards(game)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    ((PermanentImpl) permanent).removeSummoningSickness();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public KarnLiberatedDelayedEffect copy() {
        return new KarnLiberatedDelayedEffect(this);
    }

}
