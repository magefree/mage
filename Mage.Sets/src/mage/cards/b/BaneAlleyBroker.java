package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Gatecrash FAQ (01.2013)
 * <p>
 * If Bane Alley Broker's first ability resolves when you have no cards in your
 * hand, you'll draw a card and then exile it. You won't have the opportunity to
 * cast that card (or do anything else with it) before exiling it.
 * <p>
 * Due to a recent rules change, once you are allowed to look at a face-down
 * card in exile, you are allowed to look at that card as long as it's exiled.
 * If you no longer control Bane Alley Broker when its last ability resolves,
 * you can continue to look at the relevant cards in exile to choose one to
 * return.
 * <p>
 * Bane Alley Broker's second and third abilities apply to cards exiled with
 * that specific Bane Alley Broker, not any other creature named Bane Alley
 * Broker. You should keep cards exiled by different Bane Alley Brokers
 * separate.
 * <p>
 * If Bane Alley Broker leaves the battlefield, the cards exiled with it will be
 * exiled indefinitely. If it later returns to the battlefield, it will be a new
 * object with no connection to the cards exiled with it in its previous
 * existence. You won't be able to use the "new" Bane Alley Broker to return
 * cards exiled with the "old" one.
 * <p>
 * Even if not all players can look at the exiled cards, each card's owner is
 * still known. It is advisable to keep cards owned by different players in
 * distinct piles in case another player gains control of Bane Alley Broker and
 * exiles one or more cards with it.
 *
 * @author LevelX2
 */
public final class BaneAlleyBroker extends CardImpl {

    public BaneAlleyBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ROGUE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {tap}: Draw a card, then exile a card from your hand face down.
        this.addAbility(new SimpleActivatedAbility(new BaneAlleyBrokerDrawExileEffect(), new TapSourceCost()));

        // You may look at cards exiled with Bane Alley Broker.
        this.addAbility(new SimpleStaticAbility(new BaneAlleyBrokerLookAtCardEffect()));

        // {U}{B}, {tap}: Return a card exiled with Bane Alley Broker to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new BaneAlleyBrokerReturnToHandEffect(), new ManaCostsImpl("{U}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BaneAlleyBroker(final BaneAlleyBroker card) {
        super(card);
    }

    @Override
    public BaneAlleyBroker copy() {
        return new BaneAlleyBroker(this);
    }
}

class BaneAlleyBrokerDrawExileEffect extends OneShotEffect {

    BaneAlleyBrokerDrawExileEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw a card, then exile a card from your hand face down";
    }

    private BaneAlleyBrokerDrawExileEffect(final BaneAlleyBrokerDrawExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(1, source, game);
        if (controller.getHand().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInHand().withChooseHint("to exile");
        controller.chooseTarget(outcome, controller.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        if (!controller.moveCardsToExile(
                card, source, game, false, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        )) {
            return false;
        }
        card.setFaceDown(true, game);
        return true;
    }

    @Override
    public BaneAlleyBrokerDrawExileEffect copy() {
        return new BaneAlleyBrokerDrawExileEffect(this);
    }
}

class BaneAlleyBrokerReturnToHandEffect extends OneShotEffect {

    BaneAlleyBrokerReturnToHandEffect() {
        super(Outcome.Benefit);
        staticText = "return a card exiled with {this} to its owner's hand";
    }

    private BaneAlleyBrokerReturnToHandEffect(final BaneAlleyBrokerReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public BaneAlleyBrokerReturnToHandEffect copy() {
        return new BaneAlleyBrokerReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exile == null || exile.isEmpty()) {
            return false;
        }
        TargetCardInExile target = new TargetCardInExile(StaticFilters.FILTER_CARD, exile.getId());
        target.setNotTarget(true);
        player.chooseTarget(outcome, exile, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}

class BaneAlleyBrokerLookAtCardEffect extends AsThoughEffectImpl {

    BaneAlleyBrokerLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    private BaneAlleyBrokerLookAtCardEffect(final BaneAlleyBrokerLookAtCardEffect effect) {
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
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return card != null && exile != null && exile.getCards(game).contains(card);
    }

}
