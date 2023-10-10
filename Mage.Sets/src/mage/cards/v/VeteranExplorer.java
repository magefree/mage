package mage.cards.v;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class VeteranExplorer extends CardImpl {

    public VeteranExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Veteran Explorer dies, each player may search their library for up to two basic land cards and put them onto the battlefield. Then each player who searched their library this way shuffles it.
        this.addAbility(new DiesSourceTriggeredAbility(new VeteranExplorerEffect()));
    }

    private VeteranExplorer(final VeteranExplorer card) {
        super(card);
    }

    @Override
    public VeteranExplorer copy() {
        return new VeteranExplorer(this);
    }
}

class VeteranExplorerEffect extends OneShotEffect {

    public VeteranExplorerEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player may search their library for up to two basic land cards, " +
                "put them onto the battlefield, then shuffle";
    }

    private VeteranExplorerEffect(final VeteranExplorerEffect effect) {
        super(effect);
    }

    @Override
    public VeteranExplorerEffect copy() {
        return new VeteranExplorerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> toBattlefield = new HashSet<>();
        List<Player> usingPlayers = new ArrayList<>();
        if (controller != null) {
            toBattlefield.addAll(this.chooseAndSearchLibrary(usingPlayers, controller, source, game));
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null
                        && !player.equals(controller)) {
                    toBattlefield.addAll(this.chooseAndSearchLibrary(usingPlayers, player, source, game));
                }
            }

            // must happen simultaneously Rule 101.4
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);

            // only those players that searched can shuffle
            for (Player player : usingPlayers) {
                if (player != null) {
                    player.shuffleLibrary(source, game);
                }
            }
            return true;
        }
        return false;
    }

    private Set<Card> chooseAndSearchLibrary(List<Player> usingPlayers, Player player, Ability source, Game game) {
        Set<Card> toBattlefield = new HashSet<>();
        if (player.chooseUse(Outcome.PutCardInPlay, "Search your library for up to two basic land cards and put them onto the battlefield?", source, game)) {
            usingPlayers.add(player);
            TargetCardInLibrary target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND);
            if (player.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    toBattlefield.addAll(new CardsImpl(target.getTargets()).getCards(game));
                }
            }
        }
        return toBattlefield;
    }

}
