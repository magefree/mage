
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author JayDi85
 */
public final class DeeprootElite extends CardImpl {

    private static final FilterPermanent filterYourAnotherMerfolk = new FilterPermanent(SubType.MERFOLK, "another " + SubType.MERFOLK.toString());
    static {
        filterYourAnotherMerfolk.add(AnotherPredicate.instance);
        filterYourAnotherMerfolk.add(TargetController.YOU.getControllerPredicate());
    }

    private static final FilterControlledCreaturePermanent filterYourAnyMerfolk = new FilterControlledCreaturePermanent(SubType.MERFOLK);
    static {
        filterYourAnyMerfolk.add(TargetController.YOU.getControllerPredicate());
    }

    public DeeprootElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another Merfolk enters the battlefield under your control, put a +1/+1 counter on target Merfolk you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), filterYourAnotherMerfolk);
        ability.addTarget(new TargetControlledCreaturePermanent(filterYourAnyMerfolk));
        this.addAbility(ability);
    }

    private DeeprootElite(final DeeprootElite card) {
        super(card);
    }

    @Override
    public DeeprootElite copy() {
        return new DeeprootElite(this);
    }
}