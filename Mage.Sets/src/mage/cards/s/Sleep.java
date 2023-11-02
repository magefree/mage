
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Sleep extends CardImpl {

    public Sleep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new SleepEffect());
    }

    private Sleep(final Sleep card) {
        super(card);
    }

    @Override
    public Sleep copy() {
        return new Sleep(this);
    }
}

class SleepEffect extends OneShotEffect {

    public SleepEffect() {
        super(Outcome.Tap);
        staticText = "Tap all creatures target player controls. Those creatures don't untap during that player's next untap step";
    }

    private SleepEffect(final SleepEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
                creature.tap(source, game);
                doNotUntapNextUntapStep.add(creature);
            }
            if (!doNotUntapNextUntapStep.isEmpty()) {
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("", player.getId());
                effect.setText("those creatures don't untap during that player's next untap step");
                effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public SleepEffect copy() {
        return new SleepEffect(this);
    }

}
