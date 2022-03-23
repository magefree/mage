package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheElderspell extends CardImpl {

    public TheElderspell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Destroy any number of target planeswalkers. Choose a planeswalker you control. Put two loyalty counters on it for each planeswalker destroyed this way.
        this.getSpellAbility().addEffect(new TheElderspellEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_PLANESWALKERS, false
        ));
    }

    private TheElderspell(final TheElderspell card) {
        super(card);
    }

    @Override
    public TheElderspell copy() {
        return new TheElderspell(this);
    }
}

class TheElderspellEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent();

    TheElderspellEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy any number of target planeswalkers. Choose a planeswalker you control. " +
                "Put two loyalty counters on it for each planeswalker destroyed this way.";
    }

    private TheElderspellEffect(final TheElderspellEffect effect) {
        super(effect);
    }

    @Override
    public TheElderspellEffect copy() {
        return new TheElderspellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = 0;
        for (UUID permId : source.getTargets().get(0).getTargets()) {
            Permanent permanent = game.getPermanent(permId);
            if (permanent != null && permanent.destroy(source, game, false)) {
                count++;
            }
        }
        TargetPermanent targetPermanent = new TargetPermanent(filter);
        if (!player.choose(outcome, targetPermanent, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(targetPermanent.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return permanent.addCounters(CounterType.LOYALTY.createInstance(2 * count), source.getControllerId(), source, game);
    }
}