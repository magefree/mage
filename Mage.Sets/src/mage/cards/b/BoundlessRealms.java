
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
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
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class BoundlessRealms extends CardImpl {

    public BoundlessRealms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{G}");

        // Search your library for up to X basic land cards, where X is the number of lands you control, and put them onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new BoundlessRealmsEffect());
    }

    private BoundlessRealms(final BoundlessRealms card) {
        super(card);
    }

    @Override
    public BoundlessRealms copy() {
        return new BoundlessRealms(this);
    }
}

class BoundlessRealmsEffect extends OneShotEffect {

    public BoundlessRealmsEffect() {
        super(Outcome.Benefit);
        this.staticText = "search your library for up to X basic land cards, where X is the number of " +
                "lands you control, put them onto the battlefield tapped, then shuffle";
    }

    private BoundlessRealmsEffect(final BoundlessRealmsEffect effect) {
        super(effect);
    }

    @Override
    public BoundlessRealmsEffect copy() {
        return new BoundlessRealmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterLandPermanent filter = new FilterLandPermanent();
        filter.add(TargetController.YOU.getControllerPredicate());

        int amount = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
        TargetCardInLibrary target = new TargetCardInLibrary(0, amount, StaticFilters.FILTER_CARD_BASIC_LAND);
        if (controller.searchLibrary(target, source, game)) {
            controller.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
