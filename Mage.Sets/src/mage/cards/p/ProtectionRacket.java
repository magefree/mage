package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProtectionRacket extends CardImpl {

    public ProtectionRacket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of your upkeep, repeat the following process for each opponent in turn order. Reveal the top card of your library. That player pay pay life equal to that card's mana value. If they do, exile that card. Otherwise, put it into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ProtectionRacketEffect(), TargetController.YOU, false
        ));
    }

    private ProtectionRacket(final ProtectionRacket card) {
        super(card);
    }

    @Override
    public ProtectionRacket copy() {
        return new ProtectionRacket(this);
    }
}

class ProtectionRacketEffect extends OneShotEffect {

    ProtectionRacketEffect() {
        super(Outcome.Benefit);
        staticText = "repeat the following process for each opponent in turn order. " +
                "Reveal the top card of your library. That player may pay life equal to that card's mana value. " +
                "If they do, exile that card. Otherwise, put it into your hand";
    }

    private ProtectionRacketEffect(final ProtectionRacketEffect effect) {
        super(effect);
    }

    @Override
    public ProtectionRacketEffect copy() {
        return new ProtectionRacketEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            Card card = controller.getLibrary().getFromTop(game);
            if (player == null || card == null) {
                continue;
            }
            controller.revealCards(source, new CardsImpl(card), game);
            int mv = card.getManaValue();
            Cost cost = new PayLifeCost(mv);
            if (cost.canPay(source, source, playerId, game) && player.chooseUse(
                    Outcome.Detriment, "Pay " + mv + " life to exile " + card.getName() + '?', source, game
            )) {
                cost.pay(source, game, source, playerId, true);
                controller.moveCards(card, Zone.EXILED, source, game);
            }
            controller.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
