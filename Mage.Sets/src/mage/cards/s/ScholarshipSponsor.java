package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ScholarshipSponsor extends CardImpl {

    public ScholarshipSponsor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Scholarship Sponsor enters the battlefield, each player who controls fewer lands than the player who controls the most lands searches their library for a number of basic land cards less than or equal to the difference, puts those cards onto the battlefield tapped, then shuffles.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScholarshipSponsorEffect()));
    }

    private ScholarshipSponsor(final ScholarshipSponsor card) {
        super(card);
    }

    @Override
    public ScholarshipSponsor copy() {
        return new ScholarshipSponsor(this);
    }
}

class ScholarshipSponsorEffect extends OneShotEffect {

    ScholarshipSponsorEffect() {
        super(Outcome.Benefit);
        staticText = "each player who controls fewer lands than the player who controls the most lands " +
                "searches their library for a number of basic land cards less than or equal to the difference, " +
                "puts those cards onto the battlefield tapped, then shuffles";
    }

    private ScholarshipSponsorEffect(final ScholarshipSponsorEffect effect) {
        super(effect);
    }

    @Override
    public ScholarshipSponsorEffect copy() {
        return new ScholarshipSponsorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> playerMap = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_LAND, source.getControllerId(), source, game
                )
                .stream()
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
        int maxValue = playerMap
                .values()
                .stream()
                .mapToInt(x -> x)
                .max()
                .orElse(0);
        if (maxValue < 1) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            int diff = maxValue - playerMap.getOrDefault(playerId, 0);
            if (player == null || diff < 1) {
                continue;
            }
            TargetCardInLibrary target = new TargetCardInLibrary(diff, StaticFilters.FILTER_CARD_BASIC_LAND);
            player.searchLibrary(target, source, game);
            Cards cards = new CardsImpl(target.getTargets());
            cards.retainZone(Zone.LIBRARY, game);
            player.moveCards(
                    cards.getCards(game), Zone.BATTLEFIELD, source, game,
                    true, false, false, null
            );
            player.shuffleLibrary(source, game);
        }
        return true;
    }
}
