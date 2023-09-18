package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetDiscard;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CourtOfAmbition extends CardImpl {

    public CourtOfAmbition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // When Court of Ambition enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()));

        // At the beginning of your upkeep, each opponent loses 3 life unless they discard a card. If you're the monarch, instead each opponent loses 6 life unless they discard two cards.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CourtOfAmbitionEffect(), TargetController.YOU, false
        ));
    }

    private CourtOfAmbition(final CourtOfAmbition card) {
        super(card);
    }

    @Override
    public CourtOfAmbition copy() {
        return new CourtOfAmbition(this);
    }
}

class CourtOfAmbitionEffect extends OneShotEffect {

    CourtOfAmbitionEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses 3 life unless they discard a card. " +
                "If you're the monarch, instead each opponent loses 6 life unless they discard two cards";
    }

    private CourtOfAmbitionEffect(final CourtOfAmbitionEffect effect) {
        super(effect);
    }

    @Override
    public CourtOfAmbitionEffect copy() {
        return new CourtOfAmbitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int discardCount = source.isControlledBy(game.getMonarchId()) ? 2 : 1;
        String message = "Discard " + CardUtil.numberToText(discardCount, "a")
                + " card" + (discardCount > 1 ? 's' : "") + "? If not you lose " + (discardCount * 3) + " life";
        Map<UUID, Cards> discardMap = new HashMap<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (player.getHand().size() < discardCount || !player.chooseUse(Outcome.LoseLife, message, source, game)) {
                player.loseLife(discardCount * 3, game, source, false);
                continue;
            }
            TargetDiscard target = new TargetDiscard(discardCount, StaticFilters.FILTER_CARD, playerId);
            player.choose(Outcome.Discard, target, source, game);
            discardMap.put(playerId, new CardsImpl(target.getTargets()));
        }
        for (Map.Entry<UUID, Cards> entry : discardMap.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player != null) {
                player.discard(entry.getValue(), false, source, game);
            }
        }
        return true;
    }
}
