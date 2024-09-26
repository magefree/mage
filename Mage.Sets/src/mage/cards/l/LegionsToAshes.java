package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
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

    LegionsToAshesEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target nonland permanent an opponent controls " +
                "and all tokens that player controls with the same name as that permanent.";
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
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPermanent == null) {
            return false;
        }
        Set<Card> set = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_TOKEN,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.sharesName(targetPermanent, game)
                        && permanent.isControlledBy(targetPermanent.getControllerId()))
                .collect(Collectors.toSet());
        set.add(targetPermanent);
        return controller.moveCards(set, Zone.EXILED, source, game);
    }
}
