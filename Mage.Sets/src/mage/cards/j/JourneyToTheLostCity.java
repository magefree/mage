package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JourneyToTheLostCity extends CardImpl {

    public JourneyToTheLostCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of your upkeep, exile the top four cards of your library, then roll a d20.
        // 1-9 | You may put a land card from among those cards onto the battlefield.
        // 10-19 | Create a 2/2 green Wolf creature token, then put a +1/+1 counter on it for each creature card among those cards.
        // 20 | Put all permanent cards exiled with Journey to the Lost City onto the battlefield, then sacrifice it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new JourneyToTheLostCityEffect(), TargetController.YOU, false
        ));
    }

    private JourneyToTheLostCity(final JourneyToTheLostCity card) {
        super(card);
    }

    @Override
    public JourneyToTheLostCity copy() {
        return new JourneyToTheLostCity(this);
    }
}

class JourneyToTheLostCityEffect extends RollDieWithResultTableEffect {

    JourneyToTheLostCityEffect() {
        super(20, "exile the top four cards of your library, then roll a d20");
        this.addTableEntry(
                1, 9,
                new InfoEffect("you may put a land card from among those cards onto the battlefield")
        );
        this.addTableEntry(
                10, 19,
                new InfoEffect("create a 2/2 green Wolf creature token, " +
                        "then put a +1/+1 counter on it for each creature card among those cards")
        );
        this.addTableEntry(
                20, 20,
                new InfoEffect("put all permanent cards exiled with {this} onto the battlefield, then sacrifice it")
        );
    }

    private JourneyToTheLostCityEffect(final JourneyToTheLostCityEffect effect) {
        super(effect);
    }

    @Override
    public JourneyToTheLostCityEffect copy() {
        return new JourneyToTheLostCityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        int amount = player.rollDice(outcome, source, game, 20);
        if (amount < 1) {
            return false;
        }
        if (amount <= 9) {
            TargetCard target = new TargetCardInExile(0, 1, StaticFilters.FILTER_CARD_LAND, null);
            target.setNotTarget(true);
            player.choose(outcome, cards, target, game);
            Card card = game.getCard(target.getFirstTarget());
            return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        if (amount <= 19) {
            Token token = new WolfToken();
            token.putOntoBattlefield(1, game, source);
            int count = cards.count(StaticFilters.FILTER_CARD_CREATURE, game);
            if (count > 0) {
                for (UUID tokenId : token.getLastAddedTokenIds()) {
                    Optional.of(game.getPermanent(tokenId))
                            .ifPresent(permanent -> permanent.addCounters(
                                    CounterType.P1P1.createInstance(count), source, game
                            ));
                }
            }
            return true;
        }
        if (amount != 20) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone != null && !exileZone.isEmpty()) {
            player.moveCards(exileZone.getCards(
                    StaticFilters.FILTER_CARD_PERMANENT, game
            ), Zone.BATTLEFIELD, source, game);
        }
        Optional.of(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> permanent.sacrifice(source, game));
        return true;
    }
}
