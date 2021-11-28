
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class FistfulOfForce extends CardImpl {

    public FistfulOfForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Target creature gets +2/+2 until end of turn. Clash with an opponent. If you win, that creature gets an additional +2/+2 and gains trample until end of turn.
        this.getSpellAbility().addEffect(new FistfulOfForceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FistfulOfForce(final FistfulOfForce card) {
        super(card);
    }

    @Override
    public FistfulOfForce copy() {
        return new FistfulOfForce(this);
    }
}

class FistfulOfForceEffect extends OneShotEffect {

    public FistfulOfForceEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Target creature gets +2/+2 until end of turn. Clash with an opponent. If you win, that creature gets an additional +2/+2 and gains trample until end of turn";
    }

    public FistfulOfForceEffect(final FistfulOfForceEffect effect) {
        super(effect);
    }

    @Override
    public FistfulOfForceEffect copy() {
        return new FistfulOfForceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && creature != null) {
            ContinuousEffect effect = new BoostTargetEffect(2,2,Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature.getId(), game));
            game.addEffect(effect, source);
            if (ClashEffect.getInstance().apply(game, source)) {
                game.addEffect(effect.copy(), source);
                effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature.getId(), game));
                game.addEffect(effect.copy(), source);
            }
            return true;
        }
        return false;
    }
}
