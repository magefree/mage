package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Rene - bugisemail at gmail dot com
 */
public final class NaturalBalance extends CardImpl {

    public NaturalBalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Each player who controls six or more lands chooses five lands they control and sacrifices the rest. Each player who controls four or fewer lands may search their library for up to X basic land cards and put them onto the battlefield, where X is five minus the number of lands they control. Then each player who searched their library this way shuffles it.
        this.getSpellAbility().addEffect(new NaturalBalanceEffect());
    }

    private NaturalBalance(final NaturalBalance card) {
        super(card);
    }

    @Override
    public NaturalBalance copy() {
        return new NaturalBalance(this);
    }

    class NaturalBalanceEffect extends OneShotEffect {

        public NaturalBalanceEffect() {
            super(Outcome.PutCardInPlay);
            this.staticText = "Each player who controls six or more lands chooses five lands they control and sacrifices the rest. Each player who controls four or fewer lands may search their library for up to X basic land cards and put them onto the battlefield, where X is five minus the number of lands they control. Then each player who searched their library this way shuffles.";
        }

        public NaturalBalanceEffect(final NaturalBalanceEffect effect) {
            super(effect);
        }

        @Override
        public NaturalBalanceEffect copy() {
            return new NaturalBalanceEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                PlayerList players = game.getState().getPlayersInRange(controller.getId(), game);
                for (UUID playerId : players) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        int landCount = game.getBattlefield().countAll(new FilterControlledLandPermanent(), player.getId(), game);
                        if (landCount > 5) {
                            // chooses five lands they control and sacrifices the rest
                            TargetControlledPermanent target = new TargetControlledPermanent(5, 5, new FilterControlledLandPermanent("lands to keep"), true);
                            if (target.choose(Outcome.Sacrifice, player.getId(), source.getSourceId(), source, game)) {
                                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), player.getId(), game)) {
                                    if (!target.getTargets().contains(permanent.getId())) {
                                        permanent.sacrifice(source, game);
                                    }
                                }
                            }
                        }
                    }
                }
                List<Player> toShuffle = new ArrayList<>();
                for (UUID playerId : players) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        int landCount = game.getBattlefield().countAll(new FilterControlledLandPermanent(), player.getId(), game);
                        int amount = 5 - landCount;
                        if (landCount < 5 && player.chooseUse(outcome, "Search your library for up to " + amount + " basic land cards and put them onto the battlefield?", source, game)) {
                            // Select lands and put them onto battlefield
                            TargetCardInLibrary target = new TargetCardInLibrary(0, amount, StaticFilters.FILTER_CARD_BASIC_LAND);
                            if (player.searchLibrary(target, source, game)) {
                                player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game);
                            }
                            toShuffle.add(player);
                        }
                    }
                }
                for (Player player : toShuffle) {
                    player.shuffleLibrary(source, game);
                }
                return true;
            }
            return false;
        }
    }
}
