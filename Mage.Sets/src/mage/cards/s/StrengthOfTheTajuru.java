package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
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
import mage.target.targetadjustment.TargetAdjuster;

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
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        this.getSpellAbility().setTargetAdjuster(StrengthOfTheTajuruAdjuster.instance);
    }

    private StrengthOfTheTajuru(final StrengthOfTheTajuru card) {
        super(card);
    }

    @Override
    public StrengthOfTheTajuru copy() {
        return new StrengthOfTheTajuru(this);
    }
}

enum StrengthOfTheTajuruAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int numbTargets = MultikickerCount.instance.calculate(game, ability, null) + 1;
        ability.addTarget(new TargetCreaturePermanent(0, numbTargets));
    }
}

class StrengthOfTheTajuruAddCountersTargetEffect extends OneShotEffect {

    public StrengthOfTheTajuruAddCountersTargetEffect() {
        super(Outcome.BoostCreature);
        staticText = "Choose target creature, then choose another target creature for each time this spell was kicked. Put X +1/+1 counters on each of them";
    }

    public StrengthOfTheTajuruAddCountersTargetEffect(final StrengthOfTheTajuruAddCountersTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        int amount = source.getManaCostsToPay().getX();
        Counter counter = CounterType.P1P1.createInstance(amount);
        for (UUID uuid : targetPointer.getTargets(game, source)) {
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
