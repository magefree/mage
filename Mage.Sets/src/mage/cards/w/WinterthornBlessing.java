package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class WinterthornBlessing extends CardImpl {

    public WinterthornBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{U}");

        // Put a +1/+1 counter on up to one target creature you control. Tap up to one target creature you don't control, and that creature doesn't untap during its controller's next untap step.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(new TargetOpponentsCreaturePermanent()));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new WinterthornBlessingEffect());

        // Flashback {1}{G}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{G}{U}")));

    }

    private WinterthornBlessing(final WinterthornBlessing card) {
        super(card);
    }

    @Override
    public WinterthornBlessing copy() {
        return new WinterthornBlessing(this);
    }
}

class WinterthornBlessingEffect extends OneShotEffect {
    WinterthornBlessingEffect() {
        super(Outcome.Benefit);
        staticText = "Tap up to one target creature you don't control, " +
                "and that creature doesn't untap during its controller's next untap step.";
    }

    private WinterthornBlessingEffect(final WinterthornBlessingEffect effect) {
        super(effect);
    }

    @Override
    public WinterthornBlessingEffect copy() {
        return new WinterthornBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect();
            effect.setTargetPointer(new FixedTarget(creature, game));

            game.addEffect(effect, source);
            creature.tap(source, game);
            return true;
        }
        return false;
    }
}
