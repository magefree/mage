
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author TheElk801
 */
public final class FieldOfRuin extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("nonbasic land an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    public FieldOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}, Sacrifice Field of Ruin: Destroy target nonbasic land an opponent controls. Each player searches their library for a basic land card, puts it onto the battlefield, then shuffles their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new FieldOfRuinEffect());
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);
    }

    private FieldOfRuin(final FieldOfRuin card) {
        super(card);
    }

    @Override
    public FieldOfRuin copy() {
        return new FieldOfRuin(this);
    }
}

class FieldOfRuinEffect extends OneShotEffect {

    FieldOfRuinEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player searches their library for a basic land card, puts it onto the battlefield, then shuffles";
    }

    FieldOfRuinEffect(final FieldOfRuinEffect effect) {
        super(effect);
    }

    @Override
    public FieldOfRuinEffect copy() {
        return new FieldOfRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND);
                    if (player.searchLibrary(target, source, game)) {
                        player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game);
                        player.shuffleLibrary(source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
