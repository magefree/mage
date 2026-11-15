package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author muz
 */
public final class TwistedFates extends CardImpl {

    public TwistedFates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}{B}");

        // Destroy target nonland permanent. Put a +1/+1 counter on each creature target player controls.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent().setTargetTag(1));
        this.getSpellAbility().addEffect(new TwistedFatesEffect());
        this.getSpellAbility().addTarget(new TargetPlayer().setTargetTag(2));
    }

    private TwistedFates(final TwistedFates card) {
        super(card);
    }

    @Override
    public TwistedFates copy() {
        return new TwistedFates(this);
    }
}

class TwistedFatesEffect extends OneShotEffect {

    TwistedFatesEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on each creature target player controls";
    }

    private TwistedFatesEffect(final TwistedFatesEffect effect) {
        super(effect);
    }

    @Override
    public TwistedFatesEffect copy() {
        return new TwistedFatesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetPlayer target = (TargetPlayer) source.getTargets().getByTag(2);
        Player player = target == null ? null : game.getPlayer(target.getFirstTarget());
        if (player == null) {
            return false;
        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(
            StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game
        )) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }
}
