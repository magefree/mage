package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RenegadeSilent extends CardImpl {

    public RenegadeSilent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, goad up to one target creature you don't control and put a +1/+1 counter on Renegade Silent. Renegade Silent phases out.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new GoadTargetEffect().setText("goad up to one target creature you don't control"),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .concatBy("and"));
        ability.addEffect(new PhaseOutSourceEffect());
        this.addAbility(ability);
    }

    private RenegadeSilent(final RenegadeSilent card) {
        super(card);
    }

    @Override
    public RenegadeSilent copy() {
        return new RenegadeSilent(this);
    }
}
