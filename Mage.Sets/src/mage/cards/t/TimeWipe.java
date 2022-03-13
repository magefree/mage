package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TimeWipe extends CardImpl {

    public TimeWipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}{U}");

        // Return a creature you control to its owner's hand, then destroy all creatures.
        this.getSpellAbility().addEffect(new TimeWipeEffect());
    }

    private TimeWipe(final TimeWipe card) {
        super(card);
    }

    @Override
    public TimeWipe copy() {
        return new TimeWipe(this);
    }
}

class TimeWipeEffect extends OneShotEffect {

    TimeWipeEffect() {
        super(Outcome.Benefit);
        staticText = "Return a creature you control to its owner's hand, then destroy all creatures.";
    }

    private TimeWipeEffect(final TimeWipeEffect effect) {
        super(effect);
    }

    @Override
    public TimeWipeEffect copy() {
        return new TimeWipeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (player.choose(outcome, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                player.moveCards(permanent, Zone.HAND, source, game);
            }
        }
        return new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE).apply(game, source);
    }
}