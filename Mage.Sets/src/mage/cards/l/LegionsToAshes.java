package mage.cards.l;

import java.util.HashSet;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author weirddan455
 */
public final class LegionsToAshes extends CardImpl {

    public LegionsToAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{B}");

        // Exile target nonland permanent an opponent controls and all tokens that player controls with the same name as that permanent.
        this.getSpellAbility().addEffect(new LegionsToAshesEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
    }

    private LegionsToAshes(final LegionsToAshes card) {
        super(card);
    }

    @Override
    public LegionsToAshes copy() {
        return new LegionsToAshes(this);
    }
}

class LegionsToAshesEffect extends OneShotEffect {

    public LegionsToAshesEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target nonland permanent an opponent controls and all tokens that player controls with the same name as that permanent.";
    }

    private LegionsToAshesEffect(final LegionsToAshesEffect effect) {
        super(effect);
    }

    @Override
    public LegionsToAshesEffect copy() {
        return new LegionsToAshesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || targetPermanent == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent();
        filter.add(TokenPredicate.TRUE);
        filter.add(new ControllerIdPredicate(targetPermanent.getControllerId()));
        filter.add(new NamePredicate(targetPermanent.getName()));
        HashSet<Permanent> toExile = new HashSet<>(game.getBattlefield().getActivePermanents(filter, controller.getId(), source, game));
        toExile.add(targetPermanent);
        controller.moveCards(toExile, Zone.EXILED, source, game);
        return true;
    }
}
