package mage.cards.h;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HuntTheWeak extends CardImpl {

    public HuntTheWeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Put a +1/+1 counter on target creature you control. Then that creature fights target creature you don't control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText(
                "Then that creature fights target creature you don't control. " +
                        "<i>(Each deals damage equal to its power to the other.)</i>"
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private HuntTheWeak(final HuntTheWeak card) {
        super(card);
    }

    @Override
    public HuntTheWeak copy() {
        return new HuntTheWeak(this);
    }
}
