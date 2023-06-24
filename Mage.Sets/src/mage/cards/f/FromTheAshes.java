
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
        if (controller == null) {
            return false;
        }
        // Destroy all nonbasic lands.
        Map<UUID, Integer> playerAmount = new HashMap<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LANDS_NONBASIC, source.getControllerId(), source, game)) {
            UUID controllerId = permanent.getControllerId();
            if (permanent.destroy(source, game, false)) {
                playerAmount.merge(controllerId, 1, Integer::sum);
            }
        }
        game.getState().processAction(game);

        // For each land destroyed this way, its controller may search their library for a basic land card and put it onto the battlefield.
        for (Map.Entry<UUID, Integer> entry : playerAmount.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            TargetCardInLibrary target = new TargetCardInLibrary(0, entry.getValue(),
                    entry.getValue() > 1 ? StaticFilters.FILTER_CARD_BASIC_LANDS : StaticFilters.FILTER_CARD_BASIC_LAND);
            if (player.chooseUse(Outcome.PutLandInPlay, "Search your library for " + target.getDescription() + '?', source, game)) {
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

        // Then each player who searched their library this way shuffles. 
        for (Map.Entry<UUID, Integer> entry : playerAmount.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player != null && entry.getValue() > 0) {
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }
}
