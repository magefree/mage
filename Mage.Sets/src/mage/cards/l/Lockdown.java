package mage.cards.l;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
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
 * Color-shifted Sleep
 *
 * @author NinthWorld
 */
public final class Lockdown extends CardImpl {

    public Lockdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        

        // Tap all creatures target player controls. Those creatures don't untap during that player's next untap step.
        this.getSpellAbility().addEffect(new LockdownEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Lockdown(final Lockdown card) {
        super(card);
    }

    @Override
    public Lockdown copy() {
        return new Lockdown(this);
    }
}

class LockdownEffect extends OneShotEffect {

    public LockdownEffect() {
        super(Outcome.Tap);
        staticText = "Tap all creatures target player controls. Those creatures don't untap during that player's next untap step";
    }

    public LockdownEffect(final LockdownEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
                creature.tap(game);
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
    public LockdownEffect copy() {
        return new LockdownEffect(this);
    }

}
