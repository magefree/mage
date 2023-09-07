
package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public final class TraverseTheOutlands extends CardImpl {

    public TraverseTheOutlands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Search your library for up to X basic land cards, where X is the greatest power among creatures you control. Put those cards onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new TraverseTheOutlandsEffect());
    }

    private TraverseTheOutlands(final TraverseTheOutlands card) {
        super(card);
    }

    @Override
    public TraverseTheOutlands copy() {
        return new TraverseTheOutlands(this);
    }
}

class TraverseTheOutlandsEffect extends OneShotEffect {

    public TraverseTheOutlandsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to X basic land cards, where X is the greatest power among creatures you control. Put those cards onto the battlefield tapped, then shuffle.";
    }

    private TraverseTheOutlandsEffect(final TraverseTheOutlandsEffect effect) {
        super(effect);
    }

    @Override
    public TraverseTheOutlandsEffect copy() {
        return new TraverseTheOutlandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterLandPermanent filter = new FilterLandPermanent();
        filter.add(TargetController.YOU.getControllerPredicate());

        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game);
        int amount = 0;
        for (Permanent creature : creatures) {
            int power = creature.getPower().getValue();
            if (amount < power) {
                amount = power;
            }
        }

        TargetCardInLibrary target = new TargetCardInLibrary(0, amount, StaticFilters.FILTER_CARD_BASIC_LAND);
        if (controller.searchLibrary(target, source, game)) {
            controller.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
