package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author stravant
 */
public final class ShedWeakness extends CardImpl {

    public ShedWeakness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +2/+2 until end of turn. You may remove a -1/-1 counter from it.
        getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        getSpellAbility().addEffect(new MayRemoveM1M1CouterTargetEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ShedWeakness(final ShedWeakness card) {
        super(card);
    }

    @Override
    public ShedWeakness copy() {
        return new ShedWeakness(this);
    }
}

class MayRemoveM1M1CouterTargetEffect extends OneShotEffect {

    public MayRemoveM1M1CouterTargetEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "You may remove a -1/-1 counter from it.";
    }

    public MayRemoveM1M1CouterTargetEffect(final MayRemoveM1M1CouterTargetEffect effect) {
        super(effect);
        this.staticText = "You may remove a -1/-1 counter from it.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent target = game.getPermanent(source.getTargets().getFirstTarget());
        if (target == null) {
            return false;
        }

        if (target.getCounters(game).getCount(CounterType.M1M1) > 0) {
            if (controller.chooseUse(outcome, "Remove a -1/-1 counter from " + target.getIdName() + "?", source, game)) {
                Effect effect = new RemoveCounterTargetEffect(CounterType.M1M1.createInstance());
                effect.setTargetPointer(new FixedTarget(target.getId(), game));
                effect.apply(game, source);
            }
        }

        return true;
    }

    @Override
    public MayRemoveM1M1CouterTargetEffect copy() {
        return new MayRemoveM1M1CouterTargetEffect(this);
    }
}
