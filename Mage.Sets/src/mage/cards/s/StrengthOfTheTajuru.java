package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author noxx
 */
public final class StrengthOfTheTajuru extends CardImpl {

    public StrengthOfTheTajuru(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{G}");

        // Multikicker (You may pay an additional {1} any number of times as you cast this spell.)
        this.addAbility(new MultikickerAbility("{1}"));

        // Choose target creature, then choose another target creature for each time Strength of the Tajuru was kicked. Put X +1/+1 counters on each of them.
        this.getSpellAbility().addEffect(new StrengthOfTheTajuruAddCountersTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new TargetsCountAdjuster(new IntPlusDynamicValue(1, MultikickerCount.instance)));
    }

    private StrengthOfTheTajuru(final StrengthOfTheTajuru card) {
        super(card);
    }

    @Override
    public StrengthOfTheTajuru copy() {
        return new StrengthOfTheTajuru(this);
    }
}

class StrengthOfTheTajuruAddCountersTargetEffect extends OneShotEffect {

    StrengthOfTheTajuruAddCountersTargetEffect() {
        super(Outcome.BoostCreature);
        staticText = "Choose target creature, then choose another target creature for each time this spell was kicked. Put X +1/+1 counters on each of them";
    }

    private StrengthOfTheTajuruAddCountersTargetEffect(final StrengthOfTheTajuruAddCountersTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        int amount = source.getManaCostsToPay().getX();
        Counter counter = CounterType.P1P1.createInstance(amount);
        for (UUID uuid : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(uuid);
            if (permanent != null) {
                permanent.addCounters(counter.copy(), source.getControllerId(), source, game);
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public StrengthOfTheTajuruAddCountersTargetEffect copy() {
        return new StrengthOfTheTajuruAddCountersTargetEffect(this);
    }

}
