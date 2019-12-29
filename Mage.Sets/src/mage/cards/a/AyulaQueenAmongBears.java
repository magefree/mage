package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyulaQueenAmongBears extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.BEAR, "another Bear");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.BEAR, "Bear");
    private static final FilterControlledPermanent filter3 = new FilterControlledPermanent("Bear you controls");
    private static final FilterPermanent filter4 = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(AnotherPredicate.instance);
        filter3.add(new SubtypePredicate(SubType.BEAR));
        filter4.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public AyulaQueenAmongBears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Bear enters the battlefield under your control, choose one —
        // • Put two +1/+1 counters on target Bear.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), filter
        );
        ability.addTarget(new TargetPermanent(filter2));

        // • Target Bear you control fights target creature you don't control.
        Mode mode = new Mode(new FightTargetsEffect());
        mode.addTarget(new TargetControlledPermanent(filter3));
        mode.addTarget(new TargetPermanent(filter4));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private AyulaQueenAmongBears(final AyulaQueenAmongBears card) {
        super(card);
    }

    @Override
    public AyulaQueenAmongBears copy() {
        return new AyulaQueenAmongBears(this);
    }
}
