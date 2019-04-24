package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 * @author JRHerlehy Created on 4/7/18.
 */
public final class MarwynTheNurturer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Elf");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public MarwynTheNurturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF, SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another Elf enters the battlefield under your control, put a +1/+1 counter on Marwyn, the Nurturer.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter));

        // {T}: Add an amount of {G} equal to Marwyn's power.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), new SourcePermanentPowerCount(), "Add an amount of {G} equal to {this}'s power"));
    }

    public MarwynTheNurturer(final MarwynTheNurturer card) {
        super(card);
    }

    @Override
    public MarwynTheNurturer copy() {
        return new MarwynTheNurturer(this);
    }

}
