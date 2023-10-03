
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class JoragaInvocation extends CardImpl {

    public JoragaInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Each creature you control gets +3/+3 until end of turn and must be blocked this turn if able.
        this.getSpellAbility().addEffect(new BoostControlledEffect(3, 3, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new JoragaInvocationEffect());

    }

    private JoragaInvocation(final JoragaInvocation card) {
        super(card);
    }

    @Override
    public JoragaInvocation copy() {
        return new JoragaInvocation(this);
    }
}

class JoragaInvocationEffect extends OneShotEffect {

    public JoragaInvocationEffect() {
        super(Outcome.Detriment);
        this.staticText = "and must be blocked this turn if able";
    }

    private JoragaInvocationEffect(final JoragaInvocationEffect effect) {
        super(effect);
    }

    @Override
    public JoragaInvocationEffect copy() {
        return new JoragaInvocationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
                ContinuousEffect effect = new MustBeBlockedByAtLeastOneTargetEffect();
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
