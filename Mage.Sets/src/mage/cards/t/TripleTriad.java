package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TripleTriad extends CardImpl {

    public TripleTriad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}{R}");

        // At the beginning of your upkeep, each player exiles the top card of their library. Until end of turn, you may play the card you own exiled this way and each other card exiled this way with lesser mana value than it without paying their mana costs.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TripleTriadEffect()));
    }

    private TripleTriad(final TripleTriad card) {
        super(card);
    }

    @Override
    public TripleTriad copy() {
        return new TripleTriad(this);
    }
}

class TripleTriadEffect extends OneShotEffect {

    TripleTriadEffect() {
        super(Outcome.Benefit);
        staticText = "each player exiles the top card of their library. Until end of turn, " +
                "you may play the card you own exiled this way and each other card exiled this way " +
                "with lesser mana value than it without paying their mana costs";
    }

    private TripleTriadEffect(final TripleTriadEffect effect) {
        super(effect);
    }

    @Override
    public TripleTriadEffect copy() {
        return new TripleTriadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                return false;
            }
            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            player.moveCards(card, Zone.EXILED, source, game);
            cards.add(card);
        }
        Card card = cards
                .getCards(game)
                .stream()
                .filter(c -> c.isOwnedBy(source.getControllerId()))
                .findAny()
                .orElse(null);
        if (card == null) {
            return true;
        }
        cards.removeIf(uuid -> game.getCard(uuid).getManaValue() >= card.getManaValue());
        cards.add(card);
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, true, false
        ).setTargetPointer(new FixedTargets(cards, game)), source);
        return true;
    }
}
