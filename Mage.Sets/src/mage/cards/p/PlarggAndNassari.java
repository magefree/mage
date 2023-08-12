package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlarggAndNassari extends CardImpl {

    public PlarggAndNassari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, each player exiles cards from the top of their library until they exile a nonland card. An opponent chooses a nonland card exiled this way. You may cast up to two spells from among the other cards exiled this way without paying their mana costs.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new PlarggAndNassariEffect(), TargetController.YOU, false
        ));
    }

    private PlarggAndNassari(final PlarggAndNassari card) {
        super(card);
    }

    @Override
    public PlarggAndNassari copy() {
        return new PlarggAndNassari(this);
    }
}

class PlarggAndNassariEffect extends OneShotEffect {

    PlarggAndNassariEffect() {
        super(Outcome.Benefit);
        staticText = "each player exiles cards from the top of their library until they exile a nonland card. " +
                "An opponent chooses a nonland card exiled this way. You may cast up to two spells " +
                "from among the other cards exiled this way without paying their mana costs";
    }

    private PlarggAndNassariEffect(final PlarggAndNassariEffect effect) {
        super(effect);
    }

    @Override
    public PlarggAndNassariEffect copy() {
        return new PlarggAndNassariEffect(this);
    }

    private static Player getOpponent(Player controller, Ability source, Game game) {
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.size() < 2) {
            return game.getPlayer(RandomUtil.randomFromCollection(opponents));
        }
        TargetOpponent target = new TargetOpponent(true);
        controller.choose(Outcome.Neutral, target, source, game);
        return game.getPlayer(target.getFirstTarget());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            for (Card card : player.getLibrary().getCards(game)) {
                cards.add(card);
                player.moveCards(card, Zone.EXILED, source, game);
                if (!card.isLand(game)) {
                    break;
                }
            }
        }
        if (cards.isEmpty()) {
            return false;
        }
        Player opponent = getOpponent(controller, source, game);
        if (opponent != null) {
            TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_NON_LAND);
            target.setNotTarget(true);
            opponent.choose(outcome, cards, target, source, game);
            cards.remove(game.getCard(target.getFirstTarget()));
        }
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD, 2);
        return true;
    }
}
