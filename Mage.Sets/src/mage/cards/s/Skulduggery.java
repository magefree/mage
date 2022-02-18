
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class Skulduggery extends CardImpl {

    public Skulduggery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Until end of turn, target creature you control gets +1/+1 and target creature an opponent controls gets -1/-1.
        this.getSpellAbility().addEffect(new SkulduggeryEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
    }

    private Skulduggery(final Skulduggery card) {
        super(card);
    }

    @Override
    public Skulduggery copy() {
        return new Skulduggery(this);
    }
}

class SkulduggeryEffect extends OneShotEffect {

    public SkulduggeryEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Until end of turn, target creature you control gets +1/+1 and target creature an opponent controls gets -1/-1";
    }

    public SkulduggeryEffect(final SkulduggeryEffect effect) {
        super(effect);
    }

    @Override
    public SkulduggeryEffect copy() {
        return new SkulduggeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            ContinuousEffect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            ContinuousEffect effect = new BoostTargetEffect(-1, -1, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
