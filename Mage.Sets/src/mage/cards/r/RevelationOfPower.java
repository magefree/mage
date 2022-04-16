package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class RevelationOfPower extends CardImpl {

    public RevelationOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 until end of turn. If it has a counter on it, it also gains flying and lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new RevelationOfPowerEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RevelationOfPower(final RevelationOfPower card) {
        super(card);
    }

    @Override
    public RevelationOfPower copy() {
        return new RevelationOfPower(this);
    }
}

class RevelationOfPowerEffect extends OneShotEffect {

    public RevelationOfPowerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If it has a counter on it, it also gains flying and lifelink until end of turn";
    }

    private RevelationOfPowerEffect(final RevelationOfPowerEffect effect) {
        super(effect);
    }

    @Override
    public RevelationOfPowerEffect copy() {
        return new RevelationOfPowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            for (Counter counter : targetCreature.getCounters(game).values()) {
                if (counter.getCount() > 0) {
                    game.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance()), source);
                    game.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance()), source);
                    return true;
                }
            }
        }
        return false;
    }
}
