
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class TwistAllegiance extends CardImpl {

    public TwistAllegiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}");

        // You and target opponent each gain control of all creatures the other controls until end of turn. Untap those creatures. Those creatures gain haste until end of turn.
        this.getSpellAbility().addEffect(new TwistAllegianceEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private TwistAllegiance(final TwistAllegiance card) {
        super(card);
    }

    @Override
    public TwistAllegiance copy() {
        return new TwistAllegiance(this);
    }
}

class TwistAllegianceEffect extends OneShotEffect {

    public TwistAllegianceEffect() {
        super(Outcome.Detriment);
        this.staticText = "You and target opponent each gain control of all creatures the other controls until end of turn. Untap those creatures. Those creatures gain haste until end of turn";
    }

    public TwistAllegianceEffect(final TwistAllegianceEffect effect) {
        super(effect);
    }

    @Override
    public TwistAllegianceEffect copy() {
        return new TwistAllegianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                // only creatures of controller & target opponent
                if (permanent.isControlledBy(source.getControllerId()) || permanent.isControlledBy(targetOpponent.getId())) {
                    UUID newController = permanent.isControlledBy(source.getControllerId()) ? targetOpponent.getId() : source.getControllerId();
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn, true, newController);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                    permanent.untap(game);
                    effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
