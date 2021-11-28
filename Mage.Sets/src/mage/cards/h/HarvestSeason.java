
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Styxo
 */
public final class HarvestSeason extends CardImpl {

    public HarvestSeason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for up to X basic land cards, where X is the number of tapped creatures you control,
        // and put those card onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new HarvestSeasonEffect());

    }

    private HarvestSeason(final HarvestSeason card) {
        super(card);
    }

    @Override
    public HarvestSeason copy() {
        return new HarvestSeason(this);
    }
}

class HarvestSeasonEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    HarvestSeasonEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to X basic land cards, where X is the number of tapped creatures you control,"
                + " put those cards onto the battlefield tapped, then shuffle.";
    }

    HarvestSeasonEffect(final HarvestSeasonEffect effect) {
        super(effect);
    }

    @Override
    public HarvestSeasonEffect copy() {
        return new HarvestSeasonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int tappedCreatures = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
            if (tappedCreatures > 0) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, tappedCreatures, StaticFilters.FILTER_CARD_BASIC_LAND);
                if (controller.searchLibrary(target, source, game)) {
                    controller.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
