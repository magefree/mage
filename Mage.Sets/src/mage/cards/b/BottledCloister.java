package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BottledCloister extends CardImpl {

    public BottledCloister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of each opponent's upkeep, exile all cards from your hand face down.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new BottledCloisterExileEffect(), TargetController.OPPONENT, false
        ));

        // At the beginning of your upkeep, return all cards you own exiled with Bottled Cloister to your hand, then draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new BottledCloisterReturnEffect(), TargetController.YOU, false
        ));
    }

    private BottledCloister(final BottledCloister card) {
        super(card);
    }

    @Override
    public BottledCloister copy() {
        return new BottledCloister(this);
    }
}

class BottledCloisterExileEffect extends OneShotEffect {

    BottledCloisterExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile all cards from your hand face down";
    }

    private BottledCloisterExileEffect(final BottledCloisterExileEffect effect) {
        super(effect);
    }

    @Override
    public BottledCloisterExileEffect copy() {
        return new BottledCloisterExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getHand());
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCardsToExile(cards.getCards(game), source, game, false, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        cards.getCards(game)
                .stream()
                .filter(c -> game.getState().getZone(c.getId()) == Zone.EXILED)
                .forEach(card -> card.setFaceDown(true, game));
        return true;
    }
}

class BottledCloisterReturnEffect extends OneShotEffect {

    BottledCloisterReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return all cards you own exiled with {this} to your hand, then draw a card";
    }

    private BottledCloisterReturnEffect(final BottledCloisterReturnEffect effect) {
        super(effect);
    }

    @Override
    public BottledCloisterReturnEffect copy() {
        return new BottledCloisterReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(exileZone.getCards(game));
        cards.removeIf(uuid -> !player.getId().equals(game.getOwnerId(uuid)));
        player.moveCards(cards, Zone.HAND, source, game);
        player.drawCards(1, source, game);
        return true;
    }
}
