
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SurveyorsScope extends CardImpl {

    public SurveyorsScope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}, Exile Surveyor's Scope: Search your library for up to X basic land cards, where X is the number of players who control at least two more lands than you. Put those cards onto the battlefield, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SurveyorsScopeEffect(), new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private SurveyorsScope(final SurveyorsScope card) {
        super(card);
    }

    @Override
    public SurveyorsScope copy() {
        return new SurveyorsScope(this);
    }
}

class SurveyorsScopeEffect extends OneShotEffect {

    public SurveyorsScopeEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Search your library for up to X basic land cards, where X is the number of players who control at least two more lands than you. Put those cards onto the battlefield, then shuffle";
    }

    public SurveyorsScopeEffect(final SurveyorsScopeEffect effect) {
        super(effect);
    }

    @Override
    public SurveyorsScopeEffect copy() {
        return new SurveyorsScopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int numberOfLands = 0;
            int ownLands = game.getBattlefield().countAll(new FilterLandPermanent(), controller.getId(), game);
            for (UUID playerId: game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controller.getId())) {
                    if (game.getBattlefield().countAll(new FilterLandPermanent(), playerId, game) > ownLands + 1) {
                        numberOfLands++;
                    }
                }
            }
            game.informPlayers("Surveyor's Scope: X = " + numberOfLands);
            // 10/17/2013 	If no players control at least two more lands than you when the ability resolves, you'll still search and shuffle your library.
            if (numberOfLands < 1) {
                player.shuffleLibrary(source, game);
                return true;
            }
            return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, numberOfLands, StaticFilters.FILTER_CARD_BASIC_LAND)).apply(game, source);
        }
        return false;
    }
}
