package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class HunterOfEyeblights extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public HunterOfEyeblights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Hunter of Eyeblights enters the battlefield, put a +1/+1 counter on target creature you don't control
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        //{B}{2},{T}: Destroy target creature with a counter on it.
        Ability ability2 = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability2);
    }

    private HunterOfEyeblights(final HunterOfEyeblights card) {
        super(card);
    }

    @Override
    public HunterOfEyeblights copy() {
        return new HunterOfEyeblights(this);
    }
}
