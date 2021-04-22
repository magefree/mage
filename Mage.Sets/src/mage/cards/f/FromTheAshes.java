
package mage.cards.f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class FromTheAshes extends CardImpl {

    public FromTheAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Destroy all nonbasic lands. For each land destroyed this way, its controller may search their library for a basic land card and put it onto the battlefield. Then each player who searched their library this way shuffles it.
        this.getSpellAbility().addEffect(new FromTheAshesEffect());
    }

    private FromTheAshes(final FromTheAshes card) {
        super(card);
    }

    @Override
    public FromTheAshes copy() {
        return new FromTheAshes(this);
    }
}

class FromTheAshesEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = FilterLandPermanent.nonbasicLands();

    public FromTheAshesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy all nonbasic lands. For each land destroyed this way, its controller may search their library for a basic land card and put it onto the battlefield. Then each player who searched their library this way shuffles";
    }

    public FromTheAshesEffect(final FromTheAshesEffect effect) {
        super(effect);
    }

    @Override
    public FromTheAshesEffect copy() {
        return new FromTheAshesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> playerAmount = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int amount = 0;
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        amount++;
                        permanent.destroy(source, game, false);
                    }
                    playerAmount.put(playerId, amount);
                }
            }
            game.getState().processAction(game);
            for (Map.Entry<UUID, Integer> entry : playerAmount.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null && player.chooseUse(outcome, "Search your library for up to " + entry.getValue() + " basic land card(s) to put it onto the battlefield?", source, game)) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, entry.getValue(), StaticFilters.FILTER_CARD_BASIC_LAND);
                    if (player.searchLibrary(target, source, game)) {
                        if (!target.getTargets().isEmpty()) {
                            player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
                        }
                    }
                } else {
                    entry.setValue(0); // no search no shuffling
                }
            }
            game.getState().processAction(game);
            for (Map.Entry<UUID, Integer> entry : playerAmount.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null && entry.getValue() > 0) {
                    player.shuffleLibrary(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
