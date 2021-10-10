package mage.cards.w;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class WinterthornBlessing extends CardImpl {

    public WinterthornBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{U}");

        // Put a +1/+1 counter on up to one target creature you control. Tap up to one target creature you don't control, and that creature doesn't untap during its controller's next untap step.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setTargetPointer(new FirstTargetPointer()));
        this.getSpellAbility().addEffect(new TapTargetEffect().setTargetPointer(new SecondTargetPointer()).setText("tap up to one target creature you don't control"));
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect().setTargetPointer(new SecondTargetPointer()).setText(", and that creature doesn't untap during its controller's next untap step"));

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
