package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodCurdle extends CardImpl {

    public BloodCurdle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Destroy target creature. Put a menace counter on a creature you control.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BloodCurdleEffect());
    }

    private BloodCurdle(final BloodCurdle card) {
        super(card);
    }

    @Override
    public BloodCurdle copy() {
        return new BloodCurdle(this);
    }
}

class BloodCurdleEffect extends OneShotEffect {

    BloodCurdleEffect() {
        super(Outcome.Benefit);
        staticText = "Put a menace counter on a creature you control. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>";
    }

    private BloodCurdleEffect(final BloodCurdleEffect effect) {
        super(effect);
    }

    @Override
    public BloodCurdleEffect copy() {
        return new BloodCurdleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (!player.choose(outcome, target, source.getSourceId(), game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return permanent.addCounters(CounterType.MENACE.createInstance(), source.getControllerId(), source, game);
    }
}