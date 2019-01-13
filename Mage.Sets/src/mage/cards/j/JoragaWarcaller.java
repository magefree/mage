
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class JoragaWarcaller extends CardImpl {
    
    private static final String rule = "Other Elf creatures you control get +1/+1 for each +1/+1 counter on Joraga Warcaller";
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("elf creatures you control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.ELF));
        filter.add(AnotherPredicate.instance);
    }

    public JoragaWarcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Multikicker {1}{G}
        this.addAbility(new MultikickerAbility("{1}{G}"));
        
        // Joraga Warcaller enters the battlefield with a +1/+1 counter on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), MultikickerCount.instance, true),
                "with a +1/+1 counter on it for each time it was kicked"));

        
        // Other Elf creatures you control get +1/+1 for each +1/+1 counter on Joraga Warcaller.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new BoostAllEffect( new CountersSourceCount(CounterType.P1P1), new CountersSourceCount(CounterType.P1P1), Duration.WhileOnBattlefield, filter, true, rule)));
        
    }

    public JoragaWarcaller(final JoragaWarcaller card) {
        super(card);
    }

    @Override
    public JoragaWarcaller copy() {
        return new JoragaWarcaller(this);
    }
}