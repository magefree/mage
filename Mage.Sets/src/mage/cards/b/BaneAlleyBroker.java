
package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
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
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * Gatecrash FAQ (01.2013)
 *
 * If Bane Alley Broker's first ability resolves when you have no cards in your
 * hand, you'll draw a card and then exile it. You won't have the opportunity to
 * cast that card (or do anything else with it) before exiling it.
 *
 * Due to a recent rules change, once you are allowed to look at a face-down
 * card in exile, you are allowed to look at that card as long as it's exiled.
 * If you no longer control Bane Alley Broker when its last ability resolves,
 * you can continue to look at the relevant cards in exile to choose one to
 * return.
 *
 * Bane Alley Broker's second and third abilities apply to cards exiled with
 * that specific Bane Alley Broker, not any other creature named Bane Alley
 * Broker. You should keep cards exiled by different Bane Alley Brokers
 * separate.
 *
 * If Bane Alley Broker leaves the battlefield, the cards exiled with it will be
 * exiled indefinitely. If it later returns to the battlefield, it will be a new
 * object with no connection to the cards exiled with it in its previous
 * existence. You won't be able to use the "new" Bane Alley Broker to return
 * cards exiled with the "old" one.
 *
 * Even if not all players can look at the exiled cards, each card's owner is
 * still known. It is advisable to keep cards owned by different players in
 * distinct piles in case another player gains control of Bane Alley Broker and
 * exiles one or more cards with it.
 *
 * @author LevelX2
 */
public final class BaneAlleyBroker extends CardImpl {

    public BaneAlleyBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ROGUE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {tap}: Draw a card, then exile a card from your hand face down.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BaneAlleyBrokerDrawExileEffect(), new TapSourceCost()));

        // You may look at cards exiled with Bane Alley Broker.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new BaneAlleyBrokerLookAtCardEffect()));

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

class BaneAlleyBrokerDrawExileEffect extends OneShotEffect {

    public BaneAlleyBrokerDrawExileEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw a card, then exile a card from your hand face down";
    }

    public BaneAlleyBrokerDrawExileEffect(final BaneAlleyBrokerDrawExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(1, game);
            Target target = new TargetCardInHand(new FilterCard("card to exile"));
            if (controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                MageObject sourceObject = game.getObject(source.getSourceId());
                if (card != null && sourceObject != null) {
                    if (card.moveToExile(CardUtil.getCardExileZoneId(game, source), new StringBuilder(sourceObject.getName()).toString(), source.getSourceId(), game)) {
                        card.setFaceDown(true, game);
                        return true;
                    }
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

class TargetCardInBaneAlleyBrokerExile extends TargetCardInExile {

    public TargetCardInBaneAlleyBrokerExile(UUID cardId) {
        super(1, 1, new FilterCard("card exiled with Bane Alley Broker"), null);
    }

    public TargetCardInBaneAlleyBrokerExile(final TargetCardInBaneAlleyBrokerExile target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Card sourceCard = game.getCard(sourceId);
        if (sourceCard != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, sourceId);
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && !exile.isEmpty()) {
                possibleTargets.addAll(exile);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        Card sourceCard = game.getCard(sourceId);
        if (sourceCard != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, sourceId);
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && !exile.isEmpty()) {
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
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
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

class BaneAlleyBrokerLookAtCardEffect extends AsThoughEffectImpl {

    public BaneAlleyBrokerLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
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
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                MageObject sourceObject = game.getObject(source.getSourceId());
                if (sourceObject == null) {
                    return false;
                }
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                ExileZone exile = game.getExile().getExileZone(exileId);
                return exile != null && exile.contains(objectId);
            }
        }
        return false;
    }

}
