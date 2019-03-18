
package mage.cards.h;

import java.util.UUID;
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
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class HunterOfEyeblights extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature you don't control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with a counter on it");

    static {
        filter1.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter2.add(CounterAnyPredicate.instance);

    }

    public HunterOfEyeblights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Hunter of Eyeblights enters the battlefield, put a +1/+1 counter on target creature you don't control
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(filter1));
        this.addAbility(ability);

        //{B}{2},{T}: Destroy target creature with a counter on it.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{2}{B}"));
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability2);
    }

    public HunterOfEyeblights(final HunterOfEyeblights card) {
        super(card);
    }

    @Override
    public HunterOfEyeblights copy() {
        return new HunterOfEyeblights(this);
    }
}
