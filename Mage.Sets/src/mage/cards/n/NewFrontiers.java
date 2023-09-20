
package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import mage.cards.CardsImpl;

/**
 *
 * @author spjspj
 */
public final class NewFrontiers extends CardImpl {

    public NewFrontiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Each player may search their library for up to X basic land cards and put them onto the battlefield tapped. Then each player who searched their library this way shuffles it.
        this.getSpellAbility().addEffect(new NewFrontiersEffect());
    }

    private NewFrontiers(final NewFrontiers card) {
        super(card);
    }

    @Override
    public NewFrontiers copy() {
        return new NewFrontiers(this);
    }
}

class NewFrontiersEffect extends OneShotEffect {

    public NewFrontiersEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player may search their library for up to X basic land cards and put them onto the battlefield tapped. Then each player who searched their library this way shuffles";
    }

    private NewFrontiersEffect(final NewFrontiersEffect effect) {
        super(effect);
    }

    @Override
    public NewFrontiersEffect copy() {
        return new NewFrontiersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = source.getManaCostsToPay().getX();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.chooseUse(outcome, "Search your library for up to " + amount + " basic lands?", source, game)) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, amount, StaticFilters.FILTER_CARD_BASIC_LAND);
                    if (player.searchLibrary(target, source, game)) {
                        player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
                        player.shuffleLibrary(source, game);
                    }

                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
