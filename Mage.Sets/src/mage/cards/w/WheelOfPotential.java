package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WheelOfPotential extends CardImpl {

    public WheelOfPotential(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // You get {E}{E}{E}, then you may pay X {E}.
        // Each player may exile their hand and draw X cards. If X is 7 or more, you may play cards you own exiled this way until the end of your next turn.
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(3));
        this.getSpellAbility().addEffect(new WheelOfPotentialEffect());
    }

    private WheelOfPotential(final WheelOfPotential card) {
        super(card);
    }

    @Override
    public WheelOfPotential copy() {
        return new WheelOfPotential(this);
    }
}

class WheelOfPotentialEffect extends OneShotEffect {

    WheelOfPotentialEffect() {
        super(Outcome.DrawCard);
        staticText = ", then you may pay X {E}.<br>Each player may exile their hand and draw X cards. "
                + "If X is 7 or more, you may play cards you own exiled this way until the end of your next turn.";
    }

    private WheelOfPotentialEffect(final WheelOfPotentialEffect effect) {
        super(effect);
    }

    @Override
    public WheelOfPotentialEffect copy() {
        return new WheelOfPotentialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int numberToPay = controller.getAmount(
                0, controller.getCountersCount(CounterType.ENERGY),
                "How many {E} do you want to pay?", game
        );
        Cost cost = new PayEnergyCost(numberToPay);
        int numberPaid = 0;
        if (cost.pay(source, game, source, controller.getId(), true)) {
            numberPaid = numberToPay;
        }
        Cards cardsExiled = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (playerId == null) {
                continue;
            }
            Outcome outcome = player.getHand().size() > numberPaid
                    ? Outcome.Discard : Outcome.DrawCard;
            if (!player.chooseUse(outcome, "Exile your hand and draw " + numberPaid + "?", source, game)) {
                game.informPlayers(player.getLogName() + " chose to not exile their hand");
                continue;
            }
            cardsExiled.addAll(player.getHand());
            player.moveCardsToExile(player.getHand().getCards(game), source, game, true, null, "");
            player.drawCards(numberPaid, source, game);
        }
        if (numberPaid >= 7) {
            game.getState().processAction(game);
            cardsExiled.removeIf(cardId -> {
                Card card = game.getCard(cardId);
                return card == null || !card.getOwnerId().equals(controller.getId());
            });
            cardsExiled.retainZone(Zone.EXILED, game);
            String exileName = CardUtil.getSourceIdName(game, source);
            UUID exileId = CardUtil.getExileZoneId(game, source);
            ExileZone exileZone = game.getExile().createZone(exileId, exileName);
            for (Card card : cardsExiled.getCards(game)) {
                game.getExile().moveToAnotherZone(card, game, exileZone);
                CardUtil.makeCardPlayable(game, source, card, false, Duration.UntilEndOfYourNextTurn, false);
            }
        }
        return true;
    }

}