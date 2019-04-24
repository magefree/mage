package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class AssassinsTrophy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public AssassinsTrophy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // Destroy target permanent an opponent controls. Its controller may search their library for a basic land card, put it onto the battlefield, then shuffle their library.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new AssassinsTrophyEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public AssassinsTrophy(final AssassinsTrophy card) {
        super(card);
    }

    @Override
    public AssassinsTrophy copy() {
        return new AssassinsTrophy(this);
    }
}

class AssassinsTrophyEffect extends OneShotEffect {

    public AssassinsTrophyEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Its controller may search their library "
                + "for a basic land card, put it onto the battlefield, "
                + "then shuffle their library";
    }

    public AssassinsTrophyEffect(final AssassinsTrophyEffect effect) {
        super(effect);
    }

    @Override
    public AssassinsTrophyEffect copy() {
        return new AssassinsTrophyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller.chooseUse(Outcome.PutLandInPlay, "Do you wish to search for a basic land, put it onto the battlefield and then shuffle your library?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
                if (controller.searchLibrary(target, game)) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
